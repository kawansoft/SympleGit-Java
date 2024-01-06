package com.symplegit.test;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.File;
import java.io.IOException;

import com.symplegit.GitCommander;
import com.symplegit.SympleGit;
import com.symplegit.wrappers.GitBranch;

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
        
        //git log --graph --decorate --oneline --all --simplify-by-decoration --since="1 week ago" --author="Jane Doe" --grep="feature" -p file1.txt file2.txt
        
       
        GitBranch gitBranch	= new GitBranch(sympleGit);
        System.out.println("Default branch: " + gitBranch.getActiveBranch());
        
        
    }
}
