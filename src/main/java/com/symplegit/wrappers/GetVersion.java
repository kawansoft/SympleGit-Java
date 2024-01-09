package com.symplegit.wrappers;

import java.io.IOException;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

/**
 * The GetVersion class provides the functionality to retrieve the current version of Git.
 * It implements the GitWrapper interface, using GitCommander to execute the 'git --version' command.
 */
public class GetVersion implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GetVersion instance with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GetVersion(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Retrieves the current Git version.
     *
     * @return The current Git version as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getVersion() throws IOException {
        executeGitCommandWithErrorHandler("git", "--version");

        if (gitCommander.isResponseOk()) {
            return gitCommander.getProcessOutput().trim();
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