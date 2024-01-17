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
package com.symplegit.examples.misc.doc;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.symplegit.api.SympleGit;
import com.symplegit.api.facilitator.GitAdd;
import com.symplegit.api.facilitator.GitCommit;
import com.symplegit.test.util.GitTestUtils;

public class GitAddApiExample {

    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "/path/to/my/git/repository";

	repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();
	
	// Staging files & commit  with SympleGit using GitAdd & GitCommit
	
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .setTimeout(5, TimeUnit.MINUTES)
                .build();
	
	GitAdd gitAdd = new GitAdd(sympleGit);
	gitAdd.add("testFile1", "testFile2");

	GitCommit gitCommit = new GitCommit(sympleGit);
	gitCommit.commitChanges("Modified test files");

	// It's recommended to test the result of the commit call:
	if (!gitCommit.isResponseOk()) {
	    System.out.println("An Error occured: " + gitCommit.getError());
	    if (gitCommit.getException() != null) {
		System.out.println("An Exception has been raised: " + gitCommit.getError());
	    }
	    return;
	}

	System.out.println("Added test files to git repository");
    }
    
}
