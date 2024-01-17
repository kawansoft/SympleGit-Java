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

import java.io.IOException;

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * The GitMerge class provides functionalities to manage merging operations in a
 * Git repository. It implements the GitWrapper interface and uses the
 * GitCommander class to execute Git commands related to merging. <br>
 * <br>
 * Usage:
 * 
 * <pre>
 *  <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	 GitMerge gitMerge = new GitMerge(sympleGit);
	
	// Call a method
	gitMerge.mergeBranches("branch_1", "branch_2");

 * </code>
 * </pre>
 * 
 * @author KawanSoft SAS
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
