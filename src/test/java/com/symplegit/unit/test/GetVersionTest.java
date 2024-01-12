package com.symplegit.unit.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.symplegit.api.SympleGit;
import com.symplegit.facilitator.api.GitVersion;
import com.symplegit.test.util.GitTestUtils;

public class GetVersionTest {

    private GitVersion gitVersion;
    private File repoDir;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary Git repository
        repoDir = GitTestUtils.createTemporaryGitRepo();
	final SympleGit sympleGit = SympleGit.custom()
                .setDirectory(repoDir)
                .build();
        gitVersion = new GitVersion(sympleGit);
    }

    @Test
    public void testGetVersion() throws IOException {
        // Execute the gitVersion method
        String gitVersion = this.gitVersion.getVersion();

        // Check that the returned version string is not null and contains expected content
        assertNotNull(gitVersion, "Git version should not be null");
        assert(gitVersion.contains("git version"));
    }
}
