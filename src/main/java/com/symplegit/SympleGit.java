/**
 * 
 */
package com.symplegit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * @author Nicolas de Pomereu
 *
 */
public class SympleGit {

    private File projectDir;

    public SympleGit(File projectDir) throws FileNotFoundException {
	this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");

	if (!projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}
    }

    /**
     * @return the projectDir
     */
    public File getProjectDir() {
        return projectDir;
    }
    
    
    
  
}
