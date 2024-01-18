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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.symplegit.util.FrameworkDebug;

/**
 * SympleGit provides a fluent and simplified interface for configuring and
 * using Git operations. It utilizes a builder pattern for easy configuration
 * and initialization. It is a part of the SympleGit package, which aims to
 * simplify interactions with Git repositories. <br>
 * <br>
 * Usage:
 * 
 * <pre>
 * <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	// From there:
	// 1) Call directly a Git Command

	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "add", "testFile1", "testFile2");

	// Or 2) call the Facilitator API with the build in classes
	 
	GitAdd gitAdd = new GitAdd(sympleGit);
	gitAdd.add("testFile1", "testFile2");

	GitCommit gitCommit = new GitCommit(sympleGit);
	gitCommit.commitChanges("Modified test files");   
	
 * </code>
 * </pre>
 * 
 * @author KawanSoft SAS
 */
public class SympleGit implements AutoCloseable {

    /** Debug flag. */
    public static boolean DEBUG = FrameworkDebug.isSet(SympleGit.class);

    /** The default timeout for Git operations. */
    private static final int DEFAULT_TIMEOUT_SECONDS = 0;

    private final File directory;
    private int timeout;
    private TimeUnit unit;

    private List<File> tempFiles = new ArrayList<>();

    /**
     * Constructs a new instance of SympleGit with the specified configuration.
     *
     * @param builder The Builder object containing configuration settings.
     */
    private SympleGit(Builder builder) {
	this.directory = builder.directory;
	this.timeout = builder.timeout;
	this.unit = builder.unit;
    }

    /**
     * Creates a new Builder instance for configuring SympleGit.
     *
     * @return A new Builder instance.
     */
    public static Builder custom() {
	return new Builder();
    }

    /**
     * Gets the directory associated with this SympleGit instance.
     *
     * @return The directory as a File object.
     */
    public File getDirectory() {
	return directory;
    }

    /**
     * Gets the timeout setting for Git operations.
     *
     * @return The timeout in milliseconds.
     */
    public int getTimeout() {
	return timeout;
    }

    /**
     * Gets the timeout setting for Git operations.
     *
     * @return The timeout in milliseconds.
     * 
     */
    public TimeUnit getUnit() {
	return unit;
    }

    // Additional methods or functionality as needed

    /**
     * Builder class for SympleGit. Provides methods to configure SympleGit
     * instances.
     */
    public static class Builder {

	private File directory;
	private int timeout = DEFAULT_TIMEOUT_SECONDS;
	private TimeUnit unit = TimeUnit.SECONDS;

	/**
	 * Sets the directory path for the Git repository.
	 *
	 * @param directoryPath The path to the Git repository directory.
	 * @return The Builder instance for chaining.
	 */
	public Builder setDirectory(String directoryPath) {
	    Objects.requireNonNull(directoryPath, "directoryPath cannot be null");
	    this.directory = new File(directoryPath);
	    return this;
	}

	/**
	 * Sets the directory path for the Git repository.
	 *
	 * @param directoryFile The File of the Git repository directory.
	 * @return The Builder instance for chaining.
	 */
	public Builder setDirectory(File directoryFile) {
	    Objects.requireNonNull(directoryFile, "directoryFile cannot be null");
	    this.directory = directoryFile;
	    return this;
	}

	/**
	 * Sets the timeout for Git operations.
	 *
	 * @param timeout the maximum time to wait
	 * @param unit    the time unit of the timeout argument
	 * @return The Builder instance for chaining.
	 */
	public Builder setTimeout(int timeout, TimeUnit unit) {
	    this.timeout = timeout;
	    this.unit = unit;
	    return this;
	}

	/**
	 * Builds and returns a SympleGit instance with the current configuration.
	 *
	 * @return A configured SympleGit instance.
	 */
	public SympleGit build() {
	    return new SympleGit(this);
	}
    }

    /**
     * Creates a GitCommander instance based on the current SympleGit configuration.
     *
     * @return A new GitCommander instance.
     */
    public GitCommander gitCommander() {
	GitCommander gitCommander = new GitCommander(this);
	return gitCommander;
    }

    @Override
    public String toString() {
	return "SympleGit [directory=" + directory + ", timeout=" + timeout + ", unit=" + unit + "]";
    }

    /**
     * Adds a temporary error file or output to the list of temporary error files.
     * 
     * @param tempErrorFile the temp file containing the error message
     */
    void addTempFile(File tempFile) {
	tempFiles.add(tempFile);
    }

    /**
     * Deletes all temporary files. Should be done to to relieve java.io.tmpdir.
     * <br>
     * Temporary files are always deleted when the application is closed. <br>
     */
    @Override
    public void close() throws Exception {
	if (tempFiles != null) {
	    for (File tempFile : tempFiles) {
		tempFile.delete();
	    }
	}
    }


    /**
     * Prints a debug message with the current timestamp if debugging is enabled.
     *
     * @param sMsg The debug message to be printed.
     */
    protected static void debug(String sMsg) {
	if (DEBUG) {
	    System.out.println(new Date() + " " + sMsg);
	}
    }
}
