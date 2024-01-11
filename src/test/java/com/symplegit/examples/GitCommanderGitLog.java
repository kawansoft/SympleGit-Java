package com.symplegit.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.symplegit.api.GitCommander;
import com.symplegit.api.SympleGit;

public class GitCommanderGitLog {
    
    public static void main(String[] args) throws Exception {
	String repoDirectoryPath = "I:\\_dev_SimpleGit";

	Thread thread = new Thread(() -> {
	    try {
		manualCall(repoDirectoryPath);
	    } catch (IOException | InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	});
	thread.start();
	
	while(thread.isAlive()) {
	    Thread.sleep(1000);
	    System.out.println("Waiting...");
	}

	/*
	SympleGit sympleGit = new SympleGit(repoDirectoryPath);
	
	System.out.println();
	System.out.println("sympleGit.getTimeoutSeconds(): " + sympleGit.getTimeoutSeconds());
	System.out.println("sympleGit.isUseStringOutput(): " + sympleGit.isUseStringOutput());
	System.out.println();
	
	GitCommander gitCommander = new GitCommander(sympleGit);
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
        */
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
