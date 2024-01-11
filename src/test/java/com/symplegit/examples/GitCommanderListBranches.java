package com.symplegit.examples;

import java.io.IOException;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;


public class GitCommanderListBranches {

    public static void main(String[] args) throws IOException {
	String repoDirectoryPath = "I:\\_dev_SimpleGit";

	SympleGit sympleGit = new SympleGit(repoDirectoryPath);
	
	GitCommander gitCommander = new GitCommander(sympleGit);
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