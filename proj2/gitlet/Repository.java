package gitlet;

import java.io.File;
import java.io.Serializable;

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
    /** Branches directory. */
    public static final File BRANCHES = join(REFS_DIR, "branches");

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
        BRANCHES.mkdirs();
        Commit initialCommit = new Commit();
        String initialCommitHash = initialCommit.getHash();
        writeObject(initialCommitHash, initialCommit);
        Utils.createFile(HEAD);
        Utils.writeContents(HEAD, initialCommitHash);
        newBranch("master", initialCommitHash);
        Index index = new Index();
        Utils.writeObject(INDEX, index);
    }

    public static void catFile(String type, String hash) {
        // TODO allow using a partial hash instead of full string
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

    static String getFullHash(String partial) {
        String hashDirName = null;
        String hashFile = null;
        for (String dir: OBJECTS_DIR.list()) {
            if (dir.equals(partial.substring(0, 2))) {
                hashDirName = dir;
                break;
            }
        }

        if (hashDirName != null) {
            File hashDir = join(OBJECTS_DIR, hashDirName);
            String[] objects = hashDir.list((dir, name) ->
                    name.substring(0, partial.length() - 2).equals(partial.substring(2)));

            switch (objects.length) {
                case 0:
                    Utils.message("Object not found.");
                    System.exit(0);
                    break;
                case 1:
                    hashFile = objects[0];
                    break;
                default:
                    Utils.message("Ambiguous argument.");
            }
        } else {
            Utils.message("Object not found.");
            System.exit(0);
        }
        return hashDirName + hashFile;
    }

    public static void lsFiles() {
        // TODO this isn't very good
        Index index = Utils.readObject(INDEX, Index.class);
        index.dump();
    }

    public static void writeObject(String hash, Serializable object) {
        String dirName = hash.substring(0, 2);
        String fileName = hash.substring(2);
        File dir = join(OBJECTS_DIR, dirName);
        dir.mkdir();
        File file = join(dir, fileName);
        Utils.createFile(file);
        Utils.writeObject(file, object);
    }

    public static void newBranch(String name, String commit) {
        File branch = join(BRANCHES, name);
        Utils.createFile(branch);
        Utils.writeContents(branch, commit);
    }

    /* TODO: fill in the rest of this class. */
    /* TODO: CHECKPOINT: add, commit, checkout -- [file name], checkout [commit id] -- [file name], log */

    public static void add(String fileName) {
        File file = join(CWD, fileName);
        if (!file.exists()) {
            Utils.message(String.format("File %s does not exist.", fileName));
            System.exit(0);
        }

        String fileContents = Utils.readContentsAsString(file);
        String hash = Utils.sha1(fileContents);
        Index index = Utils.readObject(INDEX, Index.class);

        if (index.contains(fileName)) {
            index.updateFile(fileName, hash);
            if (index.modified(fileName)) {
                index.stageModified(fileName);
            }
        } else {
            index.addFile(fileName, hash);
            Utils.writeObject(INDEX, index);
        }
    }

    public static void commit(String message) {
        String head = Utils.readContentsAsString(HEAD);
        Index index = Utils.readObject(INDEX, Index.class);
        Tree tree = new Tree(index.getStaged());
        writeObject(tree.getHash(), tree);
        Commit commit = new Commit(head, tree.getHash(), message);
        writeObject(commit.getHash(), commit);
        Utils.writeContents(HEAD, commit.getHash());
        index.commitStaged();
        Utils.writeObject(INDEX, index);
    }
}
