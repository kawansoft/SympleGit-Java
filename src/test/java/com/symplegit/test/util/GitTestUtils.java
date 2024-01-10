/*
 * This file is part of SympleGit. 
 * SympleGit: Git in Java for the rest of us.                                     
 * Copyright (C) 2024,  KawanSoft SAS.
 * (http://www.kawansoft.com). All rights reserved.                                
 *                                                                               
 * SympleGit is free software; you can redistribute it and/or                 
 * modify it under the terms of the GNU Lesser General Public                    
 * License as published by the Free Software Foundation; either                  
 * version 2.1 of the License, or (at your option) any later version.            
 *                                                                               
 * SympleGit is distributed in the hope that it will be useful,               
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU             
 * Lesser General Public License for more details.                               
 *                                                                               
 * You should have received a copy of the GNU Lesser General Public              
 * License along with this library; if not, write to the Free Software           
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  
 * 02110-1301  USA
 *
 * Any modifications to this file must keep this entire header
 * intact.
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
    public static File createTemporaryGitRepo() throws IOException {
        //C:\Users\ndepo\AppData\Local\Temp
	String tmpDirsLocation = System.getProperty("java.io.tmpdir");
        File tempDir = new File(tmpDirsLocation + File.separator + "git_test_repo");

        if (! tempDir.exists()) {
            tempDir.mkdir();
            executeGitCommand(tempDir, "git", "init");   
        }

        return tempDir;
    }
    
    /**
     * Gets the preprogrammed GitHub URL for the repo:
     * File repoDir = GitTestUtils.createTemporaryGitRepo();
     * @return the GitHub URL for the repo.
     */
    public static String getRemoteGitHubUrl() {
	return "https://github.com/kawansoft/git_test_repo.git";
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
    
    private static void createFileInRepo(File repoDir, String filename, String content) throws IOException {
        File file = new File(repoDir, filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
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
