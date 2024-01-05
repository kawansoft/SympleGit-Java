package com.smoothgit.wrappers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.smoothgit.commander.GitCommander;
import com.smoothgit.util.FrameworkDebug;

public class GitBranch {

    public static boolean DEBUG = FrameworkDebug.isSet(GitBranch.class);
    
    private File projectDir = null;
    private String outputString = null;
    
    private boolean isOk = false;
    private String errorMessage;
    private Exception exception;
    
    public GitBranch(File projectDir) {
        this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");

        if (!projectDir.isDirectory()) {
            isOk = false;
            errorMessage = "The project does not exist anymore: " + projectDir;
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
            GitCommander gitCommander = new GitCommander(projectDir);
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
            GitCommander gitCommander = new GitCommander(projectDir);
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
            GitCommander gitCommander = new GitCommander(projectDir);
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
    
    public boolean gitSwitch(String branch) throws FileNotFoundException, IOException {
        // git checkout <original branch>
        GitCommander gitCommander = new GitCommander(projectDir);
        gitCommander.executeGitCommand("git", "switch", branch);
        isOk = gitCommander.isResponseOk();
        if (!isOk) {
            errorMessage = gitCommander.getProcessError();
            exception = gitCommander.getException();
        }
        return isOk;
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
