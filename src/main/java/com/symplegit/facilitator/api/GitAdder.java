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

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * The GitAdder class allows adding all changed files, or specific files to the staging area.
 * This class implements the GitWrapper interface, using GitCommander for executing Git commands.
 * 
 * @author GPT-4
 */
public class GitAdder implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitAdder with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitAdder(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Adds all changed files to the staging area.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void addAll() throws IOException {
        executeGitCommandWithErrorHandler("git", "add", ".");
    }

    /**
     * Adds a list of specified file paths to the staging area.
     *
     * @param files The list of file paths to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void add(List<String> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }
        for (String file : files) {
            executeGitCommandWithErrorHandler("git", "add", file);
        }
    }

    /**
     * Adds a list of File objects to the staging area.
     *
     * @param files The list of File objects to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void addFiles(List<File> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }
        for (File file : files) {
            executeGitCommandWithErrorHandler("git", "add", file.getAbsolutePath());
        }
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
