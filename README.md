# 

# SympleGit v1.0 - January 12, 2024

<img src="https://www.symplegit.com/img/arrow_fork2.png" />

A simple Git wrapper in Java, easily extendable with Artificial Intelligence.

## What is SympleGit?

SympleGit is a minimalist but robust and extendable Java Git implementation that has 3 main characteristics:

1. It supports one to one calls corresponding to Git command line calls.
2. It has wrappers classes for main Git actions, with easy names to use.
3. It is extendable & customizable using Artificial Intelligence following the AI-XOSS (AI-Extendable Open Source Software) pattern, this will be detailed.

## Why choose SympleGit when there is already JGit?

[JGit](https://www.eclipse.org/jgit/) is a great Java implementation of Git, very well and rich featured, with a clean and fluent API. 

JGit was designed to manage Git full support in Eclipse, implementing all sophisticated actions for the end user developing  a Java project. 

If you want to develop a Java editor and add to Git management: [install JGit](https://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit).

But, on the other side, the API has a learning curve and there is no direct and one to one support for CLI actions. SympleGit  should thus be easier than JGit for simple Git integration in many Java projects that use basic Git actions. Let's jump into details!

## Choosing SympleGit Over JGit: Understanding the Advantages

### Support for CLI Git calls

For example, using JGit for staging files, you must always use the API:

```java
// Staging files with JGIT

final File localPath;

// Prepare a new test-repository. 
// This uses https://github.com/centic9/jgit-cookbook tutorial

try (Repository repository = CookbookHelper.createNewRepository()) {
    localPath = repository.getWorkTree();

    try (Git git = new Git(repository)) {
        // create the file
        File myFile = new File(repository.getDirectory().getParent(), "testfile");
        if(!myFile.createNewFile()) {
            throw new IOException("Could not create file " + myFile);
        }

        // run the add-call
        git.add()
            .addFilepattern("testfile")
			.addFilepattern("testFile2")
            .call();

        System.out.println("Added file " + myFile + " to repository at " + repository.getDirectory());
    }
}

```

With SympleGit, you have two options:

- A direct invocation of a the `GitCommander` class using the command-line interface (CLI) syntax, for example: `git add testfile`.
- A call using the Facilitator API with the class `GitAdder`.

The code for direct invocation is:

```java
// Staging files with SympleGit using GitCommander
String repoDirectoryPath = "/path/to/my/git/repository";

final SympleGit sympleGit = SympleGit.custom()
    .setDirectory(repoDirectoryPath)
    .build();

GitCommander gitCommander = sympleGit.gitCommander();
// Well, git add testfile testFile2 ;-)
gitCommander.executeGitCommand("git", "add", "testFile", "testFile2"); 
```

The code with the `GitAdder` class is:

```java
// Staging files with SympleGit using GitAdder
final SympleGit sympleGit = SympleGit.custom()
            .setDirectory(repoDirectoryPath)
            .build();

GitAdder gitAdder = new GitAdder(sympleGit);
gitAdder.add("testFile", "testFile2");
```

Supporting a one to one correspondence with `GitCommander` has advantages:

- It allows a non Git expert to pass the required Git command without failure, or passing time findings the right parameters in the API. If you are not a Git expert, it will be easy for the Git expert in the company to help you implement the right Git call with the right parameters. On the other side, it will be easy for a Git expert who is not a Java expert to code Gits call in a Java workflow.
- Git is a complex software, having in Java the equivalent of CLI calls allows to pass complex or rare commands without requiring to program all the option in the API. Example: `git log --graph --abbrev-commit --decorate --date=relative --all --pretty=format:'%h - %ar | %s (%an)%d' --max-count=10`.

### Straightforward Git implementation

if you are not developing a Java Editor, a straightforward Git implementation with all the basics Git commands should be sufficient and easier.

As a java developer, the typical case you would meet in Java project accessing is having to replace/fix code automatically in a repository after a treatment. This is why we developed SympleGit: we just wanted a simple Git implementation just to create and push a new branch after modifying source code by replacing all `Statement` with `PreparedStatement` ([for SQL Injection protection](https://www.sqlephant.com/product/#sqli)).

## The Commander API

The `GitCommander` API allows to call any Git command, whatever the command and whatever the ouptut return size.

### Short Outpout 

```java
// List all branches of a repo and print them on console
String repoDirectoryPath = "/path/to/repo";
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

### Large Output 

Behind the scenes, `GitCommander` uses `InputStream` to retrieved the errors and output.

This example of the Git command provides full commit messages and metadata for each commit, which can be quite substantial for large repositories.  So we will retrieve the result  using an `InputStream` if size > 4Mb.

```java
// List full commit messages and metadata for each commit, which can be quite substantial 
// for large repositories.
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

Use `Builder.setTimeout`in order to define a timeout for the underlying process:

```java
final SympleGit sympleGit = SympleGit.custom()
	.setDirectory(repoDirectoryPath)
	.setTimeout(300, TimeUnit.SECONDS) // Process will be killed after 300 seconds
	.build();
```

The git process is internally launched in a Thread using a `java.util.concurrent.Future`. This allows to stop activity, but note that the Thread itself will eventually not be stopped.

## The Facilitator API

The Facilitator API is a set of classes that wrap the `GitCommander` for each type of Git operation:

### API List

| Class Name      | Usage                                         |
| --------------- | --------------------------------------------- |
| GitAdd          | To handle staging area.                       |
| GitBranchModify | For branch-related operations, in write mode. |
| GitBranchRead   | For branch-related operations, in read mode.  |
| GitCommit       | To handle commits.                            |
| GitDiff         | To compare changes.                           |
| GitMerge        | For merging branches.                         |
| GitRemote       | To compare changes.                           |
| GitRepo         | For repository-wide operations.               |
| GitTag          | For tagging operations.                       |
| GitVersion      | To get the installed Git version.             |

### API Usage

The Facilitator API usage is straightforward. See the Javadoc.

```java
// Staging files & commit  with SympleGit using GitAdd & GitCommit
	
final SympleGit sympleGit = SympleGit.custom()
            .setDirectory(repoDirectoryPath)
            .setTimeout(5, TimeUnit.MINUTES)
            .build();

GitAdd gitAdd = new GitAdd(sympleGit);
gitAdd.add("testFile1", "testFile2");

GitCommit gitCommit = new GitCommit(sympleGit);
gitCommit.commitChanges("Modified test files");

// It's recommended to test the result of the commit call:
if (!gitCommit.isResponseOk()) {
    System.out.println("An Error occured: " + gitCommit.getError());
    if (gitCommit.getException() != null) {
		System.out.println("An Exception has been raised: " + gitCommit.getError());
    }
    return;
}

System.out.println("Added test files to git repository");
```



## Extending SympleGit Facilitator API using AI (AI-XOSS)

### List Files: create create in a flash the FilesLister Class

