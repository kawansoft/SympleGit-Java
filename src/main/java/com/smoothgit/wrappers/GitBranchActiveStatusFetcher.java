/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smoothgit.wrappers;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.smoothgit.wrappers.commander.GitCommander;

/**
 * Gets the status of the current branch
 * @author ndepo
 */
public class GitBranchActiveStatusFetcher {

    private File projectDir = null;

    private boolean isOk = false;
    private String errorMessage;
    private Exception exception;

    /**
     * Status and branch fetcher.
     *
     * @param projectDir
     */
    public GitBranchActiveStatusFetcher(File projectDir) {
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
                errorMessage = gitCommander.getError();
                exception = gitCommander.getException();
                return false;
            }

            // Ok, git status is done
            String outputString = gitCommander.getOutput();
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

  

    public boolean isResponseOk() {
        return isOk;
    }

    public String getError() {
        return errorMessage;
    }

    public Exception getException() {
        return exception;
    }
}
