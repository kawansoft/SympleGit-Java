/*
 * This file is part of SympleGit. 
 * SympleGit: Git in Java for the rest of us                                     
 * Copyright (C) 2024,  KawanSoft SAS
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
