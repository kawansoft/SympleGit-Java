/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smoothgit.wrappers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import com.smoothgit.wrappers.commander.GitCommander;
import com.smoothgit.wrappers.commander.util.FrameworkDebug;

/**
 * Gets the gitStatus of another projectBranch.
 * It is supposed that the status of the current branch is OK.
 *
 * @author ndepo
 */
public class GitBranchAnotherStatusFetcher {

    public static boolean DEBUG = FrameworkDebug.isSet(GitBranchAnotherStatusFetcher.class);
    
    private File projectDir = null;
    private String projectBranch = null;

    private boolean isOk = false;
    private String errorMessage;
    private Exception exception;
    private String activeBranch;

    /**
     * Status and projectBranch fetcher.
     *
     * @param projectDir
     */
    public GitBranchAnotherStatusFetcher(File projectDir, String projectBranch) {
        this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");

        if (!projectDir.isDirectory()) {
            isOk = false;
            errorMessage = "The project does not exist anymore: " + projectDir;
            return;
        }

        if (projectBranch == null) {
            isOk = false;
            errorMessage = "The project branch is null! ";
            return;
        }

        this.projectBranch = projectBranch;

        GitBranchNamesFetcher gitBranchsFetcher = new GitBranchNamesFetcher(projectDir);
        activeBranch = gitBranchsFetcher.getActiveBranch();
        if (!gitBranchsFetcher.isResponseOk()) {
            isOk = false;
            debug("Can not get active branch name: " + gitBranchsFetcher.getError());
            errorMessage = gitBranchsFetcher.getError();
            exception = gitBranchsFetcher.getException();
            return;
        }
        
        debug("Active branch : " + activeBranch);
        debug("Project branch: " + projectBranch);
                      
        if (activeBranch != null && activeBranch.equals(projectBranch)) {
            isOk = true;
            return;
        }
         
        gitBranchsFetcher = new GitBranchNamesFetcher(projectDir);
        boolean branchExists = gitBranchsFetcher.branchExists(projectBranch);

        if (!gitBranchsFetcher.isResponseOk()) {
            isOk = false;
            errorMessage = gitBranchsFetcher.getError();
            exception = gitBranchsFetcher.getException();
            debug("Can't fetch project branch name: " + gitBranchsFetcher.getError());
            return;
        }

        if (!branchExists) {
            isOk = false;
            errorMessage = "The branch does not exist: " + this.projectBranch;
            debug(errorMessage);
            return;
        }

        //debug("here 0");
        isOk = true;

    }

    /**
     * Says if status is "nothing to commit, working tree clean" or not, on the
     * branch. This means we have to do the sequence to leave clean user env:
     * <pre>
        git git stash push -m "Temporary gitSwitch"
        git checkout master_sql_rewritten
        git projectBranchStatus
        git checkout master
        git stash pop
      </pre> *
     *
     */
    public boolean isStatusOk() {
    
        // We suppose the active branch is OK.
        if (activeBranch != null && activeBranch.equals(projectBranch)) {
            isOk = true;
            return true;
        }
        
        //debug("here 01");
                   
        if (!isOk) {
            return false;
        }

        isOk = false;
        boolean projectBranchStatus  = false;
        try {
            
            //debug("here 1");
          
            if (! gitSwitch(projectBranch)) {
                debug("can not switch to " + projectBranch + " error: " + errorMessage);
                return false;
            }
 
            // git Status (project branch)
            GitBranchActiveStatusFetcher gitCurrentBranchStatusFetcher = new GitBranchActiveStatusFetcher(projectDir);
            projectBranchStatus = gitCurrentBranchStatusFetcher.isStatusOk();
            
            if (! gitCurrentBranchStatusFetcher.isResponseOk()) {
                errorMessage = gitCurrentBranchStatusFetcher.getError();
                exception = gitCurrentBranchStatusFetcher.getException();
                return false;
            }
            
            if (! gitSwitch(activeBranch)) {
                debug("can not switch to " + activeBranch + " error: " + errorMessage);
                return false;
            }
            
            return projectBranchStatus;
            
        } catch (Exception theException) {
            errorMessage = theException.toString();
            exception = theException;
            return false;
        }
        
    }


    public boolean gitSwitch(String branch) throws FileNotFoundException, IOException {
        // git checkout <original branch>
        GitCommander gitCommander = new GitCommander(projectDir);
        gitCommander.executeGitCommand("git", "switch", branch);
        isOk = gitCommander.isResponseOk();
        if (!isOk) {
            errorMessage = gitCommander.getError();
            exception = gitCommander.getException();
        }
        return isOk;
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
    protected static void debug(String sMsg) {
        if (DEBUG) {
            System.out.println(new Date() + " " + GitBranchAnotherStatusFetcher.class.getSimpleName() + " " + sMsg);
        }
    }
}
