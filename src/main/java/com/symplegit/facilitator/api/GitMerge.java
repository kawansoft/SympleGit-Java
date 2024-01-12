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

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * The GitMerge class provides functionalities to manage merging operations in a Git repository.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands related to merging.
 * 
 * @author GPT-4
 */
public class GitMerge implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitMerge with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitMerge(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Merges the source branch into the target branch in the Git repository.
     *
     * @param targetBranch The name of the target branch.
     * @param sourceBranch The name of the source branch.
     * @throws IOException If an error occurs during command execution.
     */
    public void mergeBranches(String targetBranch, String sourceBranch) throws IOException {
        executeGitCommandWithErrorHandler("git", "checkout", targetBranch);
        executeGitCommandWithErrorHandler("git", "merge", sourceBranch);
    }

    /**
     * Aborts an ongoing merge operation in the Git repository.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void abortMerge() throws IOException {
        executeGitCommandWithErrorHandler("git", "merge", "--abort");
    }

    /**
     * Retrieves the merge status of the Git repository.
     *
     * @return A string indicating the current merge status.
     * @throws IOException If an error occurs during command execution.
     */
    public String getMergeStatus() throws IOException {
        executeGitCommandWithErrorHandler("git", "status");
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
