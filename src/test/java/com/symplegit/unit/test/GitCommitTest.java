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
import com.symplegit.facilitator.api.GitCommit;
import com.symplegit.test.util.GitTestUtils;

public class GitCommitTest {

    private SympleGit sympleGit;
    private GitCommit gitCommit;
    private File repoDir;

    private String latestCommitHash;
    
    @BeforeEach
    void setUp() throws IOException {
        repoDir = GitTestUtils.createTemporaryGitRepo(); // Utilize existing GitTestUtils class
        
	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        
        gitCommit = new GitCommit(sympleGit);
        
    }

    
    @Test
    public void testCommitChanges() throws IOException {
	
	long now = System.currentTimeMillis();
	File file = GitTestUtils.createFileInRepo(repoDir,  now + "_ testfile.txt", "Test content"); // Create a test file in the repo
	
        GitCommander gitCommander = sympleGit.gitCommander();
        gitCommander.executeGitCommand("git", "add", ".");

        if (! gitCommander.isResponseOk()) {
            System.out.println("gitCommander.getProcessError(): " + gitCommit.getError());
        }
        
        gitCommit.commitChanges("\"Initial commit\"");

        if (! gitCommit.isResponseOk()) {
            System.out.println("gitCommit.getProcessError(): " + gitCommit.getError());
        }
        
        assertTrue(gitCommit.isResponseOk(), "Commit should be successful");
        file.delete();
        
    }
    
    
    @Test
    public void testAmendCommit() throws IOException {
	long now = System.currentTimeMillis();
	File file = GitTestUtils.createFileInRepo(repoDir,  now + "_ testfile.txt", "Test content"); // Create a test file in the repo
	
	GitCommander gitCommander = sympleGit.gitCommander();
        gitCommander.executeGitCommand("git", "add", ".");
        
        if (! gitCommander.isResponseOk()) {
            System.out.println("gitCommander.getProcessError(): " + gitCommander.getProcessError());
        }
        
        gitCommit.commitChanges("Ammend commit");
        
        if (! gitCommit.isResponseOk()) {
            System.out.println("gitCommit.getProcessError(): " + gitCommit.getError());
        }
        
        gitCommit.amendCommit("ammend message");
        
        if (! gitCommit.isResponseOk()) {
            System.out.println("gitCommander.getProcessError(): " + gitCommit.getError());
        }
                
        assertTrue(gitCommit.isResponseOk(), "Amend commit should be successful");
        file.delete();

    }
    



    
    @Test
    public void testGetCommitHistory() throws IOException {
        gitCommit.commitChanges("Initial commit");
        String commitHistory = gitCommit.getCommitHistory();
        assertNotNull(commitHistory, "Commit history should not be null");
    }

    @Test
    public void testGetCommitDetails() throws IOException {
	
        // Use GitCommander to retrieve the last two commit hashes
	GitCommander gitCommander = sympleGit.gitCommander();
        gitCommander.executeGitCommand("git", "rev-parse", "HEAD~1");
        if (gitCommander.isResponseOk()) {
            latestCommitHash = gitCommander.getProcessOutput().trim();
        }
        
        String commitDetails = gitCommit.getCommitDetails(latestCommitHash);
        assertNotNull(commitDetails, "Commit details should not be null");
    }

    @AfterEach
    void tearDown() {
        // Nothing
    }
}
