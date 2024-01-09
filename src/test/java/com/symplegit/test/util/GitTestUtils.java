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
package com.symplegit.test.util;

import java.io.File;
import java.io.IOException;

public class GitTestUtils {

    /**
     * Creates a temporary directory that will act as a Git repository for testing.
     * @return The File object representing the created directory.
     * @throws IOException if an error occurs during directory creation.
     */
    public static File createTemporaryGitRepo() throws IOException {
	String tmpDirsLocation = System.getProperty("java.io.tmpdir");
        File tempDir = new File(tmpDirsLocation + File.separator + "git_test_repo");
        
        if (! tempDir.exists()) {
            tempDir.mkdir();
            executeGitCommand(tempDir, "git", "init");   
        }

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
