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
 * The GitBranchModifier class provides functionalities to create, delete, and rename branches in a Git repository.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.
 * 
 * @author GPT-4
 */
public class GitBranchModifier implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitBranchModifier with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitBranchModifier(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Creates a new branch in the Git repository.
     *
     * @param branchName The name of the branch to be created.
     * @throws IOException If an error occurs during command execution.
     */
    public void createBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", branchName);
    }

    /**
     * Cautious delete of a branch from the Git repository. (-d option).
     *
     * @param branchName The name of the branch to be deleted.
     * @throws IOException If an error occurs during command execution.
     */
    public void deleteBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", "-d", branchName);
    }
    
    /**
     * Forces delete of a branch from the Git repository. (-D option).
     *
     * @param branchName The name of the branch to be deleted.
     * @throws IOException If an error occurs during command execution.
     */
    public void deleteBranchForce(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", "-D", branchName);
    }

    /**
     * Renames a branch in the Git repository.
     *
     * @param oldBranchName The current name of the branch.
     * @param newBranchName The new name for the branch.
     * @throws IOException If an error occurs during command execution.
     */
    public void renameBranch(String oldBranchName, String newBranchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", "-m", oldBranchName, newBranchName);
    }

    /**
     * Pushes a local branch to the remote repository.
     *
     * @param branchName The name of the local branch to be pushed.
     * @throws IOException If an error occurs during command execution.
     */
    public void pushBranchToRemote(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "push", "origin", branchName);
    }

    /**
     * Deletes a branch from the remote repository.
     *
     * @param branchName The name of the remote branch to be deleted.
     * @throws IOException If an error occurs during command execution.
     */
    public void deleteRemoteBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "push", "origin", "--delete", branchName);
    }
    
    /**
     * Switches to a specified branch in the Git repository.
     *
     * @param branchName The name of the branch to switch to.
     * @throws IOException If an error occurs during command execution.
     */
    public void switchBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "switch", branchName);
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
