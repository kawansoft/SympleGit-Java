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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitDiff;
import com.symplegit.test.util.GitTestUtils;

/**
 * Unit tests for the GitDiff class.
 */
public class GitDiffTest {

    private GitDiff diffAnalyzer;
    private SympleGit sympleGit;
    private File repoDir;

    private String latestCommitHash;
    private String secondLatestCommitHash;
    
    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo(); 
	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        diffAnalyzer = new GitDiff(sympleGit);
        
        // Use GitCommander to retrieve the last two commit hashes
        GitCommander gitCommander = sympleGit.gitCommander();
        gitCommander.executeGitCommand("git", "rev-parse", "HEAD~1");
        if (gitCommander.isResponseOk()) {
            latestCommitHash = gitCommander.getProcessOutput().trim();
        }

        gitCommander.executeGitCommand("git", "rev-parse", "HEAD~2");
        if (gitCommander.isResponseOk()) {
            secondLatestCommitHash = gitCommander.getProcessOutput().trim();
        }
    }

    @Test
    public void testGetDiff() throws IOException {
        // Ensure that we have two valid commit hashes
        assertNotNull(latestCommitHash, "Latest commit hash should not be null");
        assertNotNull(secondLatestCommitHash, "Second latest commit hash should not be null");

        String diff = diffAnalyzer.getDiff(secondLatestCommitHash, latestCommitHash);
        assertNotNull(diff, "Diff should not be null");
        // Additional assertions can be added based on the expected output
    }

    @Test
    public void testGetStagedDiff() throws IOException {
        // Mock setup: Assume there are staged changes in the temporary repo
        // Actual implementation would depend on the setup done in createTemporaryGitRepo()

        String stagedDiff = diffAnalyzer.getStagedDiff();
        assertNotNull(stagedDiff, "Staged diff should not be null");
        // Additional assertions can be added based on the expected output
    }

    @Test
    public void testGetFileDiff() throws IOException {
        // Mock setup: Assume a file with changes exists in the temporary repo
        // Actual implementation would depend on the setup done in createTemporaryGitRepo()
        // Create a new file in the repository
        File newFile = new File(repoDir, "testFile.txt");
        //assertTrue(newFile.createNewFile());
        String filePath = newFile.getAbsolutePath();

        String fileDiff = diffAnalyzer.getFileDiff(filePath);
        assertNotNull(fileDiff, "File diff should not be null");
        // Additional assertions can be added based on the expected output
    }

    // Additional methods to clean up and delete the temporary repository could be added
}
