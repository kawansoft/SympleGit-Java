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
package com.symplegit.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Nicolas de Pomereu
 *
 *         Allow to debug files contained in
 *         user.home/.kanwansoft/aceql-debug-server.ini.
 *
 */
public class FrameworkDebug {
    /** The file that contain the classes to debug in user.home */
    private static String DEBUG_FILE = "sqlephant-client-debug.ini";

    /** Stores the classes to debug */
    private static Set<String> CLASSES_TO_DEBUG = new HashSet<String>();

    /**
     * Protected constructor
     */
    protected FrameworkDebug() {

    }

    /**
     * Says if a class must be in debug mode
     *
     * @param clazz the class to analyze if debug must be on
     * @return true if the class must be on debug mode, else false
     */
    public static boolean isSet(Class<?> clazz) {
	load();

	String className = clazz.getName();
	String rawClassName = StringUtils.substringAfterLast(className, ".");

	return CLASSES_TO_DEBUG.contains(className)
		|| CLASSES_TO_DEBUG.contains(rawClassName);
    }

    /**
     * Load the classes to debug from the file
     *
     * @throws IOException
     */
    private static void load() {
	if (!CLASSES_TO_DEBUG.isEmpty()) {
	    return;
	}

	File userHomeAppDir = new File(DirParms.getUserHomeProjectDir());
	userHomeAppDir.mkdirs();

	String file = userHomeAppDir + File.separator + DEBUG_FILE;

	// Nothing to load if file not set
	if (!new File(file).exists()) {
	    CLASSES_TO_DEBUG.add("empty");
	    return;
	}

	try (LineNumberReader lineNumberReader = new LineNumberReader(
		new FileReader(file));) {

	    String line = null;
	    while ((line = lineNumberReader.readLine()) != null) {
		line = line.trim();

		if (line.startsWith("//") || line.startsWith("#")
			|| line.isEmpty()) {
		    continue;
		}

		CLASSES_TO_DEBUG.add(line);
	    }
	} catch (FileNotFoundException e) {
	    throw new IllegalArgumentException(
		    "Wrapped IOException. Impossible to load debug file: "
			    + file,
			    e);
	} catch (IOException e) {
	    throw new IllegalArgumentException(
		    "Wrapped IOException. Error reading debug file: " + file,
		    e);
	}
    }
   

}
