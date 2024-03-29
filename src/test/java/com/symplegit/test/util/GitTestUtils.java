/*
 * This file is part of SympleGit
 * SympleGit: Straightforward  Git in Java. Follows 
 *           'AI-Extensible Open Source Software' pattern
 * Copyright (C) 2024,  KawanSoft SAS
 * (http://www.kawansoft.com). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.symplegit.test.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GitTestUtils {

    /**
     * Creates a temporary directory that will act as a Git repository for testing.
     * If the directory already exists, it is supposed that the git project exists.
     * @return The File object representing the created directory.
     * @throws IOException if an error occurs during directory creation.
     */
    public static File createIfNotTexistsTemporaryGitRepo() throws IOException {
        /**
        C:\Users\ndepo\AppData\Local\Temp\git_test_repo
         */
	String tmpDirsLocation = System.getProperty("java.io.tmpdir");
        File tempDir = new File(tmpDirsLocation + File.separator + "git_test_repo");

        if (! tempDir.exists()) {
            tempDir.mkdir();
            executeGitCommand(tempDir, "git", "init");   
        }

        return tempDir;
    }
    


    public static void createAndCheckoutBranch(File repoDir, String branchName) throws IOException {
        executeGitCommand(repoDir, "git", "checkout", "-b", branchName);
    }

    public static void checkoutBranch(File repoDir, String branchName) throws IOException {
        executeGitCommand(repoDir, "git", "checkout", branchName);
    }
    
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

    public static void createConflict(File repoDir, String branch1, String branch2) throws IOException {
        checkoutBranch(repoDir, branch1);
        makeCommit(repoDir, "Commit on " + branch1);
        checkoutBranch(repoDir, branch2);
        makeCommit(repoDir, "Conflicting commit on " + branch2);
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
    
    public static File createFileInRepo(File repoDir, String filename, String content) throws IOException {
        File file = new File(repoDir, filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        
        return file;
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
