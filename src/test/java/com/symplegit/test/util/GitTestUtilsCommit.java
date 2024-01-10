package com.symplegit.test.util;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GitTestUtilsCommit {

    /**
     * Creates an initial commit in the given Git repository.
     * 
     * @param repoDir The directory of the Git repository.
     * @throws IOException If an error occurs during file operations or command execution.
     */
    public static void makeInitialCommit(File repoDir) throws IOException {
        createFileInRepo(repoDir, "initial.txt", "Initial commit content");
        executeGitCommand(repoDir, "git", "add", ".");
        executeGitCommand(repoDir, "git", "commit", "-m", "Initial commit");
    }

    /**
     * Creates a new commit in the given Git repository on the current branch.
     * 
     * @param repoDir The directory of the Git repository.
     * @param commitMessage The commit message.
     * @throws IOException If an error occurs during file operations or command execution.
     */
    public static void makeCommit(File repoDir, String commitMessage) throws IOException {
        String filename = "file_" + System.currentTimeMillis() + ".txt";
        createFileInRepo(repoDir, filename, "Content for " + commitMessage);
        executeGitCommand(repoDir, "git", "add", filename);
        executeGitCommand(repoDir, "git", "commit", "-m", commitMessage);
    }

    private static void createFileInRepo(File repoDir, String filename, String content) throws IOException {
        File file = new File(repoDir, filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    private static void executeGitCommand(File repoDir, String... command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(repoDir);
        Process process = builder.start();

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Git command execution failed with exit code " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Git command execution interrupted", e);
        }
    }
}
