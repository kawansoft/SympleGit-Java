package com.symplegit.unit.test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.symplegit.GitCommander;
import com.symplegit.SympleGit;
import com.symplegit.test.util.GitRepoForTest;

public class GitCommanderTest {


    
    private File tempRepo;
    private GitCommander commander;

    @Test
    public void testSuccessfulCommandExecution() throws IOException {
        tempRepo = new File(GitRepoForTest.GIT_REPOSITORY);
        commander = new GitCommander(new SympleGit(tempRepo));
        
        commander.executeGitCommand("git", "status");
        assertTrue(commander.isResponseOk(), "Command should be executed successfully.");
        assertEquals (0, commander.getExitCode());

        String output = commander.getProcessOutput();
        assertNotNull(output, "Output should not be null for successful command.");
        assertTrue(output.contains("On branch"), "Output should contain branch information.");

        String error = commander.getProcessError();
        assertTrue(error.isEmpty(), "Error stream should be empty for successful command.");
    }

    
    @Test
    public void testCommandFailure() throws IOException {
        tempRepo = new File("I:\\_dev_SimpleGit"); //GitTestUtils.createTemporaryGitRepo();
        commander = new GitCommander(new SympleGit(tempRepo));
        commander.executeGitCommand("git", "invalid-command");
        assertTrue (! commander.isResponseOk(), "Command should fail.");
        assertNotEquals(0, commander.getExitCode());

        String error = commander.getProcessError();
        assertNotNull(error, "Error stream should not be null for failed command.");
    }
    
}
