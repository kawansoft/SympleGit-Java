# SympleGit-Java
<img src="https://www.symplegit.com/img/arrow_fork2.png" style="zoom:50%;" />

A simple Git wrapper in Java for the rest of us, extendable with Artificial Intelligence.

## What is SympleGit?

SympleGit is a minimalist Java Git implementation that has 3 main characteristics:

1. It supports one to one calls corresponding to Git command line calls.
2. It has wrappers classes for main Git actions, with easy names to use.
3. It is extendable using Artificial Intelligence (GPT-4). 

## Why SympleGit? There is JGit already?

[JGit](https://www.eclipse.org/jgit/) is a great Java implementation of Git, very well and rich featured, with a clean and fluent API. We don't "compete" at all with JGit. Download JGit if you need a full featured implementation of Git. 

But, JGit was designed to manage Git full support in Eclipse, implementing all sophisticated actions when developing  a Java project. The API has a learning curve and there is no direct and one to one support for CLI actions.

For example, for staging files, you must use the API:

```java
// Staging files in JGIT

 final File localPath;
// prepare a new test-repository. 
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
String repoDirectoryPath = "/path/to/my/git/repository";

SympleGit sympleGit = new SympleGit(new File(repoDirectoryPath));

GitCommander gitCommander = new GitCommander(sympleGit);
gitCommander.executeGitCommand("git", "add", "testFile");
System.out.println("Added file " + myFile + " to repository at " + repoDirectoryPath);
```



Supporting a one to one correspondence has advantages:

- It allows a non Git expert to pass the required complex Git command without failure, for example with the help of the Git expert in the company.















