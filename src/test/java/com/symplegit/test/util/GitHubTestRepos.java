/*
 * This file is part of SympleGit
 * SympleGit: Straightforward  Git in Java. Follows 
 *           'AI-Extensible Open Source Software' pattern
 * Copyright (C) 2024,  KawanSoft SAS
 * (http://www.kawansoft.com). All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
