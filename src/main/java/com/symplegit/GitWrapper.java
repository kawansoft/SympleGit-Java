/*
 * This file is part of SympleGit. 
 * SympleGit: Git in Java for the rest of us                                     
 * Copyright (C) 2024,  KawanSoft SAS
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

/**
 * The GitWrapper interface defines the essential functionalities that a wrapper
 * class for Git operations should implement. This interface ensures a
 * standardized way of handling responses and errors from Git commands.
 *
 * Implementing classes are expected to provide mechanisms to determine the
 * success of Git operations, retrieve error messages, and obtain any exceptions
 * that may have occurred.
 */
public interface GitWrapper {
    /**
     * Checks whether the last executed Git command completed successfully.
     *
     * @return true if the last Git command executed successfully (e.g., exit code
     *         0), false otherwise.
     */
    public boolean isResponseOk();

    /**
     * Retrieves the error message from the last executed Git command, if any.
     *
     * @return A String containing the error message from the last Git command
     *         execution. Returns null or an empty string if there was no error.
     */
    public String getError();

    /**
     * Gets the exception that was thrown during the last executed Git command, if
     * any.
     *
     * @return An Exception object representing the exception that occurred during
     *         the last Git command execution. Returns null if no exception was
     *         thrown.
     */
    public Exception getException();
}
