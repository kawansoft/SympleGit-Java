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
package com.symplegit.examples.misc;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.IOException;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitBranchRead;

/**
 *
 * @author ndepo
 */
public class GitBranchTest {
    public static void main(String[] args) throws IOException {
	// Replace this with the path to your Git repository
	String repoDirectoryPath = "I:\\_dev_sqlephant_tests\\Java";
	
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .build();
	
	GitBranchRead gitBranchRead = new GitBranchRead(sympleGit);
	System.out.println(gitBranchRead.getLocalBranches());
	System.out.println(gitBranchRead.getRemoteBranches());
    }
}
