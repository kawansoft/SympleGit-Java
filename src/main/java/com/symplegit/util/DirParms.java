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

public class DirParms {

    /**
     * Gets the user home for settings
     *
     * @return user.home/.smoothgit
     */
    public static String getUserHomeProjectTempDir() {
	String tempDir = getUserHomeProjectDir() + File.separator + "temp";
        File tempDirFile = new File(tempDir);
        
        if (!tempDirFile.exists()) {
            tempDirFile.mkdirs();
        }
        
        tempDirFile.mkdirs();
        return tempDir;
    }
    
    
    /**
     * Gets the user home for settings
     *
     * @return user.home/.smoothgit
     */
    public static String getUserHomeProjectDir() {
        String userHomeDotAppDir = System.getProperty("user.home");
        if (!userHomeDotAppDir.endsWith(File.separator)) {
            userHomeDotAppDir += File.separator;
        }
        userHomeDotAppDir += ".smoothgit";
        File tempDirFile = new File(userHomeDotAppDir);
        
        if (!tempDirFile.exists()) {
            tempDirFile.mkdirs();  
        }
        
        return userHomeDotAppDir;
    }
    
}
