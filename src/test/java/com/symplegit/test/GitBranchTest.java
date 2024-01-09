package com.symplegit.test;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.IOException;

import com.symplegit.SympleGit;
import com.symplegit.wrappers.GitBranchReader;

/**
 *
 * @author ndepo
 */
public class GitBranchTest {
    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "I:\\_dev_sqlephant_tests\\Java";

	SympleGit sympleGit = new SympleGit(new File(repoDirectoryPath), false);
	GitBranchReader gitBranchReader = new GitBranchReader(sympleGit);
	System.out.println(gitBranchReader.getLocalBranches());
	System.out.println(gitBranchReader.getRemoteBranches());
    }
}
