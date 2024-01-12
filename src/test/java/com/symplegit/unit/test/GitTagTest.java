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
import com.symplegit.facilitator.api.GitTag;
import com.symplegit.test.util.GitTestUtils;

/**
 * Unit tests for the GitTag class.
 */
public class GitTagTest {

    private GitTag gitTag;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
	sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        gitTag = new GitTag(sympleGit);
    }

    @Test
    public void testCreateTag() throws IOException {
        String tagName = "test-tag";
        String commitHash = "HEAD"; // Assuming there's at least one commit in the repo

        gitTag.createTag(tagName, commitHash);
        assertTrue(gitTag.isResponseOk(), "Tag creation should be successful");
    }

    @Test
    public void testDeleteTag() throws IOException {
        String tagName = "test-tag";
        gitTag.createTag(tagName, "HEAD"); // Create a tag to delete

        gitTag.deleteTag(tagName);
        assertTrue(gitTag.isResponseOk(), "Tag deletion should be successful");
    }

    @Test
    public void testListTags() throws IOException {
        String output = gitTag.listTags();
        assertNotNull(output, "Should return a list of tags");
    }

    // Additional methods to clean up and delete the temporary repository could be added
}
