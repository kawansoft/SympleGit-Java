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
package com.symplegit.api.facilitator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * The GitAdd class allows adding all changed files, or specific files to the staging area.
 * This class implements the GitWrapper interface, using GitCommander for executing Git commands.
 * <br><br>
 * Usage
 * <pre> <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();
		
	GitAdd gitAdd = new GitAdd(sympleGit);
	
	// Call a method
	gitAdd.addAll();
 * </code> </pre>
 * 
 * @author KawanSoft SAS
 * @author GPT-4
 */
public class GitAdd implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitAdd with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitAdd(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Adds all changed files to the staging area.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void addAll() throws IOException {
        executeGitCommandWithErrorHandler("git", "add", ".");
    }

    /**
     * Adds a list of specified file paths to the staging area.
     *
     * @param filenames The list of file names to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void add(List<String> filenames) throws IOException {
        if (filenames == null || filenames.isEmpty()) {
            throw new IllegalArgumentException("Filenames list cannot be null or empty.");
        }
        for (String file : filenames) {
            executeGitCommandWithErrorHandler("git", "add", file);
        }
    }

    /**
     * Adds a list of specified file paths to the staging area.
     *
     * @param filenames The list of file names to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void add(String... filenames) throws IOException {
        if (filenames == null || filenames.length ==0) {
            throw new IllegalArgumentException("Filenames list cannot be null or empty.");
        }
        for (String file : filenames) {
            executeGitCommandWithErrorHandler("git", "add", file);
        }
    }
    
    /**
     * Adds a list of File objects to the staging area.
     *
     * @param files The list of File objects to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void addFiles(File... files) throws IOException {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }
        for (File file : files) {
            executeGitCommandWithErrorHandler("git", "add", file.getAbsolutePath());
        }
    }
    
    /**
     * Adds a list of File objects to the staging area.
     *
     * @param files The list of File objects to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void addFiles(List<File> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }
        for (File file : files) {
            executeGitCommandWithErrorHandler("git", "add", file.getAbsolutePath());
        }
    }

    /**
     * Executes a Git command and handles errors generically.
     *
     * @param command The Git command to be executed.
     * @throws IOException If an error occurs during command execution.
     */
    private void executeGitCommandWithErrorHandler(String... command) throws IOException {
        gitCommander.executeGitCommand(command);

        if (!gitCommander.isResponseOk()) {
            errorMessage = gitCommander.getProcessError();
            exception = gitCommander.getException();
        }
    }

    @Override
    public boolean isResponseOk() {
        return gitCommander.isResponseOk();
    }

    @Override
    public String getError() {
        return errorMessage;
    }

    @Override
    public Exception getException() {
        return exception;
    }
}
