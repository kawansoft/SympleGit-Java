package com.symplegit.wrappers;

import java.io.IOException;

import com.symplegit.GitCommander;
import com.symplegit.GitWrapper;
import com.symplegit.SympleGit;

/**
 * The GitBranchModifier class provides functionalities to create, delete, and rename branches in a Git repository.
 * It implements the GitWrapper interface and uses the GitCommander class to execute Git commands.
 */
public class GitBranchModifier implements GitWrapper {

    private GitCommander gitCommander;
    private String errorMessage;
    private Exception exception;

    /**
     * Constructs a GitBranchModifier with a specified SympleGit instance.
     *
     * @param sympleGit The SympleGit instance to be used for Git command execution.
     */
    public GitBranchModifier(SympleGit sympleGit) {
        this.gitCommander = new GitCommander(sympleGit);
    }

    /**
     * Creates a new branch in the Git repository.
     *
     * @param branchName The name of the branch to be created.
     * @throws IOException If an error occurs during command execution.
     */
    public void createBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", branchName);
    }

    /**
     * Deletes a branch from the Git repository.
     *
     * @param branchName The name of the branch to be deleted.
     * @throws IOException If an error occurs during command execution.
     */
    public void deleteBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", "-d", branchName);
    }

    /**
     * Renames a branch in the Git repository.
     *
     * @param oldBranchName The current name of the branch.
     * @param newBranchName The new name for the branch.
     * @throws IOException If an error occurs during command execution.
     */
    public void renameBranch(String oldBranchName, String newBranchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "branch", "-m", oldBranchName, newBranchName);
    }

    /**
     * Pushes a local branch to the remote repository.
     *
     * @param branchName The name of the local branch to be pushed.
     * @throws IOException If an error occurs during command execution.
     */
    public void pushBranchToRemote(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "push", "origin", branchName);
    }

    /**
     * Deletes a branch from the remote repository.
     *
     * @param branchName The name of the remote branch to be deleted.
     * @throws IOException If an error occurs during command execution.
     */
    public void deleteRemoteBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "push", "origin", "--delete", branchName);
    }
    
    /**
     * Switches to a specified branch in the Git repository.
     *
     * @param branchName The name of the branch to switch to.
     * @throws IOException If an error occurs during command execution.
     */
    public void switchBranch(String branchName) throws IOException {
        executeGitCommandWithErrorHandler("git", "switch", branchName);
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
