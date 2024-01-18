![GitHub top language](https://img.shields.io/github/languages/top/kawansoft/SympleGit-Java)![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/kawansoft/SympleGit-Java) ![GitHub issues](https://img.shields.io/github/issues/kawansoft/SympleGit-Java)
![Maven Central](https://img.shields.io/maven-central/v/com.aceql/SympleGit-Java) 
![GitHub commit activity](https://img.shields.io/github/commit-activity/y/kawansoft/SympleGit-Java) ![GitHub last commit (branch)](https://img.shields.io/github/last-commit/kawansoft/SympleGit-Java/master)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/d14142d5d6f04ba891d505e2e47b417d)](https://www.codacy.com/gh/kawansoft/SympleGit-Java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kawansoft/SympleGit-Java&amp;utm_campaign=Badge_Grade)
![GitHub contributors](https://img.shields.io/github/contributors/kawansoft/SympleGit-Java)

# SympleGit v1.0 - January 17, 2024

<img src="https://www.symplegit.com/img/arrow_fork2.png" />

SympleGit is a Java-based Git wrapper, co-developed with AI assistance, offering simplicity and ease of extension through AI integration.

## What is SympleGit?

SympleGit is a minimalist yet robust and expandable Java implementation of Git, characterized by three main features:

1. It supports direct calls corresponding to Git command line operations.
2. It includes wrapper classes for primary Git actions, with straightforward and easy-to-use names.
3. It is extendable and customizable using Artificial Intelligence, adhering to the AI-XOSS (AI-Extendable Open Source Software) pattern. More details on this will follow.

## Installation

### Maven

```xml
<groupId>com.symplegit</groupId>
<artifactId>symplegit</artifactId>
<version>1.0</version>
```

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

In contrast, [SympleGit](https://www.symplegit.com/rest/soft/javadoc) offers two straightforward options:

- Direct invocation using the [GitCommander](https://www.symplegit.com/rest/soft/v1.0/javadoc/com/symplegit/api/GitCommander.html) class with command-line interface (CLI) syntax, such as: `git add testfile`.
- Utilizing the Facilitator API with the [GitAdder](https://www.symplegit.com/rest/soft/v1.0/javadoc/com/symplegit/api/facilitator/GitAdd.html) class.

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

The [GitCommander](https://www.symplegit.com/rest/soft/v1.0/javadoc/com/symplegit/api/GitCommander.html) API is versatile, allowing the execution of any Git command regardless of the command's complexity or the size of its output.

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

When the specified timeout is reached, GitCommander (or the Facilitator API) throws an unchecked exception, [UncheckedTimeoutException](https://www.symplegit.com/rest/soft/v1.0/javadoc/com/symplegit/api/exception/UncheckedTimeoutException.html). This mechanism ensures that operations do not exceed the predefined time limit.

### Releasing Resources by Closing the SympleGit Instance

It's a recommended practice to call the `close` method on the SympleGit instance to ensure the cleanup of temporary files. SympleGit is designed to be `AutoCloseable`, allowing for easy resource management.

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

Utilizing the Facilitator API is straightforward. Refer to the [Javadoc](https://www.symplegit.com/rest/soft/v1.0/javadoc/com/symplegit/api/facilitator/package-summary.html) for detailed documentation.

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

## SympleGit and Artificial Intelligence

### Using AI (GPT-4) to Generate the Facilitator API

All classes in the Facilitator API were generated using GPT-4 and have not been manually updated since, with the following exceptions:

1. Enhancing Javadoc Documentation and Improving code formatting.
2. The `getStagedDiffAsStream()` method in `GitDiff` was added manually due to the possibility of the command returning a large volume of data.

The generation process for these classes utilized a single, parameterized prompt. This prompt included three specific parameters:

- `${0}`: Represents the [class name].
- `${1}`: A list of method names, each potentially with self-descriptive parameters, separated by commas.
- `${2}`: The intended purpose of the class.

Moreover, the prompt included source code from SympleGit, which enabled GPT-4 to efficiently produce contextually relevant new code. (Note: Only GPT-4 is supported; the prompt will not work correctly with GPT-3.5 and has not been test with other AI providers.)

For illustration purposes, the template below was employed to generate the `GitRepo` class. To keep it simple, the actual source code of the referenced classes has been omitted from this prompt (full prompt [URL](https://www.symplegit.com/rest/soft/v1.0/templates/facilitator_prompt_template.txt)):

```bash
You are a Java expert and a Git expert, world class.

I will pass you 4 Java classes:

- SympleGit: a class that is the main point of entry, and allows to get GitCommander with SympleGit.getCommander()
- GitCommander: a class that allows to pass Git commands and get output and errors.
- GitWrapper: an interface for Git Wrapper classes 
- GitBranchExample: a simplified example of a wrapper class that does an only update and only a read Git operation.

These classes will be used as a guideline for building a new Wrapper class.
I want you to write following these guidelines a ${0} wrapped class that will have these methods:

${1} 

in order to wrap these Git operations: ${2}

The values of ${0}, ${1} ,and ${2} are at the end of this prompt.

Add a "@author GPT-4" at first Javadoc.
Please include clean & professional Javadoc in the generated class.

Please make sure to use Git commands with the options that do not use a pager or an editor. 
(Remember that, if required, "--no-pager" option must follow immediately "git" command.)

Here are the 4 classes:

[content of SympleGit.java]
[content of GitCommander.java]
[content of GitWrapper.java]
[content of GitBranchExample.java]

${0}=GitRepo
${1}=Methods: cloneRepository(repoUrl), initializeRepository(), getRepositoryStatus(), addRemote(name, url), removeRemote(name)
${2}=For repository-wide operations.
```

The `GitBranchExample` serves as a 'generic' example within the Facilitator classes and is the sole class manually written to guide the volatile and session "training" of GPT-4 for our needs. 

This template was then applied to all other classes, with modifications limited to the values of the three parameters:

```bash
${0}=GitAdd
${1}=addAll(), add(List<String> files), add(List<File> files)
${2}=Git add operations.
 
${0}=GitCommit
${1}=Methods: commitChanges(message), amendCommit(), getCommitHistory(), getCommitDetails(commitHash)
${2}=To handle commits.

${0}=GitDiff
${1}=Methods: getDiff(commitHash1, commitHash2), getStagedDiff(), getFileDiff(filePath)
${2}=To compare changes.

${0}=GitMerge
${1}=Methods: mergeBranches(targetBranch, sourceBranch), abortMerge(), getMergeStatus()
${2}=For merging branches.

${0}=GitRemote
${1}=Methods: fetchRemote(remoteName), pushChanges(remoteName, branchName), pullChanges(remoteName, branchName), listRemotes()
${2}=For operations on remote repositories.

${0}=GitRepo
${1}=Methods: cloneRepository(repoUrl), initializeRepository(), getRepositoryStatus(), addRemote(name, url), removeRemote(name)
${2}=For repository-wide operations.

${0}=GitTag
${1}=Methods: createTag(tagName, commitHash), deleteTag(tagName), listTags()
${2}=For tagging operations.

${0}=GitVersion
${1}=getVersion()
${2}=get Git version.
```

### Using GPT-4 to Generate the Facilitator API Test Classes

This approach also extends to all unit tests, which were similarly produced by GPT-4:

```Bash
You are a Java expert and a Git expert, world class.

Please write a complete unit test class for the following ${0} Java class.
The value ${0} is at the end of this prompt.

Use this Java line to get the repository File:
File repoDir = GitTestUtils.createIfNotTexistsTemporaryGitRepo();
The class GitTestUtils and the createIfNotTexistsTemporaryGitRepo method already exist, do not create them.
        
As the repoDirt is real and exists as Git repo, do not use mock in the code, use only real calls.      

${0}=GitRepo
```

See the [source code tests](https://github.com/kawansoft/SympleGit-Java/tree/master/src/test/java/com/symplegit/test/util) for more info.

### The SympleGit AI Code Generation Prompt

The complete prompt used for generating code with SympleGit is accessible at the following link: 
[GitHub SympleGit Prompt Template](https://www.symplegit.com/rest/soft/v1.0/templates/facilitator_prompt_template.txt).

### Extending SympleGit Facilitator API using a Development Pattern (AI-XOSS)

#### How to Extend the SympleGit Facilitator API

Curious about what's next? The SympleGit open source software can now be effortlessly extended by simply submitting a prompt to GPT-4, accompanied by the parameters for any new class you aim to create.

For instance, if you want to manage Git configurations, you can resubmit the prompt template with the following three parameter values:

```bash
${0}=GitConfig
${1}=Methods: getUserConfig(), setUserConfig(userName, userEmail), getGlobalConfig(), setGlobalConfig(configKey, configValue)
${2}=For managing Git configurations.
```

This approach of developing open source code with AI, utilizing parametrized templates, is termed "AI-Extendable Open Source Software" or AI-XOSS.

### The AI-XOSS Pattern

AI-XOSS (AI-Extendable Open Source Software) is a software development pattern that integrates artificial intelligence (AI) capabilities into open-source software projects. This innovative approach allows users, even those with limited AI knowledge, to enhance software functionalities using AI-assisted tools.

The AI-XOSS pattern combines AI technologies with open-source development principles, resulting in software that is both adaptive and user-friendly. A key aspect of AI-XOSS is its user-centric design, enabling users to expand software capabilities through AI with minimal technical barriers.

The primary objective of the AI-XOSS pattern is to encourage open-source developers to publish software that is extendable using AI, without requiring users to have any AI expertise, except for knowing how to submit a prompt to a language model like GPT-4.

Utilizing the AI-XOSS pattern offers numerous advantages for both open-source developers and their community of users/developers:

1. Open-source software authors can release products that may not include every anticipated feature initially, reducing the time to market.
2. Users don't need to request pull requests or wait for the next version; they can extend the software for their specific needs without traditional programming.
3. It fosters a collaborative ecosystem where users contribute to the software’s evolution, potentially leading to more innovative and diverse functionalities.
4. The pattern can reduce the overall development burden on the core team, as users can independently develop extensions and customizations.
5. It democratizes software development, enabling a wider range of contributors to participate in the software creation process.
6. AI-XOSS can lead to faster iteration and improvement of software, as AI-generated solutions can be quickly tested and refined.
7. This approach can potentially increase the software's adaptability to different use cases and environments, enhancing its versatility and appeal.
8. AI-XOSS may attract a broader user base, as the ease of customization and extension can appeal to both technical and non-technical users.



-----------------















