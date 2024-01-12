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

import java.io.File;
import java.util.Objects;

/**
 * SympleGit provides a fluent and simplified interface for configuring and
 * using Git operations. It utilizes a builder pattern for easy configuration
 * and initialization. It is a part of the SympleGit package, which aims to
 * simplify interactions with Git repositories.
 * 
 */
public class SympleGit {

    public static final int DEFAULT_TIMEOUT_SECONDS = 0;
    
    private final File directory;
    private int timeout = DEFAULT_TIMEOUT_SECONDS;

    /**
     * Constructs a new instance of SympleGit with the specified configuration.
     *
     * @param builder The Builder object containing configuration settings.
     */
    private SympleGit(Builder builder) {
        this.directory = builder.directory;
        this.timeout = builder.timeout;
    }

    /**
     * Creates a new Builder instance for configuring SympleGit.
     *
     * @return A new Builder instance.
     */
    public static Builder custom() {
        return new Builder();
    }

    /**
     * Gets the directory associated with this SympleGit instance.
     *
     * @return The directory as a File object.
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Gets the timeout setting for Git operations.
     *
     * @return The timeout in milliseconds.
     */
    public int getTimeout() {
        return timeout;
    }

    // Additional methods or functionality as needed

    /**
     * Builder class for SympleGit. Provides methods to configure SympleGit instances.
     */
    public static class Builder {

        private File directory;
        private int timeout;

        /**
         * Sets the directory path for the Git repository.
         *
         * @param directoryPath The path to the Git repository directory.
         * @return The Builder instance for chaining.
         */
        public Builder setDirectory(String directoryPath) {
            Objects.requireNonNull(directoryPath, "directoryPath cannot be null");
            this.directory = new File(directoryPath);
            return this;
        }

	public Builder setDirectory(File directoryFile) {
	    Objects.requireNonNull(directoryFile, "directoryFile cannot be null");
            this.directory = directoryFile;
            return this;
	}
	
        /**
         * Sets the timeout for Git operations.
         *
         * @param timeout The timeout value in milliseconds.
         * @return The Builder instance for chaining.
         */
        public Builder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Builds and returns a SympleGit instance with the current configuration.
         *
         * @return A configured SympleGit instance.
         */
        public SympleGit build() {
            return new SympleGit(this);
        }


    }

    /**
     * Creates a GitCommander instance based on the current SympleGit configuration.
     *
     * @return A new GitCommander instance.
     */
    public GitCommander gitCommander() {
	GitCommander gitCommander = new GitCommander(this);
	return gitCommander;
    }

    @Override
    public String toString() {
	return "SympleGit [directory=" + directory + ", timeout=" + timeout + "]";
    }
    
    
}
