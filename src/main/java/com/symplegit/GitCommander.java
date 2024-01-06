
package com.symplegit;

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

import org.apache.commons.io.IOUtils;

import com.symplegit.util.FrameworkDebug;

/**
 *
 * @author ndepo
 */
public class GitCommander {

    public static boolean DEBUG = FrameworkDebug.isSet(GitCommander.class);

    ProcessBuilder builder = new ProcessBuilder();

    private Exception exception;

    private int exitCode;

    private File tempErrorFile;
    private File tempOutputFile;

    /**
     * Constructor. Executes a Command with the current ProcessBuilder
     *
     * @param builder the current ProcessBuilder to use
     */
     public GitCommander(SympleGit sympleGit) {
	Objects.requireNonNull(sympleGit, "sympleGit cannot be null!");

	builder = new ProcessBuilder();
	builder.directory(sympleGit.getProjectDir());

    }

    /**
     * Executes the Git command. Outout and error can be retrieved with getOutput
     * and getOutput
     *
     * @param command the command aftter "git" splited in Strings
     * @throws IOException
     */
    public void executeGitCommand(String... command) throws IOException {
	Objects.requireNonNull(command, "builder cannot be null!");

	// builder.redirectErrorStream(true);
	Process process = null;

	debug("Git command: " + removeCommas(Arrays.toString(command)));

	try {
	    builder.command(command); // Correctly set the command
	    process = builder.start();

	    tempErrorFile = File.createTempFile("smoothgit_error_stream", ".txt");
	    // Optionally, delete the file when the JVM exits
	    tempErrorFile.deleteOnExit();

	    try (OutputStream osError = new BufferedOutputStream(new FileOutputStream(tempErrorFile))) {
		IOUtils.copy(process.getErrorStream(), osError);
	    }

	    tempOutputFile = File.createTempFile("smoothgit_output_stream", ".txt");
	    // Optionally, delete the file when the JVM exits
	    tempOutputFile.deleteOnExit();

	    try (OutputStream osInput = new BufferedOutputStream(new FileOutputStream(tempOutputFile))) {
		IOUtils.copy(process.getInputStream(), osInput);
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
     * Says if the process executed correctly
     *
     * @return true if process executed correctly
     */
    public boolean isResponseOk() {
	return exitCode == 0;
    }

    /**
     * Returns the exit code, 0 means OK.
     *
     * @return the exit code, 0 means OK.
     */
    public int getExitCode() {
	return exitCode;
    }

    public String getProcessOutput() throws IOException {
	return IOUtils.toString(getProcessOutputAsInputStream(), "UTF-8");
    }

    public String getProcessError() throws IOException {
	return IOUtils.toString(getProcessOutputAsInputStream(), "UTF-8");
    }
    
    public InputStream getProcessOutputAsInputStream() throws IOException {
	if (tempOutputFile != null && tempOutputFile.exists()) {
	    InputStream is = new BufferedInputStream(new FileInputStream(tempOutputFile));
	    return is;
	} else {
	    return null;
	}
    }

    public InputStream getProcessErrorAsInputStream() throws IOException {
	if (tempErrorFile != null && tempErrorFile.exists()) {
	    InputStream is = new BufferedInputStream(new FileInputStream(tempErrorFile));
	    return is;
	}
	return null;
    }
    
    public Exception getException() {
	return exception;
    }

    public static void printError(GitCommander gitCommander) throws IOException {
	String error = gitCommander.getProcessError();
	if (error != null && !error.isEmpty()) {
	    System.out.println(error);
	}
    }

    public static void printOutput(GitCommander gitCommander) throws IOException {
	String outputString = gitCommander.getProcessOutput();
	System.out.println(outputString);

	String error = gitCommander.getProcessError();
	if (error != null && !error.isEmpty()) {
	    System.out.println(outputString);
	}
    }
    

    private String removeCommas(String str) {
	if (str != null && str.contains(",")) {
	    str = str.replace(",", "");
	}

	return str;
    }

   
    /**
     * Displays the specified message if the DEBUG flag is set.
     *
     * @param sMsg the debug message to display
     */
    protected void debug(String sMsg) {
	if (DEBUG) {
	    System.out.println(new Date() + " " + sMsg);
	}
    }

    public void close() throws Exception {
	if (tempErrorFile!= null && tempErrorFile.exists()) {
            tempErrorFile.delete();
        }
	if (tempOutputFile!= null && tempOutputFile.exists()) {
            tempOutputFile.delete();
        }
    }

}
