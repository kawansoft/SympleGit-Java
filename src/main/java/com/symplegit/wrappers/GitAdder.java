package com.symplegit.wrappers;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The GitAdder class provides functionalities to add files to a Git staging area.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.
 * @author GPT-4
 */
public class GitAdder implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitAdder with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitAdder(SympleGit sympleGit) {
	Objects.requireNonNull(sympleGit, "sympleGit cannot be null!");
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Adds all changed files in the current directory to the staging area.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void addAll() throws IOException {
        executeGitCommandWithErrorHandler("git", "add", ".");
    }

    /**
     * Adds a list of specified files to the staging area.
     *
     * @param files A list of file paths to add to the staging area.
     * @throws IOException If an error occurs during command execution.
     */
    public void add(List<String> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }

        StringJoiner joiner = new StringJoiner(" ");
        for (String file : files) {
            joiner.add(file);
        }

        executeGitCommandWithErrorHandler("git", "add", joiner.toString());
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
