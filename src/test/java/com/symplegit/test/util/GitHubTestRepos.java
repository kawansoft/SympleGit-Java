package com.symplegit.test.util;

public class GitHubTestRepos {

    /**
     * Gets the preprogrammed GitHub URL for the repo
     * Adapt URL for your acces rights.
     * @return the GitHub URL for the repo.
     */
    public static String getExistingRemoteGitHubUrl() {
	return "https://github.com/kawansoft/git_test_repo.git";
    }
   
    /**
     * Gets the GitHub URL for repo creation.
     * Adapt URL for your acces rights.
     * @return the GitHub URL for a new repo creation.
     */
    public static String getForCreateRemoteGitHubUrl() {
	return "https://github.com/kawansoft/git_test_repo_create.git";
    }
    
}
