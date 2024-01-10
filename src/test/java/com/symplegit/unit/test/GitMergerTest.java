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

import com.symplegit.SympleGit;
import com.symplegit.test.util.GitTestUtils;
import com.symplegit.wrappers.GitMerger;

public class GitMergerTest {

    private GitMerger gitMerger;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createTemporaryGitRepo();
        sympleGit = new SympleGit(repoDir);
        gitMerger = new GitMerger(sympleGit);

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
        gitMerger.mergeBranches("master", "feature-branch");
        assertTrue(gitMerger.isResponseOk(), "Merge should be successful");
    }

    /*
     * FUTURE USAGE
    @Test
    public void testAbortMerge() throws IOException {
        // Creating a conflict
        GitTestUtils.createConflict(repoDir, "feature-branch", "master");

        // Attempting merge and then aborting
        gitMerger.mergeBranches("master", "feature-branch");
        assertFalse(gitMerger.isResponseOk(), "Merge should fail due to conflict");

        gitMerger.abortMerge();
        assertTrue(gitMerger.isResponseOk(), "Abort merge should be successful");
    }
    */
    
    @Test
    public void testGetMergeStatus() throws IOException {
        String mergeStatus = gitMerger.getMergeStatus();
        assertNotNull(mergeStatus, "Should be able to retrieve merge status");
    }

    // Additional methods to clean up and delete the temporary repository could be added
}
