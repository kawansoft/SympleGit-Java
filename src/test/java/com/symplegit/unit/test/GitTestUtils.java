package com.symplegit.unit.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GitTestUtils {

    /**
     * Creates a temporary directory that will act as a Git repository for testing.
     * @return The File object representing the created directory.
     * @throws IOException if an error occurs during directory creation.
     */
    public static File createTemporaryGitRepo() throws IOException {
        File tempDir = Files.createTempDirectory("git_test_repo").toFile();
        executeGitCommand(tempDir, "git", "init");
        return tempDir;
    }

    /**
     * Executes a Git command in the specified directory.
     * @param directory The directory where the Git command will be executed.
     * @param command The Git command to execute.
     * @throws IOException if an error occurs during command execution.
     */
    private static void executeGitCommand(File directory, String... command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(directory);
        try {
            Process process = builder.start();
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while executing git command", e);
        }
    }
}
