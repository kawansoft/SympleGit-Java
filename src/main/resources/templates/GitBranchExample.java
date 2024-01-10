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
package com.symplegit.wrappers;

import java.io.IOException;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

/**
 * The GitBranchExample class provides functionalities to create a branch and get the active branch name.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.
 */
public class GitBranchExample implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitBranchModifier with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitBranchExample(SympleGit sympleGit) {
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
     * Returns the active branch in the Git repository.
     * @return  the active branch in the Git repository.
     * @throws IOException If an error occurs during command execution.
     */
    public String getActiveBranch() throws IOException {
        executeGitCommandWithErrorHandler("git", "rev-parse", "--abbrev-ref", "HEAD");
        
        if (gitCommander.isResponseOk()) {
            String outputString = gitCommander.getProcessOutput();
            if (outputString == null || outputString.isEmpty()) {
                return null;
            }

            return outputString.trim();
        }
        return null;
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
