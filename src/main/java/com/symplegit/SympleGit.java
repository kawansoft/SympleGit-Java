/*
 * This file is part of SympleGit. 
 * SympleGit: Git in Java for the rest of us.                                     
 * Copyright (C) 2024,  KawanSoft SAS.
 * (http://www.kawansoft.com). All rights reserved.                                
 *                                                                               
 * SympleGit is free software; you can redistribute it and/or                 
 * modify it under the terms of the GNU Lesser General Public                    
 * License as published by the Free Software Foundation; either                  
 * version 2.1 of the License, or (at your option) any later version.            
 *                                                                               
 * SympleGit is distributed in the hope that it will be useful,               
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU             
 * Lesser General Public License for more details.                               
 *                                                                               
 * You should have received a copy of the GNU Lesser General Public              
 * License along with this library; if not, write to the Free Software           
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  
 * 02110-1301  USA
 *
 * Any modifications to this file must keep this entire header
 * intact.
 */
package com.symplegit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * The SympleGit class represents a Git project directory and provides
 * functionalities related to the Git operations within that directory. It is a
 * part of the SympleGit package, which aims to simplify interactions with Git
 * repositories.
 * 
 * @author Nicolas de Pomereu
 */
public class SympleGit {

    private File projectDir;
    private boolean useStringOutput = false;

    /**
     * Constructs a SympleGit instance for a given project directory. This
     * constructor ensures that the provided directory exists and is indeed a
     * directory.<br>
     * The output of each Git command will be stored in a temporary File. The
     * temporary file will be deleted after the JVM's life.
     * 
     * @param projectDir The directory of the Git project.
     * @throws FileNotFoundException if the project directory does not exist or is
     *                               not a directory.
     * @throws NullPointerException  if the projectDir argument is null.
     */
    public SympleGit(String projectDir) throws FileNotFoundException {
	Objects.requireNonNull(projectDir, "projectDir cannot be null!");

	this.projectDir = new File(projectDir);
	
	if (!this.projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}
    }
    
    /**
     * Constructs a SympleGit instance for a given project directory. This
     * constructor ensures that the provided directory exists and is indeed a
     * directory.<br>
     * The output of each Git command will be stored in a temporary File. The
     * temporary file will be deleted after the JVM's life.
     * 
     * @param projectDir The directory of the Git project.
     * @throws FileNotFoundException if the project directory does not exist or is
     *                               not a directory.
     * @throws NullPointerException  if the projectDir argument is null.
     */
    public SympleGit(File projectDir) throws FileNotFoundException {
	this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");

	if (!projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}
    }

    /**
     * Constructs a SympleGit instance for a given project directory. This
     * constructor ensures that the provided directory exists and is indeed a
     * directory.
     *
     * @param projectDir      The directory of the Git project.
     * @param useStringOutput If true, the output of each Git commands will be only
     *                        stored in a String. if false, the output of each Git
     *                        command will be stored in a temporary File. The
     *                        temporary file will be deleted after the JVM's life.
     * 
     * @throws FileNotFoundException if the project directory does not exist or is
     *                               not a directory.
     * @throws NullPointerException  if the projectDir argument is null.
     */
    public SympleGit(String projectDir, boolean useStringOutput) throws FileNotFoundException {
	Objects.requireNonNull(projectDir, "projectDir cannot be null!");
	this.useStringOutput = useStringOutput;
	this.projectDir = new File(projectDir);
	
	if (!this.projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}
    }
    
    /**
     * Constructs a SympleGit instance for a given project directory. This
     * constructor ensures that the provided directory exists and is indeed a
     * directory.
     *
     * @param projectDir      The directory of the Git project.
     * @param useStringOutput If true, the output of each Git commands will be only
     *                        stored in a String. if false, the output of each Git
     *                        command will be stored in a temporary File. The
     *                        temporary file will be deleted after the JVM's life.
     * 
     * @throws FileNotFoundException if the project directory does not exist or is
     *                               not a directory.
     * @throws NullPointerException  if the projectDir argument is null.
     */
    public SympleGit(File projectDir, boolean useStringOutput) throws FileNotFoundException {
	this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");
	this.useStringOutput = useStringOutput;

	if (!projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}
    }

    /**
     * Retrieves the project directory associated with this SympleGit instance.
     *
     * @return The File object representing the project directory.
     */
    public File getProjectDir() {
	return projectDir;
    }

    /**
     * Says if the output of each Git command will be stored in a String. If true,
     * the output of the Git commands will be stored in a String, if false, the
     * output of each Git command will be stored in a temporary File. The temporary
     * file will be deleted after the JVM's life.
     * 
     * @return true if the output of the Git commands will be stored in a String,
     *         false otherwise.
     */
    public boolean isUseStringOutput() {
	return useStringOutput;
    }

}
