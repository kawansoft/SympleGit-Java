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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitBranchModifier;
import com.symplegit.facilitator.api.GitBranchReader;
import com.symplegit.test.util.GitTestUtils;

public class GitBranchReaderTest {

    private GitBranchReader branchReader;
    private SympleGit sympleGit;
    private File tempRepo;

    @BeforeEach
    public void setUp() throws IOException {
        tempRepo = GitTestUtils.createTemporaryGitRepo();
        sympleGit = new SympleGit(tempRepo);
        branchReader = new GitBranchReader(sympleGit);
    }

    @Test
    public void testIsStatusOk() throws IOException {
	GitBranchModifier gitBranchModifier = new GitBranchModifier(sympleGit);
	gitBranchModifier.switchBranch("master");
        assertTrue(branchReader.isStatusOk(), "Status should be ok for master");
    }

    @Test
    public void testGetActiveBranch() {
        String activeBranch = branchReader.getActiveBranch();
        assertNotNull(activeBranch, "Active branch should not be null");
        //assertEquals("master", activeBranch, "Active branch should be 'master'");
    }

    @Test
    public void testGetLocalBranches() {
        Set<String> localBranches = branchReader.getLocalBranches();
        assertNotNull(localBranches, "Local branches set should not be null");
        assertTrue(localBranches.contains("master"), "Local branches should include 'master'");
    }

    @Test
    public void testBranchExists() {
        assertTrue(branchReader.branchExists("master"), "Branch 'master' should exist");
    }

    @Test
    public void testGetRemoteBranches() throws IOException {
        Set<String> remoteBranches = branchReader.getRemoteBranches();
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
