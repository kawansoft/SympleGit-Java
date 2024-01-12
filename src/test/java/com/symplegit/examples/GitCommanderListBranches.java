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
