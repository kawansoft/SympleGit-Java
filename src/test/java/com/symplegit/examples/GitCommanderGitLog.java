package com.symplegit.examples;

import java.io.IOException;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;

public class GitCommanderGitLog {
    
    public static void main(String[] args) throws IOException {
	String repoDirectoryPath = "I:\\_dev_SimpleGit";

	SympleGit sympleGit = new SympleGit(repoDirectoryPath);
	
	System.out.println();
	System.out.println("sympleGit.getTimeoutSeconds(): " + sympleGit.getTimeoutSeconds());
	System.out.println("sympleGit.isUseStringOutput(): " + sympleGit.isUseStringOutput());
	System.out.println();
	
	GitCommander gitCommander = new GitCommander(sympleGit);
	gitCommander.executeGitCommand("git", "log");
	
	if (! gitCommander.isResponseOk()) {
	    System.out.println("An Error occured: " + gitCommander.getProcessError());
	    return;
	}
	
	// OK
	String[] lines = gitCommander.getProcessOutput().split("\n");
	for (String line : lines) {
            System.out.println(line);
        }
    }


}
