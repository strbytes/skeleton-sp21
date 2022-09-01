package gitlet;

import java.util.Date;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Dumpable {

    /** The parent Commit of this Commit. */
    private String parent;
    /** The file tree of this Commit. */
    private String tree;
    /** The timestamp of this Commit. */
    private String timestamp;
    /** The message of this Commit. */
    private String message;
    /** The SHA-1 hash of this commit. */
    private String hash;

    /** Create a new Commit out of a parent Commit, a commit Tree, and a commit message. */
    public Commit(String parent, String tree, String message) {
        this.parent = parent;
        this.tree = tree;
        this.message = message;
        timestamp = String.format("%1$ta %1$tb %1$td %1$tH:%1$tM:%1$tS %1$tY %1$tz", new Date());
        hash = Utils.sha1(parent, tree, message, timestamp);
    }

    /** Create the initial commit for a repository. */
    public Commit() {
        parent = "";
        tree = "";
        message = "initial commit";
        timestamp = new Date(0).toString();
        hash = Utils.sha1(parent, tree, message, timestamp);
    }

    public String getParent() {
        return parent;
    }

    public String getTree() {
        return tree;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return new Date(timestamp);
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Parent: " + parent + "\n" +
        "Tree: " + tree + "\n" +
        "Timestamp: " + timestamp + "\n" +
        "Message: " + message + "\n";
    }

    @Override
    public void dump() {
        System.out.println(this);
    }
}
