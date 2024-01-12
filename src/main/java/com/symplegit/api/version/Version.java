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
