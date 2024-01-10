package com.symplegit.wrappers;

import java.io.IOException;
import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

/**
 * The GitDiffAnalyzer class is responsible for providing functionalities
 * to compare changes in a Git repository. It supports comparing differences
 * between two commits, viewing staged differences, and viewing differences
 * in a specific file.
 * 
 * @author Nicolas de Pomereu
 * @author GPT-4
 */
public class GitDiffAnalyzer implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitDiffAnalyzer with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitDiffAnalyzer(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Gets the diff between two commits.
     *
     * @param commitHash1 The hash of the first commit.
     * @param commitHash2 The hash of the second commit.
     * @return The diff output as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getDiff(String commitHash1, String commitHash2) throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", commitHash1, commitHash2);
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
    }

    /**
     * Gets the diff of currently staged changes.
     *
     * @return The staged diff output as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getStagedDiff() throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", "--staged");
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
    }

    /**
     * Gets the diff for a specific file.
     *
     * @param filePath The path to the file.
     * @return The file diff output as a String.
     * @throws IOException If an error occurs during command execution.
     */
    public String getFileDiff(String filePath) throws IOException {
        executeGitCommandWithErrorHandler("git", "diff", filePath);
        return gitCommander.isResponseOk() ? gitCommander.getProcessOutput() : null;
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
