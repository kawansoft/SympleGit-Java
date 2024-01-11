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

import java.io.IOException;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

/**
 * The GitRemoteManager class is responsible for managing remote repository operations.
 * It provides functionalities to fetch, push, pull and list remote repositories.
 * This class implements the GitWrapper interface, using GitCommander for executing Git commands.
 * 
 * @author Nicolas de Pomereu
 * @author GPT-4
 */
public class GitRemoteManager implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitRemoteManager with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitRemoteManager(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Adds a new remote repository.
     *
     * @param remoteName The name of the remote repository.
     * @param remoteUrl The URL of the remote repository.
     * @throws IOException If an error occurs during command execution.
     */
    public void addRemote(String remoteName, String remoteUrl) throws IOException {
        executeGitCommandWithErrorHandler("git", "remote", "add", remoteName, remoteUrl);
    }
    
    /**
     * Fetches updates from a specified remote repository.
     *
     * @param remoteName The name of the remote repository.
     * @throws IOException If an error occurs during command execution.
     */
    public void fetchRemote(String remoteName) throws IOException {
        executeGitCommandWithErrorHandler("git", "fetch", remoteName);
    }

    /**
     * Pushes changes to a specified remote repository and branch.
     *
     * @param remoteName The name of the remote repository.
     * @param branchName The name of the branch to push changes to.
     * @throws IOException If an error occurs during command execution.
     */
    public void pushChanges(String remoteName, String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "push", remoteName, branchName);
    }

    /**
     * Pulls changes from a specified remote repository and branch.
     *
     * @param remoteName The name of the remote repository.
     * @param branchName The name of the branch to pull changes from.
     * @throws IOException If an error occurs during command execution.
     */
    public void pullChanges(String remoteName, String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "pull", remoteName, branchName);
    }

    /**
     * Lists all remote repositories configured.
     *
     * @return The list of configured remote repositories.
     * @throws IOException If an error occurs during command execution.
     */
    public String listRemotes() throws IOException {
        executeGitCommandWithErrorHandler("git", "remote", "-v");
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
