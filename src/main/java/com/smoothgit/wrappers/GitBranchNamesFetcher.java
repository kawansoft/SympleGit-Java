/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smoothgit.wrappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.smoothgit.wrappers.commander.GitCommander;
import com.smoothgit.wrappers.commander.util.FrameworkDebug;

/**
 *
 * @author ndepo
 */
public class GitBranchNamesFetcher {

    public static boolean DEBUG = FrameworkDebug.isSet(GitBranchNamesFetcher.class);
        
    public static final String ON_BRANCH = "On branch";

    private File projectDir = null;
    private String outputString = null;

    private boolean isOk = false;
    private String errorMessage;
    private Exception exception;

    /**
     * Status and branch fetcher.
     *
     * @param projectDir
     */
    public GitBranchNamesFetcher(File projectDir) {
        this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");

        if (!projectDir.isDirectory()) {
            isOk = false;
            errorMessage = "The project does not exist anymore: " + projectDir;
            return;
        }

        isOk = true;

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
     * Returns the active branch
     * @return  the active branch
     */
    public String getActiveBranch() {

        String branch = null;

        try {
            //git rev-parse --abbrev-ref HEAD
            GitCommander gitCommander = new GitCommander(projectDir);
            gitCommander.executeGitCommand("git", "rev-parse", "--abbrev-ref", "HEAD");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getError();
                exception = gitCommander.getException();
                return null;
            }

            // Ok, git status is done
            outputString = gitCommander.getOutput();
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
            GitCommander gitCommander = new GitCommander(projectDir);
            gitCommander.executeGitCommand("git", "branch");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getError();
                exception = gitCommander.getException();
                return branches;
            }

            // Ok, git status is done
            outputString = gitCommander.getOutput();
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
     * Gets a set of the remote branches only
     * @return  a set of the remote branches only
     */
    public Set<String> getRemoteBranches() throws IOException {

        Set<String> branches = new TreeSet<>();
        isOk = false;

        try {
            GitCommander gitCommander = new GitCommander(projectDir);
            gitCommander.executeGitCommand("git", "-a", "branch");

            isOk = gitCommander.isResponseOk();

            if (!isOk) {
                errorMessage = gitCommander.getError();
                exception = gitCommander.getException();
                return branches;
            }

            // Ok, git status is done
            outputString = gitCommander.getOutput();
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
