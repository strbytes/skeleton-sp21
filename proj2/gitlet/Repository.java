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
    /** The index file to keep track of the working directory. */
    public static final File INDEX = join(GITLET_DIR, "index");
    /** HEAD pointer. Keeps track of current position in the commit tree. */
    public static final File HEAD = join(CWD, "HEAD");

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
        Index index = new Index();
        Utils.writeObject(INDEX, index);
        // TODO set up staging area
            // TODO method for reading/writing Index?
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
                default:
                    fileContents = "Invalid type specified.";
            }
            System.out.println(fileContents);
        } else {
            System.out.println("File not found.");
        }

    }

    public static void writeObject(String hash, Serializable object) {
        String dirName = hash.substring(0, 2);
        String fileName = hash.substring(2);
        File dir = join(OBJECTS_DIR, dirName);
        dir.mkdir();
        File file = join(dir, fileName);
        try {
            file.createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }
        Utils.writeObject(file, object);
    }

    /* TODO: fill in the rest of this class. */
    /* TODO: CHECKPOINT: add, commit, checkout -- [file name], checkout [commit id] -- [file name], log */

    public static void add(String fileName) {
        File file = join(CWD, fileName);
        if (!file.exists()) {
            Utils.message("File does not exist.");
            System.exit(0);
        }
        Index index = Utils.readObject(INDEX, Index.class);
        if (!index.contains(file)) {
            index.addFile(file);
            Utils.writeObject(INDEX, index);
        } else {
            // TODO check if new version is identical to previous commit or to staged commit
        }
    }
}
