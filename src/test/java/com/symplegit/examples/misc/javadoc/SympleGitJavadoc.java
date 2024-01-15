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
import com.symplegit.facilitator.api.GitAdd;
import com.symplegit.facilitator.api.GitCommit;
import com.symplegit.facilitator.api.GitMerge;
import com.symplegit.facilitator.api.GitRemote;
import com.symplegit.facilitator.api.GitRepo;
import com.symplegit.facilitator.api.GitTag;

public class SympleGitJavadoc {

    public static void main(String[] args) throws Exception {

	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	// From there:
	// 1) Call directly a Git Command

	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "add", "testFile1", "testFile2");

	// Or 2) call the Facilitator API with the build in classes
	 
	GitAdd gitAdd = new GitAdd(sympleGit);
	gitAdd.add("testFile1", "testFile2");

	GitCommit gitCommit = new GitCommit(sympleGit);
	gitCommit.commitChanges("Modified test files"); 
	
	GitMerge gitMerge = new GitMerge(sympleGit);
	
	// Call a method
	gitMerge.mergeBranches("branch_1", "branch_2");
	
	GitRemote gitRemote = new GitRemote(sympleGit);
	
	// Call a method
	gitRemote.fetchRemote("/my/git/repository");
	
	
	GitRepo gitRepo = new GitRepo(sympleGit);
	
	// Call a method
	gitRepo.cloneRepository("https://github.com/kawansoft/SympleGit-Java");
	
	GitTag gitTag = new GitTag(sympleGit);
	
	// Call a method
	gitTag.deleteTag("myTag");
    }

}
