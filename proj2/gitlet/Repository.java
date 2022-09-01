package gitlet;

import java.io.File;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
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

    public static void init() {
        if (GITLET_DIR.exists()) {
            Utils.message("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        REFS_DIR.mkdir();
        Commit initialCommit = new Commit();
        String initialCommitHash = initialCommit.getHash();
        writeObject(initialCommitHash, initialCommit);
        String branch = newBranch("master", initialCommitHash);
        Utils.createFile(HEAD);
        // TODO -- update other methods that use HEAD to get the Branch from this
        Utils.writeContents(HEAD, branch);
        Index index = new Index();
        Utils.writeObject(INDEX, index);
    }

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

    public static void lsFiles() {
        Index index = Utils.readObject(INDEX, Index.class);
        System.out.println(index);
    }

    public static String newBranch(String name, String commit) {
        File branch = join(REFS_DIR, name);
        Utils.createFile(branch);
        Utils.writeContents(branch, commit);
        return name;
    }

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

    public static void commit(String message) {
        String branch = Utils.readContentsAsString(HEAD);
        String prevCommit = getBranch(branch);
        Index index = Utils.readObject(INDEX, Index.class);
        Tree tree = new Tree(index.getStaged());
        writeObject(tree.getHash(), tree);
        Commit commit = new Commit(prevCommit, tree.getHash(), message);
        writeObject(commit.getHash(), commit);
        File branchFile = join(REFS_DIR, branch);
        Utils.writeContents(branchFile, commit.getHash());
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

    public static String getBranch(String branchName) {
        File branch = Utils.join(REFS_DIR, branchName);
        return readContentsAsString(branch);
    }

    public static void checkoutCommit(String hash) {
        // TODO this should be checkout branch

//        Commit commit = readObject(hash, Commit.class);
//        String treeHash = commit.getTree();
//        Tree tree = readObject(treeHash, Tree.class);
//        for (String fileName : tree) {
//            String fileHash = tree.fileHash(fileName);
//            String fileContents = readFile(fileHash);
//
//            File file = Utils.join(CWD, fileName);
//            if (file.exists()) {
//                file.delete();
//            }
//            Utils.createFile(file);
//            Utils.writeContents(file, fileContents);
//        }
    }

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
    }

    public static void checkoutFileFromCommit(String hash, String fileName) {
        Commit commit = readObject(hash, Commit.class);
        String treeHash = commit.getTree();
        Tree tree = readObject(treeHash, Tree.class);
        String fileHash = tree.fileHash(fileName);
        String fileContents = readFile(fileHash);
        File file = Utils.join(CWD, fileName);
        if (file.exists()) {
            file.delete();
        }
        Utils.createFile(file);
        Utils.writeContents(file, fileContents);
    }
}
