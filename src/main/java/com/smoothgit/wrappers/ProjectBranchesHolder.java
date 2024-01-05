/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smoothgit.wrappers;

/**
 *
 * @author ndepo
 */
public class ProjectBranchesHolder {
    
    /** The active/default branch */
    private String activeBranch;
    
    /** The project branch we want to make corrections on */
    private String projectBranch;
    
    /** The new branch with the corrections */
    private String correctedBranch;

    public String getActiveBranch() {
        return activeBranch;
    }

    public void setActiveBranch(String activeBranch) {
        this.activeBranch = activeBranch;
    }

    public String getProjectBranch() {
        return projectBranch;
    }

    public void setProjectBranch(String projectBranch) {
        this.projectBranch = projectBranch;
    }

    public String getCorrectedBranch() {
        return correctedBranch;
    }

    public void setCorrectedBranch(String correctedBranch) {
        this.correctedBranch = correctedBranch;
    }

    @Override
    public String toString() {
        return "ProjectBranchesHolder{" + "activeBranch=" + activeBranch + ", projectBranch=" + projectBranch + ", correctedBranch=" + correctedBranch + '}';
    }
    
    
}
