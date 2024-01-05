package com.smoothgit.wrappers.commander.util;

import java.io.File;

public class DirParms {

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
        tempDirFile.mkdirs();
        return userHomeDotAppDir;
    }
    
}
