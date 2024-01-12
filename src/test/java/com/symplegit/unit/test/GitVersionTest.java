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

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitVersion;
import com.symplegit.test.util.GitTestUtils;

public class GitVersionTest {

    private GitVersion gitVersion;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary Git repository
        repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        gitVersion = new GitVersion(sympleGit);
    }

    @Test
    public void testGetVersion() throws IOException {
        // Execute the gitVersion method
        String gitVersion = this.gitVersion.getVersion();

        // Check that the returned version string is not null and contains expected content
        assertNotNull(gitVersion, "Git version should not be null");
        assert(gitVersion.contains("git version"));
    }
}
