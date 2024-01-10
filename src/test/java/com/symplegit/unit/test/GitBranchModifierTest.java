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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.SympleGit;
import com.symplegit.test.util.GitTestUtils;
import com.symplegit.wrappers.GitBranchModifier;

public class GitBranchModifierTest {

    private GitBranchModifier branchModifier;
    private SympleGit sympleGit;
    private static final String TEST_BRANCH = "testBranch";

    @BeforeEach
    public void setUp() throws IOException {
        File repoDir = GitTestUtils.createTemporaryGitRepo();
        sympleGit = new SympleGit(repoDir);
        branchModifier = new GitBranchModifier(sympleGit);
    }

    @Test
    public void testCreateAndDeleteBranch() throws IOException {
	
	GitBranchModifier gitBranchModifier = new GitBranchModifier(sympleGit);
	gitBranchModifier.switchBranch("master");
	
	// Force delete of the test branch if it still exists
	branchModifier.deleteBranch(TEST_BRANCH);
        //assertTrue(branchModifier.isResponseOk(), "Branch deletion should be successful");
        
        // Test branch creation
        branchModifier.createBranch(TEST_BRANCH);
        
        if (!branchModifier.isResponseOk()) {
            System.out.println(branchModifier.getError());
        }
        
        assertTrue(branchModifier.isResponseOk(), "Branch creation should be successful");

        // Test branch deletion
        branchModifier.deleteBranch(TEST_BRANCH);
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
        branchModifier.deleteBranch(newBranchName);
    }

    @Test
    public void testSwitchBranch() throws IOException {
        // Ensure the test branch exists
        branchModifier.createBranch(TEST_BRANCH);
        
        // Switch to the test branch
        branchModifier.switchBranch(TEST_BRANCH);
        assertTrue(branchModifier.isResponseOk(), "Branch switching should be successful");

        // Clean up
        branchModifier.deleteBranch(TEST_BRANCH);
    }

    // Note: Pushing and deleting remote branches would require a remote setup and network access.
    // These tests should be conducted with caution to avoid affecting actual remote repositories.

    @AfterEach
    public void tearDown() {
        // Attempt to clean up the test branch if it still exists
        try {
            branchModifier.deleteBranch(TEST_BRANCH);
        } catch (IOException ignored) {
            // Ignored as this is just cleanup
        }
    }
}
