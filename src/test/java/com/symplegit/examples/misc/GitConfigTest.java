/**
 * 
 */
package com.symplegit.examples.misc;

import com.symplegit.api.SympleGit;
import com.symplegit.test.util.GitTestUtils;

/**
 * @author KawanSoft
 *
 */
public class GitConfigTest {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

	String repoDirectoryPath = GitTestUtils.createIfNotTexistsTemporaryGitRepo().toString();
	final SympleGit sympleGit = SympleGit.custom()
		.setDirectory(repoDirectoryPath)
		.build();
	
	GitConfig gitConfig = new GitConfig(sympleGit);
	System.out.println("gitConfig.getGlobalConfig(): " + gitConfig.getGlobalConfig());
	System.out.println();
	
	System.out.println("gitConfig.getUserConfig()  : " + gitConfig.getUserConfig());
	System.out.println();
	gitConfig.setUserConfig("Nico de Pomereu", "ndepomereu@kawansoft.com");
	System.out.println();
	System.out.println("gitConfig.getUserConfig()  : " + gitConfig.getUserConfig());
	System.out.println();
	gitConfig.setUserConfig("Nicolas de Pomereu", "ndepomereu@kawansoft.com");
	System.out.println();
	System.out.println("gitConfig.getUserConfig()  : " + gitConfig.getUserConfig());

	
    }

}
