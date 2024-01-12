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
package com.symplegit.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;
import com.symplegit.test.util.GitTestUtils;

public class GitCommanderGitLog {
    
    public static void main(String[] args) throws Exception {
	String repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();

	boolean manual = false;
	
	if (manual) {
	    manualCallInThread(repoDirectoryPath);
	    return;
	}

	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDirectoryPath)
                .setTimeout(5)
                .build();
	
	System.out.println();
	System.out.println("sympleGit.getTimeout(): " + sympleGit.getTimeout());
	System.out.println();
	
	GitCommander gitCommander = sympleGit.gitCommander();
	//gitCommander.executeGitCommand("git", "--version");
	gitCommander.executeGitCommand("git", "--no-pager", "log");
	
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

    /**
     * @param repoDirectoryPath
     * @throws InterruptedException
     */
    private static void manualCallInThread(String repoDirectoryPath) throws InterruptedException {
	Thread thread = new Thread(() -> {
	try {
	    manualCall(repoDirectoryPath);
	} catch (IOException | InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	});
	thread.start();

	while (thread.isAlive()) {
	Thread.sleep(1000);
	System.out.println("Waiting...");
	}
    }

    /**
     * @param repoDirectoryPath
     * @throws IOException
     * @throws InterruptedException
     */
    public static void manualCall(String repoDirectoryPath) throws IOException, InterruptedException {
	
	ProcessBuilder builder = new ProcessBuilder("git", "--no-pager", "log");
	builder.directory(new File(repoDirectoryPath)); // Set the working directory
	builder.redirectErrorStream(true); // Combine output and error streams
	Process process = builder.start();

	// Read the combined output and error stream in a separate thread
	new Thread(() -> {
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line); // Handle the output and error line
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}).start();

	int exitCode = process.waitFor(); // Wait for the process to complete
	if (exitCode != 0) {
	    // Handle error
	}

	/*
	ProcessBuilder builder = new ProcessBuilder("git", "--no-pager", "log");
	builder.directory(new File(repoDirectoryPath)); // Set the working directory
	Process process = builder.start();

	// Read the output in a separate thread
	new Thread(() -> {
	    
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line); // Handle the output line
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            System.out.println(line); // Handle the output line
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}).start();

	int exitCode = process.waitFor(); // Wait for the process to complete
	if (exitCode != 0) {
	    // Handle error
	}
	*/
    }


}
