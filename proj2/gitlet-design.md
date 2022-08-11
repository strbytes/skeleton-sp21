# Gitlet Design Document

**Name**: strbytes

## Classes and Data Structures

### Main

Validates arguments and calls commands from CapersRepository.

### Repository

#### Fields

1. GITLET_DIR - hook for the .gitlet directory
2. Will need hooks for the commit tree, HEAD, staging area


### Staging

Stores the information about a planned commit. Serializable.
Holds much of the same info as Commit but does not serialize whole files.

#### Fields

1. List of files (??) -- same as Commit format but doesn't serialize files, only points to
working directory. 
2. Pointer to previous commit(s)


### Commit

Stores the serializable information about a commit. Does not serialize files
(links them via SHA-1 hash) either but does create serialized files when 
necessary.

## Algorithms

## Persistence

Repository class sets up and maintains persistence.

1. Repository.init sets up persistence
2. Repository add, rm, (?) modify the staging area
3. Repository commit and merge write commits to the tree