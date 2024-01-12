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
