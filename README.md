# SympleGit
<img src="https://www.symplegit.com/img/arrow_fork2.png" />

A simple Git wrapper in Java for the rest of us, extendable with Artificial Intelligence.

## What is SympleGit?

SympleGit is a minimalist Java Git implementation that has 3 main characteristics:

1. It supports one to one calls corresponding to Git command line calls.
2. It has wrappers classes for main Git actions, with easy names to use.
3. It is extendable using Artificial Intelligence (GPT-4). 

## Choosing SympleGit Over JGit: Understanding the Advantages

[JGit](https://www.eclipse.org/jgit/) is a great Java implementation of Git, very well and rich featured, with a clean and fluent API. We don't "compete" at all with JGit. 

But, JGit was designed to manage Git full support in Eclipse, implementing all sophisticated actions for the end user developing  a Java project. The API has a learning curve and there is no direct and one to one support for CLI actions.

### Support for CLI calls

For example, using JGit for staging files, you must use the API:

```java
// Staging files with JGIT
//

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
            .call();

        System.out.println("Added file " + myFile + " to repository at " + repository.getDirectory());
    }
}

```

With SympleGit, you have two options:

- A direct invocation of a wrapper using the command-line interface (CLI) syntax, for example: `git add testfile`.
- A call using an API with the class `GitAdder`.

The code for direct invocation is:

```java
// Staging files with SympleGit
String repoDirectoryPath = "/path/to/my/git/repository";

SympleGit sympleGit = new SympleGit(repoDirectoryPath);

GitCommander gitCommander = new GitCommander(sympleGit);
gitCommander.executeGitCommand("git", "add", "testFile"); // "git add testfile"
```

Supporting a one to one correspondence has advantages:

- It allows a non Git expert to pass the required Git command without failure, or passing time findings the right parameters in the API. If you are not a Git expert, it will be easy for the Git expert in the company to help you implement the right Git call with the right parameters. On the other side, it will be easy for a Git expert who is not a Java expert to code Gits call in aa Java workflow.
- Git is a complex software, having in Java the equivalent of CLI calls allows to pass complex or rare commands without requiring to program all the option in the API. Example: `git log --graph --abbrev-commit --decorate --date=relative --all --pretty=format:'%h - %ar | %s (%an)%d' --max-count=10`

### Straightforward Git implementation

if you are not developing a Java Editor, a straightforward Git implementation with all the basics Git commands should be sufficient.

As a java developer, the typical case you would meet in Java project accessing is having to replace/fix code automatically in a repository after a treatment. This is why we developed SympleGit: we just a simple Git implementation just to create and push a new branch after modifying source code by replacing all `Statement` with `PreparedStatement`([for SQL Injection protection](https://www.sqlephant.com/product/#sqli)).

## The GitCommander API

The `GitCommander` API allows to call any Git command, whatever the command and whatever the ouptut return size.

Example 1: listing all branches of a project

Example 2: retrieving commit messages and metadata

This version of the command provides full commit messages and metadata for each commit, which can be quite substantial for large repositories. 















