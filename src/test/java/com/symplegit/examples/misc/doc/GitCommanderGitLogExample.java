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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.test.util.GitTestUtils;

public class GitCommanderGitLogExample {

    public static void main(String[] args) throws Exception {
	String repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();

	// .setTimeout(300, TimeUnit.SECONDS)

	try(final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		//.setTimeout(300, TimeUnit.SECONDS) // Process will be killed after 300 seconds
		.build();)
	{
		GitCommander gitCommander = sympleGit.gitCommander();
		gitCommander.executeGitCommand("git", "--no-pager", "log");

		if (!gitCommander.isResponseOk()) {
		    System.out.println("An Error occured: " + gitCommander.getProcessError());
		    return;
		}

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
	}

	SympleGit.deleteTempFiles();

    }

}
