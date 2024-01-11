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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.symplegit.GitCommander;
import com.symplegit.SympleGit;
import com.symplegit.util.FrameworkDebug;

/**
 * The GitBranchReader class provides functionalities to read the status of a branch, test if a branch exists,
 * list all local branches, list all remote branches.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.
 * 
 * @author Nicolas de Pomereu
 */
public class GitBranchReader {

    public static boolean DEBUG = FrameworkDebug.isSet(GitBranchReader.class);
    
    private String outputString = null;
    
    private boolean isOk = false;
    private String errorMessage;
    private Exception exception;

    private SympleGit sympleGit;
    
    public GitBranchReader(SympleGit sympleGit) {
        this.sympleGit = Objects.requireNonNull(sympleGit, "sympleGit cannot be null!");

        if (!this.sympleGit.getProjectDir().isDirectory()) {
            isOk = false;
            errorMessage = "The project does not exist anymore: " + this.sympleGit.getProjectDir();
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
            GitCommander gitCommander = new GitCommander(sympleGit);
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
            GitCommander gitCommander = new GitCommander(sympleGit);
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
            GitCommander gitCommander = new GitCommander(sympleGit);
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
            GitCommander gitCommander = new GitCommander(sympleGit);
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
