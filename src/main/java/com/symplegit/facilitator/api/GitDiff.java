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
 * 
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
