/**
 * 
 */
package com.symplegit.test;

import java.io.IOException;

import com.symplegit.GitCommander;

/**
 * @author Nicolas de Pomereu
 *
 */
public class GitCommanderTest {

    /**
     * 
     */
    public GitCommanderTest() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {


    }

    public static void printError(GitCommander gitCommander) throws IOException {
	String error = gitCommander.getProcessError();
	if (error != null && !error.isEmpty()) {
	    System.out.println(error);
	}
    }

    public static void printOutput(GitCommander gitCommander) throws IOException {
	String outputString = gitCommander.getProcessOutput();
	System.out.println(outputString);

	String error = gitCommander.getProcessError();
	if (error != null && !error.isEmpty()) {
	    System.out.println(outputString);
	}
    }
    	
}
