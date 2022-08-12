# Gitlet Design Document

**Name**: strbytes

## Classes and Data Structures

### Main

Validates arguments and calls commands from CapersRepository.


### Repository

Stores static variables and methods for interacting with the repository.
Main command logic.

#### Fields

1. GITLET_DIR - hook for the .gitlet directory
2. Will need hooks for the commit tree, HEAD, staging area


### Index

Stores the information about the current repo status. Serializable.

#### Fields

1. repo - Map<String, String> from a file name to its hash in the repo.
2. staged - Map<String, String>  from a file name to its hash in the staging area.
3. commit - Map<String, String>  from a file name to its hash in the working commit.


### Tree

Stores information about the files and their versions in a commit.


### Commit

Stores the serializable information about a commit in String format.
Returns deserialized objects via getters.

#### Fields

1. parent - The hash of the parent commit.
2. tree - The hash of the tree for this commit.
3. timestamp - The Date the commit was made.
4. message - The commit message.
5. hash - The hash of this commit.

## Algorithms

## Persistence

Repository class sets up and maintains persistence.

1. Repository.init sets up persistence
2. Repository add, rm, (?) modify the staging area
3. Repository commit and merge write commits to the tree