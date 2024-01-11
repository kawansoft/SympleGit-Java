package com.symplegit.unit.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitCommitter;
import com.symplegit.test.util.GitTestUtils;

public class GitCommitterTest {

    private SympleGit sympleGit;
    private GitCommitter gitCommitter;
    private File repoDir;

    private String latestCommitHash;
    
    @BeforeEach
    void setUp() throws IOException {
        repoDir = GitTestUtils.createTemporaryGitRepo(); // Utilize existing GitTestUtils class
        
	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        
        gitCommitter = new GitCommitter(sympleGit);
        
    }

    
    @Test
    public void testCommitChanges() throws IOException {
	
	long now = System.currentTimeMillis();
	File file = GitTestUtils.createFileInRepo(repoDir,  now + "_ testfile.txt", "Test content"); // Create a test file in the repo
	
        GitCommander gitCommander = new GitCommander(sympleGit);
        gitCommander.executeGitCommand("git", "add", ".");

        if (! gitCommander.isResponseOk()) {
            System.out.println("gitCommander.getProcessError(): " + gitCommitter.getError());
        }
        
        gitCommitter.commitChanges("\"Initial commit\"");

        if (! gitCommitter.isResponseOk()) {
            System.out.println("gitCommitter.getProcessError(): " + gitCommitter.getError());
        }
        
        assertTrue(gitCommitter.isResponseOk(), "Commit should be successful");
        file.delete();
        
    }
    
    
    @Test
    public void testAmendCommit() throws IOException {
	long now = System.currentTimeMillis();
	File file = GitTestUtils.createFileInRepo(repoDir,  now + "_ testfile.txt", "Test content"); // Create a test file in the repo
	
        GitCommander gitCommander = new GitCommander(sympleGit);
        gitCommander.executeGitCommand("git", "add", ".");
        
        if (! gitCommander.isResponseOk()) {
            System.out.println("gitCommander.getProcessError(): " + gitCommander.getProcessError());
        }
        
        gitCommitter.commitChanges("Ammend commit");
        
        if (! gitCommitter.isResponseOk()) {
            System.out.println("gitCommitter.getProcessError(): " + gitCommitter.getError());
        }
        
        gitCommitter.amendCommit("ammend message");
        
        if (! gitCommitter.isResponseOk()) {
            System.out.println("gitCommander.getProcessError(): " + gitCommitter.getError());
        }
                
        assertTrue(gitCommitter.isResponseOk(), "Amend commit should be successful");
        file.delete();

    }
    



    
    @Test
    public void testGetCommitHistory() throws IOException {
        gitCommitter.commitChanges("Initial commit");
        String commitHistory = gitCommitter.getCommitHistory();
        assertNotNull(commitHistory, "Commit history should not be null");
    }

    @Test
    public void testGetCommitDetails() throws IOException {
	
        // Use GitCommander to retrieve the last two commit hashes
        GitCommander gitCommander = new GitCommander(sympleGit);
        gitCommander.executeGitCommand("git", "rev-parse", "HEAD~1");
        if (gitCommander.isResponseOk()) {
            latestCommitHash = gitCommander.getProcessOutput().trim();
        }
        
        String commitDetails = gitCommitter.getCommitDetails(latestCommitHash);
        assertNotNull(commitDetails, "Commit details should not be null");
    }

    @AfterEach
    void tearDown() {
        // Nothing
    }
}
