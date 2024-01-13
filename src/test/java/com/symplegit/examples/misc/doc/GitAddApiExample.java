package com.symplegit.examples.misc.doc;

import java.io.IOException;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitAdd;
import com.symplegit.test.util.GitTestUtils;

public class GitAddApiExample {

    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "/path/to/my/git/repository";

	repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();
	
	// Staging files with SympleGit using GitCommander
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .build();
	
	GitAdd gitAdd = new GitAdd(sympleGit);
	gitAdd.add("testFile1", "testFile2");
	
    }
    
}
