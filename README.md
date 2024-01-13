# 

# SympleGit v1.0 - January 12, 2024

<img src="https://www.symplegit.com/img/arrow_fork2.png" />

A simple Git wrapper in Java, easily extendable with Artificial Intelligence.

## What is SympleGit?

SympleGit is a minimalist yet robust and expandable Java implementation of Git, characterized by three main features:

1. It supports direct calls corresponding to Git command line operations.
2. It includes wrapper classes for primary Git actions, with straightforward and easy-to-use names.
3. It is extendable and customizable using Artificial Intelligence, adhering to the AI-XOSS (AI-Extendable Open Source Software) pattern. More details on this will follow.

## Why Choose SympleGit Over JGit?

[JGit](https://www.eclipse.org/jgit/) is an excellent Java implementation of Git, richly featured and well-regarded for its clean and fluent API.

Designed primarily for complete Git support within Eclipse, JGit implements all the sophisticated actions required by end-users developing Java projects.

For those looking to develop a Java editor and integrate Git management, [JGit is a recommended choice](https://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit).

However, JGit's API comes with a learning curve and lacks direct, one-to-one support for CLI actions. Therefore, SympleGit is likely to be a more straightforward option for simple Git integration in many Java projects, particularly those utilizing basic Git functionalities. Let's delve into the details!



------

## Choosing SympleGit Over JGit: Understanding the Advantages

### Support for CLI Git Calls

Using JGit for staging files typically requires utilizing the API:

```java
// Staging files with JGIT

final File localPath;

// Prepare a new test-repository. 
// This uses https://github.com/centic9/jgit-cookbook tutorial

try (Repository repository = CookbookHelper.createNewRepository()) {
    localPath = repository.getWorkTree();

    try (Git git = new Git(repository)) {
 		// Files existence tests skipped for the sake of clarity
        
        // run the add-call
        git.add()
            .addFilepattern("testfile")
			.addFilepattern("testFile2")
            .call();

        System.out.println("Added file " + myFile + " to repository at " + repository.getDirectory());
    }
}
```

In contrast, SympleGit offers two straightforward options:

- Direct invocation using the `GitCommander` class with command-line interface (CLI) syntax, such as: `git add testfile`.
- Utilizing the Facilitator API with the `GitAdder` class.

The code for direct invocation is simpler:

```java
// Staging files with SympleGit using GitCommander
String repoDirectoryPath = "/path/to/my/git/repository";

final SympleGit sympleGit = SympleGit.custom()
    .setDirectory(repoDirectoryPath)
    .build();

// Files existence tests skipped for the sake of clarity

GitCommander gitCommander = sympleGit.gitCommander();
// Well, git add testfile testFile2 ;-)
gitCommander.executeGitCommand("git", "add", "testFile", "testFile2"); 
```

Alternatively, you can use the `GitAdder` class:

```java
// Staging files with SympleGit using GitAdder
String repoDirectoryPath = "/path/to/my/git/repository";

final SympleGit sympleGit = SympleGit.custom()
            .setDirectory(repoDirectoryPath)
            .build();

GitAdder gitAdder = new GitAdder(sympleGit);
gitAdder.add("testFile", "testFile2");
```

The one-to-one correspondence with `GitCommander` offers significant advantages:

- It simplifies the process for those not well-versed in Git, allowing them to execute necessary Git commands without errors or spending time searching for the correct API parameters. This makes it easier for Git experts to assist non-experts in implementing accurate Git commands. Conversely, it's also convenient for Git experts who are less familiar with Java to integrate Git calls into a Java workflow.
- Given Git's complexity, the ability to directly use CLI commands in Java enables users to execute complex or uncommon commands without the need to program every option in the API. An example is using detailed Git log commands like `git log --graph --abbrev-commit --decorate --date=relative --all --pretty=format:'%h - %ar | %s (%an)%d' --max-count=10`.

### Straightforward Git Implementation

If you're not developing a Java Editor, a more straightforward Git implementation that covers all the basic commands could be more suitable and easier to use.

As a Java developer, a common scenario involves automatically replacing or fixing code in a repository after certain operations. This was our primary motivation behind developing SympleGit. Our goal was simple: to provide an uncomplicated Git implementation that allows for the creation and pushing of new branches after modifying source code. A typical use case, for example, would be replacing all `Statement` instances with `PreparedStatement` for enhanced SQL Injection protection. You can learn more about this aspect of security at [Sqlephant](https://www.sqlephant.com/product/#sqli).

### Handling Short and Large Outputs with GitCommander

The `GitCommander` API is versatile, allowing the execution of any Git command regardless of the command's complexity or the size of its output.

#### Short Output

For standard operations where the output size is manageable, `GitCommander` efficiently handles the command execution and retrieves the results directly.

```java
// List all branches of a repo and print them on console
String repoDirectoryPath = "/path/to/my/git/repository";
final SympleGit sympleGit = SympleGit.custom()
    .setDirectory(repoDirectoryPath)
    .build();

GitCommander gitCommander = sympleGit.gitCommander();
gitCommander.executeGitCommand("git", "branch", "-a");

if (! gitCommander.isResponseOk()) {
    System.out.println("An Error Occured: " + gitCommander.getProcessError());
    return;
}

// OK, display branches on console
String[] branches = gitCommander.getProcessOutput().split("\n");
for (String branch : branches) {
	System.out.println(branch);
}
```

#### Large Output 

In cases involving more extensive data, such as full commit messages and metadata for each commit in large repositories, `GitCommander` employs `InputStream` to manage and retrieve the output. For outputs exceeding 4MB, `InputStream` is particularly useful to process and handle the data effectively. This approach ensures that even with substantial amounts of data, `GitCommander` can efficiently process and provide the required information.

```java
// List full commit messages and metadata for each commit, which can be quite substantial 
// for large repositories.
String repoDirectoryPath = "/path/to/my/git/repository";

final SympleGit sympleGit = SympleGit.custom()
	.setDirectory(repoDirectoryPath)
	.build();

GitCommander gitCommander = sympleGit.gitCommander();
gitCommander.executeGitCommand("git", "--no-pager", "log");

if (!gitCommander.isResponseOk()) {
    System.out.println("An Error occured: " + gitCommander.getProcessError());
    return;
}

// It's always cautious to test the output
if (gitCommander.getSize() <= 1 * 1024 * 1024) {
    // Small output size: use String
    String[] lines = gitCommander.getProcessOutput().split("\n");
    for (String line : lines) {
		System.out.println(line);
    }
} else {
    // Large output size: use an InputStream
    try (BufferedReader reader = new BufferedReader(
	    new InputStreamReader(gitCommander.getProcessOutputAsInputStream()));) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
		}
    }
}
```

### Setting a Timeout

To manage execution time, SympleGit allows you to set a timeout for the underlying Git process using the `Builder.setTimeout` method. This feature is especially useful for ensuring that operations do not run indefinitely:

```java
final SympleGit sympleGit = SympleGit.custom()
	.setDirectory(repoDirectoryPath)
	.setTimeout(300, TimeUnit.SECONDS) // The process will be terminated after 300 seconds
	.build();
```

Internally, the Git process is executed within a thread using `java.util.concurrent.Future`, enabling controlled termination of operations. However, it's important to note that while this stops the process, the thread itself may continue running until it reaches a natural stopping point.

### Releasing Resources by Closing the SympleGit Instance

It's a recommended practice to call the `close` method on the SympleGit instance to ensure the cleanup of temporary files.

SympleGit is designed to be `AutoCloseable`, allowing for easy resource management.

Additionally, there's a static method available, `SympleGit.deleteTempFiles()`, which can be used to delete all temporary files created. However, exercise caution when using this method, especially in multi-user environments.

## The Facilitator API

The Facilitator API consists of a suite of classes designed to encapsulate the `GitCommander`, each tailored to specific types of Git operations. This approach streamlines the process of executing various Git commands by providing specialized, easy-to-use wrappers.

### API List

| Class Name      | Purpose                                                |
| --------------- | ------------------------------------------------------ |
| GitAdd          | Manages the staging area for changes.                  |
| GitBranchModify | Handles branch-related operations in write mode.       |
| GitBranchRead   | Manages branch-related operations in read mode.        |
| GitCommit       | Facilitates the process of making commits.             |
| GitDiff         | Compares and tracks changes in the repository.         |
| GitMerge        | Handles merging of branches.                           |
| GitRemote       | Manages remote repositories and tracks changes.        |
| GitRepo         | Provides functionality for repository-wide operations. |
| GitTag          | Manages tagging operations in the repository.          |
| GitVersion      | Retrieves information about the installed Git version. |

### API Usage

Utilizing the Facilitator API is straightforward. Refer to the Javadoc for detailed documentation.

```java
// Staging Files & Committing with SympleGit using GitAdd & GitCommit
String repoDirectoryPath = "/path/to/my/git/repository";

// Create a SympleGit instance
final SympleGit sympleGit = SympleGit.custom()
            .setDirectory(repoDirectoryPath)
            .setTimeout(5, TimeUnit.MINUTES)
            .build();

// Create a GitAdd instance to manage staging
GitAdd gitAdd = new GitAdd(sympleGit);
gitAdd.add("testFile1", "testFile2");

// Create a GitCommit instance for making commits
GitCommit gitCommit = new GitCommit(sympleGit);
gitCommit.commitChanges("Modified test files");

// It's advisable to check the result of the commit operation:
if (!gitCommit.isResponseOk()) {
    System.out.println("An Error occurred: " + gitCommit.getError());
    if (gitCommit.getException() != null) {
		System.out.println("An Exception has been raised: " + gitCommit.getError());
    }
    return;
}

System.out.println("Added test files to the Git repository");

```



## Extending SympleGit Facilitator API using AI (AI-XOSS)

### List Files: create create in a flash the FilesLister Class

