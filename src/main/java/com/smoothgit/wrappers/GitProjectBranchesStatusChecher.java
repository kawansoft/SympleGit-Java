/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smoothgit.wrappers;

import java.io.File;

/**
 *
 * @author ndepo
 */
public class GitProjectBranchesStatusChecher {

    private File projectDir; 
    private String projectBranch;
            
    /**
     * Constructor.
     * @param projectDir
     * @param activeBranch
     * @param projectBranch 
     */
    public GitProjectBranchesStatusChecher(File projectDir, String projectBranch) {
        this.projectDir = projectDir;
        this.projectBranch = projectBranch;
    }

    public boolean getActiveBranchStatus() {
        GitBranchActiveStatusFetcher gitStatusFetcher = new GitBranchActiveStatusFetcher(projectDir);
        boolean status = gitStatusFetcher.isStatusOk();  
        return status;
    }

    public boolean getProjectBranchStatus() {
        GitBranchAnotherStatusFetcher gitBranchAnotherStatusFetcher = new GitBranchAnotherStatusFetcher(projectDir, projectBranch);   
        boolean status = gitBranchAnotherStatusFetcher.isStatusOk();  
        return status;        
    }
    
}
