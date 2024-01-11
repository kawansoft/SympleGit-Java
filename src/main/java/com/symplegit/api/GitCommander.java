/*
 * This file is part of SympleGit. 
 * SympleGit: Git in Java for the rest of us.                                     
 * Copyright (C) 2024,  KawanSoft SAS.
 * (http://www.kawansoft.com). All rights reserved.                                
 *                                                                               
 * SympleGit is free software; you can redistribute it and/or                 
 * modify it under the terms of the GNU Lesser General Public                    
 * License as published by the Free Software Foundation; either                  
 * version 2.1 of the License, or (at your option) any later version.            
 *                                                                               
 * SympleGit is distributed in the hope that it will be useful,               
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU             
 * Lesser General Public License for more details.                               
 *                                                                               
 * You should have received a copy of the GNU Lesser General Public              
 * License along with this library; if not, write to the Free Software           
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  
 * 02110-1301  USA
 *
 * Any modifications to this file must keep this entire header
 * intact.
 */
package com.symplegit.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

import com.symplegit.api.exception.UncheckedTimeoutException;
import com.symplegit.util.ApiDateUtil;
import com.symplegit.util.FrameworkDebug;

/**
 * The GitCommander class is responsible for executing Git commands and handling
 * their outputs. It uses a ProcessBuilder to run Git commands and captures
 * their output and error streams.
 *
 * @author Nicolas de Pomereu
 */
public class GitCommander {

    public static boolean DEBUG = FrameworkDebug.isSet(GitCommander.class);

    private ProcessBuilder builder;
    private Exception exception;
    private int exitCode;

    private File tempErrorFile = null;
    private File tempOutputFile = null;

    private boolean useStringOutput = false;

    private String outputStr;
    private String errorStr;

    private int timeoutSeconds = 0;

    /**
     * Constructs a GitCommander object with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     * @throws NullPointerException if sympleGit is null.
     */
    public GitCommander(SympleGit sympleGit) {
	Objects.requireNonNull(sympleGit, "sympleGit cannot be null!");
	builder = new ProcessBuilder();
	builder.directory(sympleGit.getProjectDir());
	this.useStringOutput = sympleGit.isUseStringOutput();
	this.timeoutSeconds = sympleGit.getTimeoutSeconds();
    }

    /**
     * Executes a Git command and handles its output and error streams.
     *
     * @param command The Git command to be executed, split into an array of
     *                strings.
     */
    public void executeGitCommand(String... command) {
	Objects.requireNonNull(command, "builder cannot be null!");

	Thread thread = new Thread(() -> {
	    executeInThread(command);
	});
	thread.start();
	
//	ExecutorService executor = Executors.newFixedThreadPool(1);
//	Future<?> future = executor.submit(() -> {
//	    executeInThread(command);
//	});
	
        System.out.println("here 1");
        
	long begin = System.currentTimeMillis();
	    
	while (thread.isAlive()) {
	    	
	    long now = System.currentTimeMillis();
            try {	
                Thread.sleep(100);
            }
            catch (InterruptedException ignore) {
        	ignore.printStackTrace();
            }
            
            if ( timeoutSeconds != 0 &&  (now  - begin > 1000 * timeoutSeconds)) {
        	//future.cancel(true);
                throw new UncheckedTimeoutException("Timeout after " + timeoutSeconds + " seconds!");
            };
	}
	
    }

    /**
     * @param command
     */
    private void executeInThread(String... command) {
	// builder.redirectErrorStream(true);
	Process process = null;

	debug("Git command: " + removeCommas(Arrays.toString(command)));

	try {
	    builder.command(command); // Correctly set the command
	    process = builder.start();

	    if (useStringOutput) {
		outputStr = IOUtils.toString(process.getInputStream(), "UTF-8");
		errorStr = IOUtils.toString(process.getErrorStream(), "UTF-8");
	    } else {
		
		debug("Before tempErrorFile creation");
		
		tempErrorFile = File.createTempFile("symplegit-error-" + ApiDateUtil.getDateWithTime() + "-", ".txt");

		try (OutputStream osError = new BufferedOutputStream(new FileOutputStream(tempErrorFile))) {
		    IOUtils.copy(process.getErrorStream(), osError);
		}
		
		debug("After tempErrorFile creation");
		
		// Optionally, delete the file when the JVM exits
		tempErrorFile.deleteOnExit();

		debug("Before tempOutputFile creation");
		
		tempOutputFile = File.createTempFile("symplegit-output-" + ApiDateUtil.getDateWithTime() + "-", ".txt");
	        
		try (OutputStream osInput = new BufferedOutputStream(new FileOutputStream(tempOutputFile))) {
		    IOUtils.copy(process.getInputStream(), osInput);
		}

		debug("After tempOutputFile creation");
	        
		// Optionally, delete the file when the JVM exits
		tempOutputFile.deleteOnExit();
	    }

	    debug("waitFor...: " + removeCommas(Arrays.toString(command)));

	    exitCode = process.waitFor();
	    debug("exitCode: " + exitCode);

	    process.destroy();
	    process.destroyForcibly();

	} catch (Exception theException) {
	    this.exception = theException;
	}
    }

    /**
     * Checks if the last executed Git command was successful.
     *
     * @return true if the last Git command executed successfully (exit code 0),
     *         false otherwise.
     */
    public boolean isResponseOk() {
	return exitCode == 0;
    }

    /**
     * Retrieves the exit code of the last executed Git command.
     *
     * @return The exit code of the last Git command execution.
     */
    public int getExitCode() {
	return exitCode;
    }

    /**
     * Gets the standard output of the last executed Git command as a String.
     *
     * @return The standard output of the last executed Git command.
     * @throws IOException if an I/O error occurs while reading the output.
     */
    public String getProcessOutput() throws IOException {
	if (getSize() > SympleGit.DEFAULT_MAX_STRING_SIZE_MB * 1024 * 1024) {
	    throw new IOException("Output too large for String content! Is > " + SympleGit.DEFAULT_MAX_STRING_SIZE_MB + " MB.");
	}
	return this.useStringOutput ? outputStr : IOUtils.toString(getProcessOutputAsInputStream(), "UTF-8");
    }

    /**
     * Gets the error output of the last executed Git command as a String.
     *
     * @return The error output of the last executed Git command.
     * @throws IOException if an I/O error occurs while reading the error output.
     */
    public String getProcessError() throws IOException {
	return this.useStringOutput ? errorStr : IOUtils.toString(getProcessErrorAsInputStream(), "UTF-8");
    }

    /**
     * Gets the length of the standard output of the last executed Git command. This
     * will allow to decide if the content can be directly retrieved as a String.
     *
     * @return The length of the standard output of the last executed Git command.
     * @throws IOException if an I/O error occurs while reading the output.
     */
    public long getSize() {
	return tempOutputFile.length();
    }

    /**
     * Retrieves the standard output of the last executed Git command as an
     * InputStream.
     *
     * @return An InputStream of the standard output of the last executed Git
     *         command.
     * @throws IOException if the output file does not exist or an I/O error occurs.
     */
    public InputStream getProcessOutputAsInputStream() throws IOException {

	if (useStringOutput) {
	    return new ByteArrayInputStream(outputStr.getBytes());
	} else {
	    if (tempOutputFile != null && tempOutputFile.exists()) {
		return new BufferedInputStream(new FileInputStream(tempOutputFile));
	    }
	    return null;
	}
    }

    /**
     * Retrieves the error output of the last executed Git command as an
     * InputStream.
     *
     * @return An InputStream of the error output of the last executed Git command.
     * @throws IOException if the error file does not exist or an I/O error occurs.
     */
    public InputStream getProcessErrorAsInputStream() throws IOException {

	if (useStringOutput) {
	    return new ByteArrayInputStream(errorStr.getBytes());
	} else {
	    if (tempErrorFile != null && tempErrorFile.exists()) {
		return new BufferedInputStream(new FileInputStream(tempErrorFile));
	    }
	    return null;
	}

    }

    /**
     * Retrieves the exception that occurred during the last Git command execution,
     * if any.
     *
     * @return The exception thrown during the last Git command execution, or null
     *         if no exception occurred.
     */
    public Exception getException() {
	return exception;
    }

    /**
     * Removes commas from a given string.
     *
     * @param str The string from which commas should be removed.
     * @return The string without commas.
     */
    private String removeCommas(String str) {
	if (str != null && str.contains(",")) {
	    str = str.replace(",", "");
	}
	return str;
    }

    /**
     * The delete of the temp files are normally done when the JVM exits with a
     * File.deleteOnExit() call. May be optionally be done to delete immediately the
     * temp files created.
     */
    public void close() {
	if (tempErrorFile != null && tempErrorFile.exists()) {
	    tempErrorFile.delete();
	}

	if (tempOutputFile != null && tempOutputFile.exists()) {
	    tempOutputFile.delete();
	}
    }

    /**
     * Prints a debug message with the current timestamp if debugging is enabled.
     *
     * @param sMsg The debug message to be printed.
     */
    protected void debug(String sMsg) {
	if (DEBUG) {
	    System.out.println(new Date() + " " + sMsg);
	}
    }

}
