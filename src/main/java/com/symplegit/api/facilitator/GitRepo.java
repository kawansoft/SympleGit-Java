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
 * The GitRepo class provides functionalities for managing a Git repository. It
 * includes methods to clone, initialize, and manage the status and  of
 * the remote repositories.
 * <br><br>
 * Usage
 * <pre> <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	GitRepo gitRepo = new GitRepo(sympleGit);
	
	// Call a method
	gitRepo.cloneRepository("https://github.com/kawansoft/SympleGit-Java");
 * </code> </pre>
 * 
 * @author KawanSoft SAS
 * @author GPT-4
 */
public class GitRepo implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitRepo with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitRepo(SympleGit sympleGit) {
	this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Clones a Git repository from the specified URL.
     *
     * @param repoUrl The URL of the Git repository to clone.
     * @throws IOException If an error occurs during command execution.
     */
    public void cloneRepository(String repoUrl) throws IOException {
	executeGitCommandWithErrorHandler("git", "clone", repoUrl);
    }

    /**
     * Initializes a new Git repository in the current directory.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void initializeRepository() throws IOException {
	executeGitCommandWithErrorHandler("git", "init");
    }

    /**
     * Gets the status of the Git repository.
     *
     * @return The status of the Git repository.
     * @throws IOException If an error occurs during command execution.
     */
    public String getRepositoryStatus() throws IOException {
	executeGitCommandWithErrorHandler("git", "status");
	return gitCommander.isResponseOk() ? gitCommander.getProcessOutput().trim() : null;
    }

    /**
     * Adds a remote to the Git repository.
     *
     * @param name The name of the remote.
     * @param url  The URL of the remote.
     * @throws IOException If an error occurs during command execution.
     */
    public void addRemote(String name, String url) throws IOException {
	executeGitCommandWithErrorHandler("git", "remote", "add", name, url);
    }

    /**
     * Removes a remote from the Git repository.
     *
     * @param name The name of the remote to remove.
     * @throws IOException If an error occurs during command execution.
     */
    public void removeRemote(String name) throws IOException {
	executeGitCommandWithErrorHandler("git", "remote", "remove", name);
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
