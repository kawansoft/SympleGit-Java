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
package com.symplegit.examples;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitAdd;
import com.symplegit.test.util.GitTestUtils;

/**
 *
 * @author ndepo
 */
public class GitCommanderGitAdd {
    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "/path/to/my/git/repository";

	repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();
	
	directApi(repoDirectoryPath);
	facilitatorApi(repoDirectoryPath);
    }

    /**
     * @param repoDirectoryPath
     */
    private static void directApi(String repoDirectoryPath) {
	
	// Staging files with SympleGit using GitCommander
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .build();
	
	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "add", "testFile", "testFile2");
    }

    private static void facilitatorApi(String repoDirectoryPath) throws IOException {

	// Staging files with SympleGit using GitAdd
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .build();

	GitAdd gitAdd = new GitAdd(sympleGit);
	gitAdd.add("testFile", "testFile2");
	
    }
}
