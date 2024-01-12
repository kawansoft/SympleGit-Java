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
