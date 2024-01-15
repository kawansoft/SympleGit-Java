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

import com.symplegit.api.SympleGit;
import com.symplegit.api.facilitator.GitAdd;
import com.symplegit.api.facilitator.GitBranchModify;
import com.symplegit.api.facilitator.GitBranchRead;
import com.symplegit.api.facilitator.GitCommit;
import com.symplegit.api.facilitator.GitDiff;
import com.symplegit.api.facilitator.GitVersion;

public class FacilitatorApiJavadoc {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {

	String repoDirectoryPath = "/path/to/my/git/repository";
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();

	 
	GitAdd gitAdd = new GitAdd(sympleGit);
	
	// Call GitAdd method
	gitAdd.addAll();

	GitBranchModify gitBranchModify = new GitBranchModify(sympleGit);
	
	// Call a method
	gitBranchModify.deleteBranch("myBranch");
	
	GitBranchRead GitBranchRead = new GitBranchRead(sympleGit);
	@SuppressWarnings("unused")
	String active = GitBranchRead.getActiveBranch();
	
	GitDiff gitDiff = new GitDiff(sympleGit);
	// Call a method
	String diff = gitDiff.getFileDiff("path/to/my/file.txt");
	
	GitCommit commit = new GitCommit(sympleGit);
	// Call a method
	commit.commitChanges("My new commit message");
	
	GitVersion gitVersion = new GitVersion(sympleGit);
	System.out.println("Git Version: " + gitVersion.getVersion());
    }

}
