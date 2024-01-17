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
 * The GitRemote class is responsible for managing remote repository operations.
 * It provides functionalities to fetch, push, pull and list remote
 * repositories. This class implements the GitWrapper interface, using
 * GitCommander for executing Git commands. <br>
 * <br>
 * Usage:
 * 
 * <pre>
 *  <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();
		
	GitRemote gitRemote = new GitRemote(sympleGit);
	
	// Call a method
	gitRemote.fetchRemote("/my/git/repository");
 * </code>
 * </pre>
 * 
 * @author KawanSoft SAS
 * @author GPT-4
 */
public class GitRemote implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitRemote with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitRemote(SympleGit sympleGit) {
	this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Adds a new remote repository.
     *
     * @param remoteName The name of the remote repository.
     * @param remoteUrl  The URL of the remote repository.
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
