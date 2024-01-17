package com.symplegit.examples.misc;

import java.io.IOException;

import com.symplegit.api.GitCommander;
import com.symplegit.api.GitWrapper;
import com.symplegit.api.SympleGit;

/**
 * GitConfig provides functionalities for managing Git configurations.
 * It allows retrieving and setting user and global configurations.
 *
 * @author GPT-4
 */
public class GitConfig implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitConfig with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitConfig(SympleGit sympleGit) {
        this.gitCommander = sympleGit.gitCommander();
    }

    /**
     * Retrieves the user configuration.
     *
     * @return The user configuration as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getUserConfig() throws IOException {
        executeGitCommandWithErrorHandler("git", "config", "--list", "--no-pager");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
    }

    /**
     * Sets the user configuration.
     *
     * @param userName  The user name to set in the configuration.
     * @param userEmail The user email to set in the configuration.
     * @throws IOException If an error occurs during command execution.
     */
    public void setUserConfig(String userName, String userEmail) throws IOException {
        executeGitCommandWithErrorHandler("git", "config", "user.name", userName);
        executeGitCommandWithErrorHandler("git", "config", "user.email", userEmail);
    }

    /**
     * Retrieves the global configuration.
     *
     * @return The global configuration as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getGlobalConfig() throws IOException {
        executeGitCommandWithErrorHandler("git", "config", "--global", "--list", "--no-pager");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
    }

    /**
     * Sets a global configuration key-value pair.
     *
     * @param configKey   The configuration key to set.
     * @param configValue The value for the configuration key.
     * @throws IOException If an error occurs during command execution.
     */
    public void setGlobalConfig(String configKey, String configValue) throws IOException {
        executeGitCommandWithErrorHandler("git", "config", "--global", configKey, configValue);
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
