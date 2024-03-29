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
 *
 *         Allow to debug files contained in
 *         user.home/.symplegit/symplegit-debug-server.ini.
 *
 */
public class FrameworkDebug {
    /** The file that contain the classes to debug in user.home */
    private static String DEBUG_FILE = "symplegit-debug.ini";

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
