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

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * The GitBranchModify class provides functionalities to create, delete, and rename branches in a Git repository.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.
 * <br><br>
 * Usage
 * <pre> <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	GitBranchModify gitBranchModify = new GitBranchModify(sympleGit);
	
	// Call a method
	gitBranchModify.deleteBranch("myBranch");
 * </code> </pre>
 * 
 * @author KawanSoft SAS
 * @author GPT-4
 */
public class GitBranchModify implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitBranchModify with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitBranchModify(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
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
