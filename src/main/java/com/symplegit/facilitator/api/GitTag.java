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
 * The GitTag class provides functionalities to manage tags in a Git repository.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands related to tagging.
 * 
 * @author GPT-4
 */
public class GitTag implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitTag with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitTag(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Creates a new tag in the Git repository.
     *
     * @param tagName The name of the tag to be created.
     * @param commitHash The commit hash to which the tag should be attached.
     * @throws IOException If an error occurs during command execution.
     */
    public void createTag(String tagName, String commitHash) throws IOException {
        executeGitCommandWithErrorHandler("git", "tag", tagName, commitHash);
    }

    /**
     * Deletes a tag from the Git repository.
     *
     * @param tagName The name of the tag to be deleted.
     * @throws IOException If an error occurs during command execution.
     */
    public void deleteTag(String tagName) throws IOException {
        executeGitCommandWithErrorHandler("git", "tag", "-d", tagName);
    }

    /**
     * Lists all tags in the Git repository.
     *
     * @return A string containing all the tags.
     * @throws IOException If an error occurs during command execution.
     */
    public String listTags() throws IOException {
        executeGitCommandWithErrorHandler("git", "tag");
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
