package com.symplegit.unit.test;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.SympleGit;
import com.symplegit.test.util.GitTestUtils;
import com.symplegit.wrappers.GitRemoteManager;

/**
 * Unit tests for the GitRemoteManager class.
 */
public class GitRemoteManagerTest {

    private GitRemoteManager remoteManager;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary Git repository for testing
        repoDir = GitTestUtils.createTemporaryGitRepo();
        sympleGit = new SympleGit(repoDir);
        remoteManager = new GitRemoteManager(sympleGit);

        // Set up a remote repository for testing
        // For example, you might want to set up a test remote repository
        // This can be a local path or a URL to a remote server.
        // Ensure this remote is accessible and suitable for test purposes.
        //String testRemoteUrl = "path_or_url_to_test_remote_repo";
        //remoteManager.gitCommander.executeGitCommand("git", "remote", "add", "origin", testRemoteUrl);
    }

    @Test
    public void testFetchRemote() throws IOException {
        // Assuming 'origin' is a valid remote
        remoteManager.fetchRemote("origin");
        assertTrue(remoteManager.isResponseOk(), "Fetch operation should be successful");
    }

    @Test
    public void testPushChanges() throws IOException {
        // This test requires the remote to be set up for accepting pushes
        // and the repository to have some committed changes to push
        remoteManager.pushChanges("origin", "master");
        assertTrue(remoteManager.isResponseOk(), "Push operation should be successful");
    }

    @Test
    public void testPullChanges() throws IOException {
        // This test requires the remote to have changes that can be pulled
        remoteManager.pullChanges("origin", "master");
        
        System.out.println("remoteManager.getError(): " + remoteManager.getError());
        assertTrue(remoteManager.isResponseOk(), "Pull operation should be successful");
    }

    @Test
    public void testListRemotes() throws IOException {
        String remotes = remoteManager.listRemotes();
        assertNotNull(remotes, "Should return a list of remotes");
        assertTrue(remotes.contains("origin"), "List should contain the 'origin' remote");
    }

    // Additional methods to clean up and delete the temporary repository could be added
}
