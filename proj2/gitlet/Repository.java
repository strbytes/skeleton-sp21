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
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    public static void init() {
        if (GITLET_DIR.exists()) {
            Utils.message("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        } else {
            GITLET_DIR.mkdir();
        }
        // TODO setup initial commit (0 unix time)
        // TODO set up staging area
    }

    /* TODO: fill in the rest of this class. */
    /* TODO: CHECKPOINT: add, commit, checkout -- [file name], checkout [commit id] -- [file name], log */

    public static void add(File addFile) {
        // TODO check if file in previous commit
        // if new file, add new file box to staging area, else update the hash in the existing file box
        // TODO hash file and add to staging area
    }
}
