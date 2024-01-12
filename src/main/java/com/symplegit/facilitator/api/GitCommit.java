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
 * GitCommit provides functionality for handling Git commits.
 * It includes methods for committing changes, amending commits, and retrieving commit history.
 * This class implements the GitWrapper interface and uses GitCommander for command execution.
 * 
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
     *
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
        return gitCommander.isResponseOk() ? gitCommander.getProcessErrorAsInputStream() : null;
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

