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
package com.symplegit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class GitProjectUtil {
    
     /**
     * Checks if the given directory is a Git repository.
     *
     * @param projectDir The File object representing the directory of the project.
     * @return true if the project is a Git repository, false otherwise.
     */
    public static boolean isGitRepository(File projectDir) {
        File gitDir = new File(projectDir, ".git");
        return gitDir.exists() && gitDir.isDirectory();
    }
    
    public static boolean isValidGitBranchName(String branchName) {
        if (branchName == null || branchName.isEmpty()) {
            return false; // Branch name should not be empty
        }

        // Check for invalid characters and patterns
        return branchName.matches("[^/][\\S]*[^/.]")
                && !branchName.contains("//")
                && !branchName.contains("@{")
                && !branchName.startsWith("-")
                && !branchName.matches(".*[\\x00-\\x1F\\x7F].*");
    }

    public static boolean isValidGitHubRepoName(String repoName) {
        if (repoName == null || repoName.isEmpty()) {
            return false; // Repo name should not be empty
        }

        // Check for invalid characters, length, and constraints on hyphens, underscores, and dots
        return repoName.matches("[a-zA-Z0-9]+([._-]?[a-zA-Z0-9]+)*");
    }

    /**
     * If the passed file is root of a Git project, this will return it's name.
     * Otw, will return repoDirectoryPath.getName()
     *
     * @param repoDirectoryPath
     * @return
     * @throws IOException
     */
    public static String getGitProjectName(File repoDirectoryPath) {

        Objects.requireNonNull(repoDirectoryPath, "repoDirectoryPath cannot be null!");

        // Set the working directory to the Git repository directory
        //ProcessBuilder processBuilder = new ProcessBuilder("git", "rev-parse", "--show-toplevel");
        //git remote get-url origin
        try {

            if (!repoDirectoryPath.exists()) {
                //throw new FileNotFoundException("Directory does not exist: " + repoDirectoryPath);
                return repoDirectoryPath.getName();
            }

            if (!repoDirectoryPath.isDirectory()) {
                return repoDirectoryPath.getName();
            }

            ProcessBuilder processBuilder = new ProcessBuilder("git", "remote", "get-url", "origin");
            processBuilder.directory(repoDirectoryPath);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String topLevelPath = reader.readLine();

            if (topLevelPath != null && !topLevelPath.isEmpty()) {
                String project = getLastElementInGitUrl(topLevelPath);
                return removeGitSuffix(project);
            } else {
                return repoDirectoryPath.getName();
            }

        } catch (IOException iOException) {
            iOException.printStackTrace();
            return repoDirectoryPath.getName();
        }
    }

    private static String getLastElementInGitUrl(String topLevelPath) {
        String project = topLevelPath;
        if (project.contains("/")) {
            project = StringUtils.substringAfterLast(project, "/");
        }
        if (project.contains("\\")) {
            project = StringUtils.substringAfterLast(project, "\\");
        }
        return project;
    }

    private static String removeGitSuffix(String project) {
        if (project.endsWith(".git")) {
            project = StringUtils.substringBeforeLast(project, ".git");
        }

        return project;
    }
}
