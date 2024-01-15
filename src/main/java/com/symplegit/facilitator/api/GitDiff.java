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
 * The GitDiff class is responsible for providing functionalities
 * to compare changes in a Git repository. It supports comparing differences
 * between two commits, viewing staged differences, and viewing differences
 * in a specific file.
 * <br><br>
 * Usage
 * <pre> <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();
		
	GitDiff gitDiff = new GitDiff(sympleGit);
	// Call a method
	String diff = gitDiff.getFileDiff("path/to/my/file.txt");
	
 * </code> </pre>
 * 
 * @author KawanSoft SAS
 * @author GPT-4
 */
public class GitDiff implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitDiff with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitDiff(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Gets the diff between two commits.
     *
     * @param commitHash1 The hash of the first commit.
     * @param commitHash2 The hash of the second commit.
     * @return The diff output as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getDiff(String commitHash1, String commitHash2) throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", commitHash1, commitHash2);
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
    }

    /**
     * Gets the diff of currently staged changes.
     *
     * @return The staged diff output as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getStagedDiff() throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", "--staged");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
    }
    
    /**
     * Gets the diff of currently staged changes as an InputStream.
     *
     * @return The staged diff output as an InputStream.
     * @throws IOException If an error occurs during command execution.
     */
    public InputStream getStagedDiffAsStream() throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", "--staged");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutputAsInputStream():null;
    }

    /**
     * Gets the diff for a specific file.
     *
     * @param filePath The path to the file.
     * @return The file diff output as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getFileDiff(String filePath) throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", filePath);
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
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
