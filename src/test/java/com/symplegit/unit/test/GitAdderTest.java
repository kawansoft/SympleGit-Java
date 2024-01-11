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
package com.symplegit.unit.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitAdder;
import com.symplegit.test.util.GitTestUtils;

public class GitAdderTest {

    private GitAdder gitAdder;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createTemporaryGitRepo();
        assertTrue(repoDir.exists(), "Git repository directory does not exist.");

	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        
        gitAdder = new GitAdder(sympleGit);
    }

    @Test
    public void testAddAll() throws IOException {
        // Create a new file in the repository
        File newFile = new File(repoDir, "testFile.txt");
        assertTrue(newFile.createNewFile());

        // Add all changes to the staging area
        gitAdder.addAll();

        // Verify that the file is staged (you would ideally check the status using GitCommander)
        // This part is a placeholder for actual verification of git status
        assertTrue(gitAdder.isResponseOk(), "Add all operation should succeed.");
    }

    @Test
    public void testAddSpecificFile() throws IOException {
        // Create a new file in the repository
        File newFile = new File(repoDir, "specificTestFile.txt");
        assertTrue(newFile.createNewFile());

        // Add the specific file to the staging area
        gitAdder.add(Collections.singletonList(newFile.getAbsolutePath()));

        // Verify that the specific file is staged
        assertTrue(gitAdder.isResponseOk(), "Add operation for specific file should succeed.");
    }

    @Test
    public void testAddFiles() throws IOException {
        // Create new files in the repository
        File newFile1 = new File(repoDir, "file1.txt");
        File newFile2 = new File(repoDir, "file2.txt");
        assertTrue(newFile1.createNewFile());
        assertTrue(newFile2.createNewFile());

        // Add multiple files to the staging area
        gitAdder.addFiles(Arrays.asList(newFile1, newFile2));

        // Verify that the files are staged
        assertTrue(gitAdder.isResponseOk(), "Add operation for multiple files should succeed.");
    }

    
    @AfterEach
    public void tearDown() {
	
        // Cleanup any created files or reset the repository state if necessary
        repoDir.listFiles(file -> {
            if (file.getName().endsWith(".txt")) {
                return file.delete();
            }
            return false;
        });
        
    }
    
    

    @AfterAll
    public static void cleanAll() throws IOException {
        File repoDir = GitTestUtils.createTemporaryGitRepo();
        assertTrue(repoDir.exists(), "Git repository directory does not exist.");

	SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
	
	GitCommander gitCommander = new GitCommander(sympleGit);
	gitCommander.executeGitCommand("git", "add", ".");
	gitCommander.executeGitCommand("git", "commit", "-m", "Clean commit."); 
    }
}
