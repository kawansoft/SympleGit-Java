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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.api.facilitator.GitMerge;
import com.symplegit.test.util.GitTestUtils;

public class GitMergeTest {

    private GitMerge gitMerge;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        gitMerge = new GitMerge(sympleGit);

        // Setup repository with two branches
        setupRepositoryWithBranches();
    }

    private void setupRepositoryWithBranches() throws IOException {
        GitTestUtils.makeInitialCommit(repoDir);
        GitTestUtils.createAndCheckoutBranch(repoDir, "feature-branch");
        GitTestUtils.makeCommit(repoDir, "Feature commit");
        GitTestUtils.checkoutBranch(repoDir, "master");
    }

    @Test
    public void testMergeBranches() throws IOException {
        gitMerge.mergeBranches("master", "feature-branch");
        assertTrue(gitMerge.isResponseOk(), "Merge should be successful");
    }

    /*
     * FUTURE USAGE
    @Test
    public void testAbortMerge() throws IOException {
        // Creating a conflict
        GitTestUtils.createConflict(repoDir, "feature-branch", "master");

        // Attempting merge and then aborting
        gitMerge.mergeBranches("master", "feature-branch");
        assertFalse(gitMerge.isResponseOk(), "Merge should fail due to conflict");

        gitMerge.abortMerge();
        assertTrue(gitMerge.isResponseOk(), "Abort merge should be successful");
    }
    */
    
    @Test
    public void testGetMergeStatus() throws IOException {
        String mergeStatus = gitMerge.getMergeStatus();
        assertNotNull(mergeStatus, "Should be able to retrieve merge status");
    }

    // Additional methods to clean up and delete the temporary repository could be added
}
