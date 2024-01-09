package com.symplegit.wrappers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

/**
 * GitAdder is a wrapper class for Git 'add' operations.
 * It allows adding all changed files, or specific files to the staging area.
 * This class implements the GitWrapper interface, using GitCommander for executing Git commands.
 * 
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
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Adds all changed files to the staging area.
     *
     * @throws IOException If an error occurs during command execution.
     */
    public void addAll() throws IOException {
        executeGitCommandWithErrorHandler("git", "add", ".");
    }

    /**
     * Adds a list of specified file paths to the staging area.
     *
     * @param files The list of file paths to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void add(List<String> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }
        for (String file : files) {
            executeGitCommandWithErrorHandler("git", "add", file);
        }
    }

    /**
     * Adds a list of File objects to the staging area.
     *
     * @param files The list of File objects to be added.
     * @throws IOException If an error occurs during command execution.
     */
    public void addFiles(List<File> files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("File list cannot be null or empty.");
        }
        for (File file : files) {
            executeGitCommandWithErrorHandler("git", "add", file.getAbsolutePath());
        }
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
