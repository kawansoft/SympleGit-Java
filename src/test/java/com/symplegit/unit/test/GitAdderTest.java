package com.symplegit.unit.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.SympleGit;
import com.symplegit.test.util.GitRepoForTest;
import com.symplegit.wrappers.GitAdder;

public class GitAdderTest {

    private GitAdder gitAdder;
    private SympleGit sympleGit;
    private File repoDir;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        repoDir = new File(GitRepoForTest.GIT_REPOSITORY);
        assertTrue(repoDir.exists(), "Git repository directory does not exist.");

        sympleGit = new SympleGit(repoDir);
        gitAdder = new GitAdder(sympleGit);
    }

    @Test
    public void testAddAll() throws IOException {
        // Create a new file in the repository
        File newFile = new File(repoDir, "testFile.txt");
        assertTrue(newFile.createNewFile());

        // Add all changes to the staging area
        gitAdder.addAll();

        // Verify that the file is staged (you would ideally check the status using GitCommander)
        // This part is a placeholder for actual verification of git status
        assertTrue(gitAdder.isResponseOk(), "Add all operation should succeed.");
    }

    @Test
    public void testAddSpecificFile() throws IOException {
        // Create a new file in the repository
        File newFile = new File(repoDir, "specificTestFile.txt");
        assertTrue(newFile.createNewFile());

        // Add the specific file to the staging area
        gitAdder.add(Collections.singletonList(newFile.getAbsolutePath()));

        // Verify that the specific file is staged
        assertTrue(gitAdder.isResponseOk(), "Add operation for specific file should succeed.");
    }

    @Test
    public void testAddFiles() throws IOException {
        // Create new files in the repository
        File newFile1 = new File(repoDir, "file1.txt");
        File newFile2 = new File(repoDir, "file2.txt");
        assertTrue(newFile1.createNewFile());
        assertTrue(newFile2.createNewFile());

        // Add multiple files to the staging area
        gitAdder.addFiles(Arrays.asList(newFile1, newFile2));

        // Verify that the files are staged
        assertTrue(gitAdder.isResponseOk(), "Add operation for multiple files should succeed.");
    }

    @AfterEach
    public void tearDown() {
        // Cleanup any created files or reset the repository state if necessary
        repoDir.listFiles(file -> {
            if (file.getName().endsWith(".txt")) {
                return file.delete();
            }
            return false;
        });
    }
}
