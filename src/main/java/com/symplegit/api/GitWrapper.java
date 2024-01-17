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

package com.symplegit.api;

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
