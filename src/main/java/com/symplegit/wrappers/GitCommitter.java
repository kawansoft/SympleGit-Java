package com.symplegit.wrappers;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;
import java.io.IOException;

/**
 * GitCommitter provides functionality for handling Git commits.
 * It includes methods for committing changes, amending commits, and retrieving commit history.
 * This class implements the GitWrapper interface and uses GitCommander for command execution.
 */
public class GitCommitter implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitCommitter with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitCommitter(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Commits changes with the provided commit message.
     *
     * @param message The commit message.
     * @throws IOException If an error occurs during command execution.
     */
    public void commitChanges(String message) throws IOException {
        executeGitCommandWithErrorHandler("git", "commit", "-m", message);
    }

    /**
     * Amends the last commit.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void amendCommit() throws IOException {
        executeGitCommandWithErrorHandler("git", "commit", "--amend");
    }

    /**
     * Retrieves the commit history of the current branch.
     *
     * @return A String containing the commit history.
     * @throws IOException If an error occurs during command execution.
     */
    public String getCommitHistory() throws IOException {
        executeGitCommandWithErrorHandler("git", "log");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput().trim() : null;
    }

    /**
     * Retrieves details of a specific commit given its hash.
     *
     * @param commitHash The hash of the commit.
     * @return A String containing the details of the specified commit.
     * @throws IOException If an error occurs during command execution.
     */
    public String getCommitDetails(String commitHash) throws IOException {
        executeGitCommandWithErrorHandler("git", "show", commitHash);
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput().trim() : null;
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

