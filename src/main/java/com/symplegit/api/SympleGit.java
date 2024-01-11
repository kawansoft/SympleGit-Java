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
package com.symplegit.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.symplegit.util.DirParms;

/**
 * The SympleGit class represents a Git project directory and provides
 * functionalities related to the Git operations within that directory. It is a
 * part of the SympleGit package, which aims to simplify interactions with Git
 * repositories.
 * 
 * @author Nicolas de Pomereu
 */
public class SympleGit {

    public static final int DEFAULT_MAX_STRING_SIZE_MB = 10;
    public static final int DEFAULT_TIMEOUT_SECONDS = 0;

    private File projectDir;
    private boolean useStringOutput = false;

    private int timeoutSeconds = DEFAULT_TIMEOUT_SECONDS;

    /**
     * Constructs a SympleGit instance for a given project directory. This
     * constructor ensures that the provided directory exists and is indeed a
     * directory.<br>
     * The output of each Git command will be stored in a temporary File. The
     * temporary file will be deleted after the JVM's life.
     * 
     * @param projectDir The directory of the Git project.
     * @throws IOException          if any IOException occurs.
     * @throws NullPointerException if the projectDir argument is null.
     */
    public SympleGit(String projectDir) throws IOException {
	Objects.requireNonNull(projectDir, "projectDir cannot be null!");

	this.projectDir = new File(projectDir);

	if (!this.projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}

	loadProperties();
    }

    /**
     * Constructs a SympleGit instance for a given project directory. This
     * constructor ensures that the provided directory exists and is indeed a
     * directory.<br>
     * The output of each Git command will be stored in a temporary File. The
     * temporary file will be deleted after the JVM's life.
     * 
     * @param projectDir The directory of the Git project.
     * @throws IOException          if any IOException occurs.
     * @throws NullPointerException if the projectDir argument is null.
     */
    public SympleGit(File projectDir) throws IOException {
	this.projectDir = Objects.requireNonNull(projectDir, "projectDir cannot be null!");

	if (!projectDir.isDirectory()) {
	    throw new FileNotFoundException("The project does not exist: " + projectDir);
	}

	loadProperties();
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
     * file will be deleted after the JVM's life. <br>
     * <br>
     * This value is fetched from the property "useStringOutput" in
     * user.home/.symplegit/symplegit.properties file. If the file or property is
     * not found, the default value of false of false will be returned. <br>
     * <br>
     * It is recommended keep the default value of false and to set
     * useStringOutput=true only if you are sure the output of the Git commands are
     * short.
     * 
     * @return true if the output of the Git commands will be stored in a String,
     *         false otherwise.
     */
    public boolean isUseStringOutput() {
	return useStringOutput;
    }

    /**
     * Gets the timeoutSeconds value associated with this SympleGit instance. If the
     * timeoutSeconds value is 0, the waiting will be infinite. <br>
     * <br>
     * This value is fetched from the "timeoutSeconds" property in
     * user.home/.symplegit/symplegit.properties file. <br>
     * If the file or property is not found, the default value of 30 seconds will be
     * returned.
     * 
     * @return timeoutSeconds value associated with this SympleGit instance.
     */
    public int getTimeoutSeconds() {
	return timeoutSeconds;
    }

    private void loadProperties() throws IOException {

	File file = new File(DirParms.getUserHomeProjectDir() + File.separator + "symplegit.properties");

	if (!file.exists()) {
	    return;
	}

	Properties properties = new Properties();
	try (InputStream in = new FileInputStream(file);) {
	    properties.load(in);
	}

	String timeoutStr = properties.getProperty("timeoutSeconds", "" + DEFAULT_TIMEOUT_SECONDS);
	useStringOutput = Boolean.parseBoolean(properties.getProperty("useStringOutput", "false"));

	if (!StringUtils.isNumeric(timeoutStr)) {
	    throw new IOException(
		    "The timeoutSeconds value in user.home/.sympelgit/symplegit.properties is not numeric: "
			    + timeoutStr);
	}

	this.timeoutSeconds = Integer.parseInt(timeoutStr);

    }

}
