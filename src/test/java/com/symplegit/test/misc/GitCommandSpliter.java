/*
 * This file is part of SympleGit. 
 * SympleGit: Git in Java for the rest of us                                     
 * Copyright (C) 2024,  KawanSoft SAS
 * (http://www.kawansoft.com). All rights reserved.                                
 *                                                                               
 * SympleGit is free software; you can redistribute it and/or                 
 * modify it under the terms of the GNU Lesser General Public                    
 * License as published by the Free Software Foundation; either                  
 * version 2.1 of the License, or (at your option) any later version.            
 *                                                                               
 * SympleGit is distributed in the hope that it will be useful,               
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU             
 * Lesser General Public License for more details.                               
 *                                                                               
 * You should have received a copy of the GNU Lesser General Public              
 * License along with this library; if not, write to the Free Software           
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  
 * 02110-1301  USA
 *
 * Any modifications to this file must keep this entire header
 * intact.
 */
package com.symplegit.test.misc;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import com.symplegit.GitCommander;
import com.symplegit.SympleGit;

/**
 *
 * @author ndepo
 */
public class GitCommandSpliter {
    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "I:\\_dev_sqlephant_tests\\Java";

	File repoFile = new File(repoDirectoryPath);

	SympleGit sympleGit = new SympleGit(repoFile);

	GitCommander gitCommander = new GitCommander(sympleGit);
	gitCommander.executeGitCommand("git", "status");

	// git log --graph --decorate --oneline --all --simplify-by-decoration
	// --since="1 week ago" --author="Jane Doe" --grep="feature" -p file1.txt
	// file2.txt

	// GitBranchReader gitBranch = new GitBranchReader(sympleGit);
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
