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

import java.io.IOException;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.test.util.GitTestUtils;


public class GitCommanderListBranches {

    public static void main(String[] args) throws IOException {
	String repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();

	// List all branches of a repo and print them 
	final SympleGit sympleGit = SympleGit.custom()
	    .setDirectory(repoDirectoryPath)
	    .build();

	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "branch", "-a");

	if (! gitCommander.isResponseOk()) {
	    System.out.println("An Error occured: " + gitCommander.getProcessError());
	    return;
	}

	// OK
	String[] branches = gitCommander.getProcessOutput().split("\n");
	for (String branch : branches) {
	        System.out.println(branch);
	}
    }

}
