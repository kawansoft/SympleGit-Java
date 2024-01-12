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

public class DirParms {
    
    /**
     * Gets the user home for settings
     *
     * @return user.home/.symplegit
     */
    public static String getUserHomeProjectDir() {
        String userHomeDotAppDir = System.getProperty("user.home");
        if (!userHomeDotAppDir.endsWith(File.separator)) {
            userHomeDotAppDir += File.separator;
        }
        userHomeDotAppDir += ".symplegit";
        File tempDirFile = new File(userHomeDotAppDir);
        
        if (!tempDirFile.exists()) {
            tempDirFile.mkdirs();  
        }
        
        return userHomeDotAppDir;
    }
    
}
