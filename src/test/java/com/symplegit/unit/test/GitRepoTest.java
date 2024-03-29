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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileDeleteStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.api.facilitator.GitRepo;
import com.symplegit.test.util.GitHubTestRepos;
import com.symplegit.test.util.GitTestUtils;

/**
 * Unit tests for the GitRepo class.
 */
public class GitRepoTest {

    private GitRepo gitRepo;
    private File repoDir;
    private String existingRepoUrl;
    private String forCreateRepoUrl;

    @BeforeEach
    public void setUp() throws Exception {
	this.existingRepoUrl = GitHubTestRepos.getExistingRemoteGitHubUrl();
	this.forCreateRepoUrl = GitHubTestRepos.getForCreateRemoteGitHubUrl();
	
        repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
        SympleGit sympleGit = SympleGit.custom().setDirectory(repoDir).build();
        gitRepo = new GitRepo(sympleGit);
    }

    @Test
    public void testInitializeRepository() throws IOException {
        gitRepo.initializeRepository();
        assertTrue(gitRepo.isResponseOk());
    }

    @Test
    public void testGetRepositoryStatus() throws IOException {
        String status = gitRepo.getRepositoryStatus();
        assertNotNull(status);
        assertFalse(status.isEmpty());
    }

    @Test
    public void testAddAndRemoveRemote() throws IOException {
        String remoteName = "origin_new";
        String remoteUrl = this.forCreateRepoUrl;
        
        gitRepo.addRemote(remoteName, remoteUrl);
        assertTrue(gitRepo.isResponseOk());

        gitRepo.removeRemote(remoteName);
        assertTrue(gitRepo.isResponseOk());
    }

    // Do this test manually because it is not possible to do it in Maven
    @Test
    public void testCloneRepository() throws IOException {
	
	boolean manual = false;
	
	if (! manual) {
	    return;
	}
        String cloneUrl = existingRepoUrl;
        
        File theRepoDir = new File(GitTestUtils.createIfNotTexistsTemporaryGitRepo().getParent() + File.separator + "clone_test");
        System.out.println("TheRepoDir: " + theRepoDir.getAbsolutePath());
        
        FileDeleteStrategy.FORCE.delete(theRepoDir);
        
        System.out.println("TheRepoDir exists: " + theRepoDir.exists());
        
        theRepoDir.mkdirs();
        
        SympleGit sympleGit = SympleGit.custom().setDirectory(theRepoDir).build();
        GitRepo theGitRepo = new GitRepo(sympleGit);
        
        theGitRepo.cloneRepository(cloneUrl);
        assertTrue(theGitRepo.isResponseOk());
        
        FileDeleteStrategy.FORCE.delete(theRepoDir);
    }

    // Additional test methods for error handling and edge cases can be added here
}
