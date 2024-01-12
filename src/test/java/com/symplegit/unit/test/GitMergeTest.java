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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitMerge;
import com.symplegit.test.util.GitTestUtils;

public class GitMergeTest {

    private GitMerge gitMerge;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createTemporaryGitRepo();
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
