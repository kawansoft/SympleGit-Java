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
package com.symplegit.examples.misc.javadoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitAdd;
import com.symplegit.facilitator.api.GitCommit;

public class SympleGitJavadoc {

    public static void main(String[] args) throws Exception {


	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	// From there:
	// 1) Call directly a Git Comamnd

	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "--no-pager", "log");

	// Or 2) call the Facilitator API with the build in classes
	GitAdd gitAdd = new GitAdd(sympleGit);
	gitAdd.add("testFile1", "testFile2");

	GitCommit gitCommit = new GitCommit(sympleGit);
	gitCommit.commitChanges("Modified test files");

	// It's always cautious to test the output
	if (gitCommander.getSize() <= 4 * 1024 * 1024) {
	    // Small output size: use String
	    String[] lines = gitCommander.getProcessOutput().split("\n");
	    for (String line : lines) {
		System.out.println(line);
	    }
	} else {
	    // Large output size: use an InputStream
	    try (BufferedReader reader = new BufferedReader(
		    new InputStreamReader(gitCommander.getProcessOutputAsInputStream()));) {
		String line;
		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
	    }
	}

	SympleGit.deleteTempFiles();

    }

}
