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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.util.FrameworkDebug;

/**
 * The GitBranchRead class provides functionalities to read the status of a branch, test if a branch exists,
 * list all local branches, list all remote branches.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.

 * Usage
 * <pre> <code>
	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	GitBranchRead GitBranchRead = new GitBranchRead(sympleGit);
	
	// Call a method
	String active = GitBranchRead.getActiveBranch();
 * </code> </pre>
 * 
 * @author KawanSoft SAS
 * @author KawanSoft SAS
 */
public class GitBranchRead {

    public static boolean DEBUG = FrameworkDebug.isSet(GitBranchRead.class);
    
    private String outputString = null;
    
    private boolean isOk = false;
    private String errorMessage;
    private Exception exception;

    private SympleGit sympleGit;
    
    public GitBranchRead(SympleGit sympleGit) {
        this.sympleGit = Objects.requireNonNull(sympleGit, "sympleGit cannot be null!");

        if (!this.sympleGit.getDirectory().isDirectory()) {
            isOk = false;
            errorMessage = "The project directory does not exist anymore: " + this.sympleGit.getDirectory();
        }

        isOk = true;
    }
    
    /**
     * Says if status is "nothing to commit, working tree clean" or not
     *
     * @param projectDir
     * @return
     * @throws IOException
     */
    public boolean isStatusOk() {

        if (!isOk) {
            return false;
        }

        isOk = false;

        try {
            GitCommander gitCommander = sympleGit.gitCommander();
            gitCommander.executeGitCommand("git", "status");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getProcessError();
                exception = gitCommander.getException();
                return false;
            }

            // Ok, git status is done
            String outputString = gitCommander.getProcessOutput();
            if (outputString != null) {
                return outputString.contains("nothing to commit, working tree clean");
            } else {
                return false;
            }
        } catch (Exception theException) {
            errorMessage = theException.toString();
            exception = theException;
            return false;
        }
    }
    
    /**
     * Returns the active branch
     * @return  the active branch
     */
    public String getActiveBranch() {

        String branch = null;

        try {
            //git rev-parse --abbrev-ref HEAD
            GitCommander gitCommander = sympleGit.gitCommander();
            gitCommander.executeGitCommand("git", "rev-parse", "--abbrev-ref", "HEAD");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getProcessError();
                exception = gitCommander.getException();
                return null;
            }

            // Ok, git status is done
            outputString = gitCommander.getProcessOutput();
            if (outputString == null || outputString.isEmpty()) {
                return null;
            }

            branch = outputString.trim();

        } catch (Exception theException) {
            isOk = false;
            errorMessage = theException.toString();
            exception = theException;
        }

        return branch;
    }

    
    /**
     * Gets a set of the local branches
     * @return  a set of the local branches
     */
    public Set<String> getLocalBranches() {

        Set<String> branches = new HashSet<>();
        isOk = false;

        try {
            GitCommander gitCommander = sympleGit.gitCommander();
            gitCommander.executeGitCommand("git", "branch");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getProcessError();
                exception = gitCommander.getException();
                return branches;
            }

            // Ok, git status is done
            outputString = gitCommander.getProcessOutput();
            if (outputString == null || outputString.isEmpty()) {
                return branches;
            }

            BufferedReader stringReader = new BufferedReader(new StringReader(outputString));

            String line;
            while ((line = stringReader.readLine()) != null) {
                if (line.contains("*")) {
                    line = StringUtils.substringAfter(line, "*");
                    branches.add(line.trim());
                } else {
                    branches.add(line.trim());
                }
            }
        } catch (Exception theException) {
            isOk = false;
            errorMessage = theException.toString();
            exception = theException;
            return branches;
        }
        return branches;
    }

    
    /**
     * Says if the branch exists
     * @param branch the branch name
     * @return true if the branch exists
     */
    public boolean branchExists(String branch) {
       Set<String> branches = getLocalBranches();
       
       if ( branches == null || ! isResponseOk()) {
           return false;
       }
       
       if (branch == null) {
           return false;
       }
       
       debug("Set<String> branches: " + branches);
       
       return branches.contains(branch.trim());
    }

    /**
     * Gets a set of the remote branches only
     * @return  a set of the remote branches only
     */
    public Set<String> getRemoteBranches() throws IOException {

        Set<String> branches = new TreeSet<>();
        isOk = false;

        try {
            GitCommander gitCommander = sympleGit.gitCommander();
            gitCommander.executeGitCommand("git", "branch", "-a");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getProcessError();
                exception = gitCommander.getException();
                return branches;
            }

            // Ok, git status is done
            outputString = gitCommander.getProcessOutput();
            if (outputString == null || outputString.isEmpty()) {
                return branches;
            }

            BufferedReader stringReader = new BufferedReader(new StringReader(outputString));

            String line;
            while ((line = stringReader.readLine()) != null) {
                if (line.contains("remotes/")) {
                    line = StringUtils.substringAfterLast(line, "/");
                    line = line.trim();
                    branches.add(line);
                }
            }
        } catch (Exception theException) {
            isOk = false;
            errorMessage = theException.toString();
            exception = theException;
            return branches;
        }
        return branches;
    }
    
    public boolean isResponseOk() {
        return isOk;
    }

    public String getError() {
        return errorMessage;
    }

    public Exception getException() {
        return exception;
    }
    
    
    /**
     * Displays the specified message if the DEBUG flag is set.
     *
     * @param sMsg the debug message to display
     */
    protected void debug(String sMsg) {
        if (DEBUG) {
            System.out.println(new Date() + " " + sMsg);
        }
    }

}
