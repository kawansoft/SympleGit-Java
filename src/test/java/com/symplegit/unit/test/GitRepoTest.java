package com.symplegit.unit.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitRepo;
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

    @Test
    public void testCloneRepository() throws IOException {
        String cloneUrl = existingRepoUrl;
        gitRepo.cloneRepository(cloneUrl);
        assertTrue(gitRepo.isResponseOk());
    }

    // Additional test methods for error handling and edge cases can be added here
}
