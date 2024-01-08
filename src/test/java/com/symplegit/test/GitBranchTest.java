package com.symplegit.test;
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
public class GitBranchTest {
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
