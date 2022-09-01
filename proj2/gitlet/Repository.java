package gitlet;

import java.io.File;
import java.util.Arrays;

import static gitlet.Utils.*;

/** Represents a gitlet repository. Contains static methods for performing Gitlet
 * commands as well as shortcuts to the files and directories used by Gitlet to
 * maintain the repository.
 *
 *  @author strbytes
 */
public class Repository {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** Objects directory. */
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    /** References directory. */
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    /** The index file to keep track of the working directory. */
    public static final File INDEX = join(GITLET_DIR, "index");
    /** HEAD pointer. Keeps track of current position in the commit tree. */
    public static final File HEAD = join(GITLET_DIR, "head");

    /** Initializes a new .gitlet directory and the files and folders needed to maintain it.
     * Creates an initial commit and master branch.
     */
    public static void init() {
        if (GITLET_DIR.exists()) {
            Utils.message("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        REFS_DIR.mkdir();
        Commit initialCommit = new Commit();
        String initialCommitHash = initialCommit.hash;
        writeObject(initialCommitHash, initialCommit);
        String branch = "master";
        newBranch(branch, initialCommitHash);
        Utils.createFile(HEAD);
        Utils.writeContents(HEAD, branch);
        Index index = new Index();
        Utils.writeObject(INDEX, index);
    }

    /** Utility method to inspect object files created by Gitlet. Accessed by calling
     * Gitlet with the type and hash of the object to be read. */
    public static void catFile(String type, String hash) {
        String dirName = hash.substring(0, 2);
        String fileName = hash.substring(2);
        File file = join(OBJECTS_DIR, dirName);
        file = join(file, fileName);
        if (file.exists()) {
            Object fileContents;
            switch (type) {
                case "commit":
                    fileContents = Utils.readObject(file, Commit.class);
                    break;
                case "tree":
                    fileContents = Utils.readObject(file, Tree.class);
                    break;
                case "file":
                    fileContents = Utils.readContentsAsString(file);
                    break;
                default:
                    fileContents = "Invalid type specified.";
            }
            System.out.println(fileContents);
        } else {
            System.out.println("File not found.");
        }
    }

    /** Utility method that displays the contents of the Index file. */
    public static void lsFiles() {
        Index index = Utils.readObject(INDEX, Index.class);
        System.out.println(index);
    }

    /** Create a new branch.
     *
     * @param name Name of the branch to be created.
     * @param commit Hash of the commit to use as the head commit for this branch.
     */
    public static void newBranch(String name, String commit) {
        // TODO check for duplicate names.
        File branch = join(REFS_DIR, name);
        Utils.createFile(branch);
        Utils.writeContents(branch, commit);
    }

    /** Add a file to the staging area.
     *
     * @param fileName Name of the file to be added.
     */
    public static void add(String fileName) {
        File file = join(CWD, fileName);
        if (!file.exists()) {
            Utils.message("File does not exist.");
            System.exit(0);
        }

        String fileContents = Utils.readContentsAsString(file);
        String hash = Utils.sha1(fileContents);
        Index index = Utils.readObject(INDEX, Index.class);

        if (index.contains(fileName)) {
            if (index.modified(fileName)) {
                index.stageModified(fileName);
                writeFile(hash, fileContents);
                Utils.writeObject(INDEX, index);
            }
        } else {
            index.addFile(fileName, hash);
            writeFile(hash, fileContents);
            Utils.writeObject(INDEX, index);
        }
    }

    /** Create a new commit with all the changed files in the staging area.
     *
     * @param message Message associated with the commit.
     */
    public static void commit(String message) {
        // TODO check that files have changed.
        String branch = Utils.readContentsAsString(HEAD);
        String prevCommit = getBranch(branch);
        Index index = Utils.readObject(INDEX, Index.class);
        Tree tree = new Tree(index.getStaged());
        writeObject(tree.getHash(), tree);
        Commit commit = new Commit(prevCommit, tree.getHash(), message);
        writeObject(commit.hash, commit);
        File branchFile = join(REFS_DIR, branch);
        Utils.writeContents(branchFile, commit.hash);
        index.commitStaged();
        Utils.writeObject(INDEX, index);
    }

    /** Update the working directory versions of all files. */
    public static void updateIndex() {
        Index index = Utils.readObject(INDEX, Index.class);
        for (String fileName : index.getFiles()) {
            File file = join(CWD, fileName);
            if (!file.exists()) {
                index.updateFile(fileName, "");
            } else {
                String fileContents = Utils.readContentsAsString(file);
                String hash = Utils.sha1(fileContents);
                index.updateFile(fileName, hash);
            }
        }
        Utils.writeObject(INDEX, index);
    }

    /** Get the commit hash from the branch associated with branchName. */
    public static String getBranch(String branchName) {
        File branch = Utils.join(REFS_DIR, branchName);
        return readContentsAsString(branch);
    }

    /** Prints out information about the sequence of commits making up a branch. */
    public static void printLog() {
        String branch = Utils.readContentsAsString(HEAD);
        String prevCommit = getBranch(branch);
        while (!prevCommit.equals("")) {
            Commit commit = readObject(prevCommit, Commit.class);
            System.out.println("===");
            System.out.println("commit " + commit.hash);
            System.out.println("Date: " + commit.timestamp);
            System.out.println(commit.message);
            System.out.println();
            prevCommit = commit.parent;
        }
    }

    /** Check out a branch by name.
     *
     * Called with format 'gitlet checkout <branchName>'. */
    public static void checkoutBranch(String branchName) {
        // TODO -- untested, cannot create new branches yet
        if (!Arrays.asList(REFS_DIR.list()).contains(branchName)) {
            System.out.println("Branch " + branchName + " does not exist.");
        }
        writeContents(HEAD, branchName);
        String commitHash = getBranch(branchName);
        Commit commit = readObject(commitHash, Commit.class);
        String treeHash = commit.tree;
        Tree tree = readObject(treeHash, Tree.class);
        for (String fileName : tree) {
            checkoutFile(fileName);
        }
        Index index = readObject(INDEX, Index.class);
        index.checkout(tree);
    }

    /** Check out a file by name.
     *
     * Called with format 'gitlet checkout -- <fileName>'. */
    public static void checkoutFile(String fileName) {
        Index index = Utils.readObject(INDEX, Index.class);
        String fileHash = index.getVersions(fileName)[2];
        String fileContents = readFile(fileHash);
        File file = Utils.join(CWD, fileName);
        if (file.exists()) {
            file.delete();
        }
        Utils.createFile(file);
        Utils.writeContents(file, fileContents);
        index.updateFile(fileName, fileHash);
    }

    /** Check out a file from a previous commit.
     *
     * Called with format 'gitlet checkout <hash> <fileName>'. */
    public static void checkoutFileFromCommit(String hash, String fileName) {
        hash = getFullHash(hash);
        Commit commit = readObject(hash, Commit.class);
        String treeHash = commit.tree;
        Tree tree = readObject(treeHash, Tree.class);
        String fileHash = tree.fileHash(fileName);
        String fileContents = readFile(fileHash);
        File file = Utils.join(CWD, fileName);
        if (file.exists()) {
            file.delete();
        }
        Utils.createFile(file);
        Utils.writeContents(file, fileContents);
        Index index = readObject(INDEX, Index.class);
        index.updateFile(fileName, fileHash);
    }
}
