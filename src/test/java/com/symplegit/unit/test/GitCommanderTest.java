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
        tempRepo = GitTestUtils.createTemporaryGitRepo();
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
