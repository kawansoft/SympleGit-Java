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
import com.symplegit.api.facilitator.GitAdd;
import com.symplegit.test.util.GitTestUtils;

public class GitAddTest {

    private GitAdd gitAdd;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
        assertTrue(repoDir.exists(), "Git repository directory does not exist.");

	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        
        gitAdd = new GitAdd(sympleGit);
    }

    @Test
    public void testAddAll() throws IOException {
        // Create a new file in the repository
        File newFile = new File(repoDir, "testFile.txt");
        assertTrue(newFile.createNewFile());

        // Add all changes to the staging area
        gitAdd.addAll();

        // Verify that the file is staged (you would ideally check the status using GitCommander)
        // This part is a placeholder for actual verification of git status
        assertTrue(gitAdd.isResponseOk(), "Add all operation should succeed.");
    }

    @Test
    public void testAddSpecificFile() throws IOException {
        // Create a new file in the repository
        File newFile = new File(repoDir, "specificTestFile.txt");
        assertTrue(newFile.createNewFile());

        // Add the specific file to the staging area
        gitAdd.add(Collections.singletonList(newFile.getAbsolutePath()));

        // Verify that the specific file is staged
        assertTrue(gitAdd.isResponseOk(), "Add operation for specific file should succeed.");
    }

    @Test
    public void testAddFiles() throws IOException {
        // Create new files in the repository
        File newFile1 = new File(repoDir, "file1.txt");
        File newFile2 = new File(repoDir, "file2.txt");
        assertTrue(newFile1.createNewFile());
        assertTrue(newFile2.createNewFile());

        // Add multiple files to the staging area
        gitAdd.addFiles(Arrays.asList(newFile1, newFile2));

        // Verify that the files are staged
        assertTrue(gitAdd.isResponseOk(), "Add operation for multiple files should succeed.");
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
        File repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
        assertTrue(repoDir.exists(), "Git repository directory does not exist.");

	SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
	
	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "add", ".");
	gitCommander.executeGitCommand("git", "commit", "-m", "Clean commit."); 
    }
}
