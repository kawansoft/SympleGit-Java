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
package com.symplegit.examples.misc;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;
import java.util.StringTokenizer;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;

/**
 *
 * @author ndepo
 */
public class GitCommandSpliter {
    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "I:\\_dev_sqlephant_tests\\Java";

	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .build();

	GitCommander gitCommander = sympleGit.gitCommander();
	gitCommander.executeGitCommand("git", "status");

	// git log --graph --decorate --oneline --all --simplify-by-decoration
	// --since="1 week ago" --author="Jane Doe" --grep="feature" -p file1.txt
	// file2.txt

	// GitBranchRead gitBranch = new GitBranchRead(sympleGit);
	// System.out.println("Default branch: " + gitBranch.getActiveBranch());

	String string = "git commit -m \"test commit\" -b \"test2 commit2\" -c --author=\"test2 commit2\" ";

	string = "git commit -m \"Your commit message here\" --author=\"Your2 commit2 message2 here2\" toto tata";

//	String[] array = string.split("\"");
//
//	List<String> list = new ArrayList<String>();
//	for (String string2 : array) {
//	    list.add(string2);
//	    System.out.println(string2);
//	}
//
//	List<String> newList = new ArrayList<String>();
//	for (String listElement : list) {
//	    if (listElement.contains(" ")) {
//		newList.add("\"" + listElement + "\"");
//	    } else {
//		newList.add(listElement);
//	    }
//	}
//
//	String result = "";
//	for (String string2 : newList) {
//	    result += string2;
//	}
//
//	System.out.println();
//	result = result.replace("\"\"", "xxxxxxxxxxxxxxxxxxxxxx");
//	result = result.replace("\"", "");
//	result = result.replace("xxxxxxxxxxxxxxxxxxxxxx", "\"");
//	System.out.println(result);
//
//	System.out.println();
	
	StringTokenizer tokenizer = new StringTokenizer(string, " ", true);
	while (tokenizer.hasMoreTokens()) {
	    System.out.println(tokenizer.nextToken());
	}

    }
}
