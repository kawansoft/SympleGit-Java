/*
 * This file is part of SympleGit
 * SympleGit: Straightforward  Git in Java. Follows 
 *           'AI-Extensible Open Source Software' pattern
 * Copyright (C) 2024,  KawanSoft SAS
 * (http://www.kawansoft.com). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.symplegit.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    private int timeout = 0;
    
    private Process process = null;

    /**
     * Constructs a GitCommander object with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     * @throws NullPointerException if sympleGit is null.
     */
    GitCommander(SympleGit sympleGit) {
	Objects.requireNonNull(sympleGit, "sympleGit cannot be null!");
	builder = new ProcessBuilder();
	builder.directory(sympleGit.getDirectory());
	this.timeout = sympleGit.getTimeout();
    }


    /**
     * Executes a Git command and handles its output and error streams.
     *
     * @param command The Git command to be executed, split into an array of
     *                strings.
     */
    @SuppressWarnings("unused")
    private void executeGitCommandOld(String... command) {
	Objects.requireNonNull(command, "builder cannot be null!");

	Thread thread = new Thread(() -> {
	    executeInThread(command);
	});
	thread.start();

	long begin = System.currentTimeMillis();
	    
        debug(" Before while (thread.isAlive())");
        
	while (thread.isAlive()) {
	    	
	    long now = System.currentTimeMillis();
            try {	
                Thread.sleep(100);
            }
            catch (InterruptedException ignore) {
        	ignore.printStackTrace();
            }
            
            if ( timeout != 0 &&  (now  - begin > 1000 * timeout)) {
                throw new UncheckedTimeoutException("Timeout after " + timeout + " seconds!");
            };
	}
	
    }
    
    /**
     * Executes a Git command and handles its output and error streams.
     *
     * @param command The Git command to be executed, split into an array of
     *                strings.
     */
    public void executeGitCommand(String... command) {
	
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
        	executeInThread(command);
                return "OK";
            }
        };

        Future<String> future = executor.submit(task);

        try {
            
            long futureTimeout = timeout == 0? Long.MAX_VALUE : timeout;
            
            // Get the result of the asynchronous computation with a timeout of 1 second
            future.get(futureTimeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            stopProcess();
            throw new UncheckedTimeoutException("Timeout after " + timeout + " seconds!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // Always remember to shut down the executor service
        }
    }

    /**
     * @param command
     */
    private void executeInThread(String... command) {
	// builder.redirectErrorStream(true);

	debug("Git command: " + removeCommas(Arrays.toString(command)));

	try {
	    
	    builder.command(command); // Correctly set the command
	    process = builder.start();

	    debug("Before tempErrorFile creation");

	    tempErrorFile = File.createTempFile("symplegit-error-" + ApiDateUtil.getDateWithTime() + "-", ".txt");

	    try (OutputStream osError = new BufferedOutputStream(new FileOutputStream(tempErrorFile))) {
		IOUtils.copy(new BufferedInputStream(getProcessErrorAsInputStream()), osError);
	    }
	    	    
	    debug("After tempErrorFile creation");

	    // Optionally, delete the file when the JVM exits
	    tempErrorFile.deleteOnExit();

	    debug("Before tempOutputFile creation");

	    tempOutputFile = File.createTempFile("symplegit-output-" + ApiDateUtil.getDateWithTime() + "-", ".txt");

	    try (OutputStream osInput = new BufferedOutputStream(new FileOutputStream(tempOutputFile))) {
		IOUtils.copy(new BufferedInputStream(process.getInputStream()), osInput);
	    }

	    debug("After tempOutputFile creation");

	    // Optionally, delete the file when the JVM exits
	    tempOutputFile.deleteOnExit();

	    debug("waitFor...: " + removeCommas(Arrays.toString(command)));

	    exitCode = process.waitFor();
	    debug("exitCode: " + exitCode);

	    //process.destroy();
	    process.destroyForcibly();

	} catch (Throwable throwable) {
	    if (throwable instanceof Exception) {
		exception = (Exception) throwable;

	    } else {
		exception = new Exception(throwable);
	    }
	}
    }
    
    private void stopProcess() {
        if (this.process != null) {
            //this.process.destroy();
            this.process.destroyForcibly();
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
     * <br>
     * It's good practice to test the length of the output as it can be retrieved with {@link #getSize()}.
     *
     * @return The standard output of the last executed Git command.
     * @throws IOException if an I/O error occurs while reading the output.
     */
    public String getProcessOutput() throws IOException {
	return IOUtils.toString(getProcessOutputAsInputStream(), "UTF-8");
    }

    /**
     * Gets the error output of the last executed Git command as a String.
     *
     * @return The error output of the last executed Git command.
     * @throws IOException if an I/O error occurs while reading the error output.
     */
    public String getProcessError() throws IOException {
	return IOUtils.toString(getProcessErrorAsInputStream(), "UTF-8");
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

	if (tempOutputFile != null && tempOutputFile.exists()) {
	    return new BufferedInputStream(new FileInputStream(tempOutputFile));
	}
	return null;
    }

    /**
     * Retrieves the error output of the last executed Git command as an
     * InputStream.
     *
     * @return An InputStream of the error output of the last executed Git command.
     * @throws IOException if the error file does not exist or an I/O error occurs.
     */
    public InputStream getProcessErrorAsInputStream() throws IOException {

	if (tempErrorFile != null && tempErrorFile.exists()) {
	    return new BufferedInputStream(new FileInputStream(tempErrorFile));
	}
	return null;

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
