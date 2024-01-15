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
package com.symplegit.facilitator.api;

import java.io.IOException;
import java.io.InputStream;

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * GitCommit provides functionality for handling Git commits.
 * It includes methods for committing changes, amending commits, and retrieving commit history.
 * This class implements the GitWrapper interface and uses GitCommander for command execution.
 * <br><br>
 * Usage
 * <pre> <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();
 
	GitCommit commit = new GitCommit(sympleGit);
	
	// Call a method
	commit.commitChanges("My new commit message");
 * </code> </pre>
 * 
 * @author KawanSoft SAS
 * @author GPT-4
 */
public class GitCommit implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitCommit with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitCommit(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Commits changes with the provided commit message.
     *
     * @param message The commit message.
     * @throws IOException If an error occurs during command execution.
     */
    public void commitChanges(String message) throws IOException {
        executeGitCommandWithErrorHandler("git", "commit", "-m", message );
    }

    /**
     * Amends the last commit.
     * @param message The commit message.
     * @throws IOException If an error occurs during command execution.
     */
    public void amendCommit(String message) throws IOException {
        executeGitCommandWithErrorHandler("git", "commit", "--amend", "-m", message );
    }

    /**
     * Retrieves the commit history of the current branch as String.
     * Will throw an IOException if the result is > 10Mb.
     * @return A String containing the commit history.
     * @throws IOException If an error occurs during command execution.
     */
    public String getCommitHistory() throws IOException {
        executeGitCommandWithErrorHandler("git", "--no-pager", "log");
        
        if (!gitCommander.isResponseOk()) {
            return null;
        }
        
        return gitCommander.getProcessOutput().trim();
    }

    /**
     * Retrieves the commit history of the current branch as an InputStream.
     *
     * @return A InputStream pointing on the commit history.
     * @throws IOException If an error occurs during command execution.
     */
    public InputStream getCommitHistoryAsStream() throws IOException {
        executeGitCommandWithErrorHandler("git", "--no-pager", "log");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutputAsInputStream() : null;
    }
    
    /**
     * Retrieves details of a specific commit given its hash.
     *
     * @param commitHash The hash of the commit.
     * @return A String containing the details of the specified commit.
     * @throws IOException If an error occurs during command execution.
     */
    public String getCommitDetails(String commitHash) throws IOException {
        executeGitCommandWithErrorHandler("git", "show", commitHash);
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput().trim() : null;
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

