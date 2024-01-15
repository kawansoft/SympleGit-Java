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
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.api.facilitator.GitBranchRead;
import com.symplegit.test.util.GitTestUtils;

public class GitBranchReadTest {

    private GitBranchRead gitBranchRead;
    private SympleGit sympleGit;
    private File tempRepo;

    @BeforeEach
    public void setUp() throws IOException {
        tempRepo = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
	sympleGit = SympleGit.custom()
                .setDirectory(tempRepo)
                .build();
        gitBranchRead = new GitBranchRead(sympleGit);
    }

//    @Test
//    public void testIsStatusOk() throws IOException {
//	
//	String activeBranch = gitBranchRead.getActiveBranch();
//	if (!activeBranch.equals("master")) {
//	    GitBranchModify gitBranchModifier = new GitBranchModify(sympleGit);
//	    gitBranchModifier.switchBranch("master");
//	}
//
//	assertTrue(gitBranchRead.isStatusOk(), "Status should be ok for master");
//    }

    @Test
    public void testGetActiveBranch() {
        String activeBranch = gitBranchRead.getActiveBranch();
        assertNotNull(activeBranch, "Active branch should not be null");
        //assertEquals("master", activeBranch, "Active branch should be 'master'");
    }

    @Test
    public void testGetLocalBranches() {
        Set<String> localBranches = gitBranchRead.getLocalBranches();
        assertNotNull(localBranches, "Local branches set should not be null");
        assertTrue(localBranches.contains("master"), "Local branches should include 'master'");
    }

    @Test
    public void testBranchExists() {
        assertTrue(gitBranchRead.branchExists("master"), "Branch 'master' should exist");
    }

    @Test
    public void testGetRemoteBranches() throws IOException {
        Set<String> remoteBranches = gitBranchRead.getRemoteBranches();
        assertNotNull(remoteBranches, "Remote branches set should not be null");
        // Assuming no remote branches for a new repo, this check might need to be adjusted based on setup.
        // assertTrue(remoteBranches.isEmpty(), "Remote branches should be empty for a new repository");
    }

    @AfterEach
    public void tearDown() {
        // Cleanup operations, if necessary
        tempRepo.delete();
    }
}
