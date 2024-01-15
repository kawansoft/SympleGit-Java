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

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;

public class SympleGitJavadoc {

    public static void main(String[] args) throws Exception {

	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	String branchName = "myNewBranch";
	
	// Create gitCommander instance from SympleGit & create a branch
	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "branch", branchName);
	
	// Test all is OK:
	if (gitCommander.isResponseOk()) {
	    System.out.println("Branch " + branchName + " successfully created!");
	}
	else {
	    String error = gitCommander.getProcessError();
	    System.out.println("Could not create branch: " +error);
	}
	
	// Get the reult of a command
	gitCommander.executeGitCommand("git", "branch", branchName);

    }

}
