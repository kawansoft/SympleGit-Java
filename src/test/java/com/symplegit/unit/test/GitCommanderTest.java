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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.test.util.GitTestUtils;

public class GitCommanderTest {


    
    private File tempRepo;
    private GitCommander commander;

    @Test
    public void testSuccessfulCommandExecution() throws IOException {
        tempRepo = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
	SympleGit sympleGit = SympleGit.custom()
                .setDirectory(tempRepo)
                .build();
	
	commander = sympleGit.gitCommander();
        
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
	SympleGit sympleGit = SympleGit.custom()
                .setDirectory(tempRepo)
                .build();
	
        commander = sympleGit.gitCommander();
        commander.executeGitCommand("git", "invalid-command");
        assertTrue (! commander.isResponseOk(), "Command should fail.");
        assertNotEquals(0, commander.getExitCode());

        String error = commander.getProcessError();
        assertNotNull(error, "Error stream should not be null for failed command.");
    }
    
}
