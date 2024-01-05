package com.smoothgit.util;

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
