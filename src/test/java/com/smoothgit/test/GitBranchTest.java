package com.smoothgit.test;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.File;
import java.io.IOException;

import com.smoothgit.wrappers.GitBranch;

/**
 *
 * @author ndepo
 */
public class GitBranchTest {
        public static void main(String[] args) throws IOException {
        // Replace this with the path to your Git repository
        String repoDirectoryPath = "I:\\_dev_sqlephant_tests\\Java";

        File repoFile = new File(repoDirectoryPath);
        GitBranch gitBranch	= new GitBranch(repoFile);
        System.out.println("Default branch: " + gitBranch.getActiveBranch());
    }
}
