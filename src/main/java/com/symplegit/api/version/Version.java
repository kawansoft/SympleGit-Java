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
package com.symplegit.api.version;

/**
 * Displays the product Edition & Version
 */

public class Version {

    public static final String VENDOR = "KawanSoft SAS";
    public static final String WEB = "http://www.kawansoft.com";
    public static final String COPYRIGHT = "Copyright &copy; 2024";
    public static final String EMAIL = "contact@kawansoft.com";

    public static final String getVersion() {
	return "" + new PRODUCT();
    }

    @Override
    public String toString() {
	return getVersion();
    }

    private static final class PRODUCT {

	public static final String NAME = "SympleGit"; ;
	public static final String EDITION = "Community";
	public static final String VERSION = VersionValues.VERSION;
	public static final String DATE = VersionValues.DATE;

	@Override
	public String toString() {
	    return NAME + " - " + EDITION + " Edition"  + " - " + VERSION + " - " + DATE;
	}

    }

    /**
     * MAIN
     */

    public static void main(String[] args) {
	System.out.println(getVersion());
    }
}
