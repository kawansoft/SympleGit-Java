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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitBranchModify;
import com.symplegit.test.util.GitTestUtils;

public class GitBranchModifyTest {

    private GitBranchModify branchModifier;
    private SympleGit sympleGit;
    private static final String TEST_BRANCH = "testBranch";

    @BeforeEach
    public void setUp() throws IOException {
        File repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        branchModifier = new GitBranchModify(sympleGit);
    }

    @Test
    public void testCreateAndDeleteBranch() throws IOException {
	
	GitBranchModify gitBranchModify = new GitBranchModify(sympleGit);
	gitBranchModify.switchBranch("master");
	
	// Force delete of the test branch if it still exists
	branchModifier.deleteBranchForce(TEST_BRANCH);
        //assertTrue(branchModifier.isResponseOk(), "Branch deletion should be successful");
        
        // Test branch creation
        branchModifier.createBranch(TEST_BRANCH);
        
        if (!branchModifier.isResponseOk()) {
            System.out.println(branchModifier.getError());
        }
        
        assertTrue(branchModifier.isResponseOk(), "Branch creation should be successful");

        // Test branch deletion
        branchModifier.deleteBranchForce(TEST_BRANCH);
        assertTrue(branchModifier.isResponseOk(), "Branch deletion should be successful");
    }

    @Test
    public void testRenameBranch() throws IOException {
        String newBranchName = TEST_BRANCH + "_new";

        // Create a branch to rename
        branchModifier.createBranch(TEST_BRANCH);
        assertTrue(branchModifier.isResponseOk(), "Branch creation should be successful");

        // Rename the branch
        branchModifier.renameBranch(TEST_BRANCH, newBranchName);
        assertTrue(branchModifier.isResponseOk(), "Branch renaming should be successful");

        // Clean up
        branchModifier.deleteBranchForce(newBranchName);
    }

    @Test
    public void testSwitchBranch() throws IOException {
        // Ensure the test branch exists
        branchModifier.createBranch(TEST_BRANCH);
        
        // Switch to the test branch
        branchModifier.switchBranch(TEST_BRANCH);
        assertTrue(branchModifier.isResponseOk(), "Branch switching should be successful");

        // Clean up
        branchModifier.deleteBranchForce(TEST_BRANCH);
    }

    // Note: Pushing and deleting remote branches would require a remote setup and network access.
    // These tests should be conducted with caution to avoid affecting actual remote repositories.

    @AfterEach
    public void tearDown() {
        // Attempt to clean up the test branch if it still exists
        try {
            branchModifier.deleteBranchForce(TEST_BRANCH);
        } catch (IOException ignored) {
            // Ignored as this is just cleanup
        }
    }
}

