package gitlet;
import java.util.*;

/** Represents the index of the repository, which keeps track of the files and
 * their versions in the working directory and staging area.
 *
 * @author strbytes
 * */
public class Index implements Dumpable {
    /** Stores file names as the key and an array of SHA-1 hashes of the working
     * version and the saved versions in the repository.
     *
     * Stored versions: {working directory, staged, previous commit}
     * */
    private final Map<String, String[]> files;
    /** Headers for the array of versions (used in toString). */
    private final String[] versionHeaders = new String[] {"wdir", "stage", "repo"};

    /** Initializes the index. */
    public Index() {
        files = new HashMap<>();
    }

    /** Add an untracked file to the index. Called when staging a new file.
     *
     * @param fileName Name of the file to add.
     * @param hash Hash of the file contents.
     */
    public void addFile(String fileName, String hash) {
        files.put(fileName, new String[] {hash, hash, ""});
    }

    /** Update the version of a tracked file in the working directory. Called
     * with every Gitlet command except init.
     *
     * @param fileName Name of the file to add.
     * @param hash Hash of the file contents.
     */
    public void updateFile(String fileName, String hash) {
        assert contains(fileName);
        files.get(fileName)[0] = hash;
    }

    /** Replace the current index with the index of a checked out branch.
     *
     * @param tree The tree of files saved in the head commit of a branch.
     */
    public void checkout(Tree tree) {
        files.clear();
        for (String fileName : tree) {
            String hash = tree.fileHash(fileName);
            files.put(fileName, new String[] {hash, hash, hash});
        }
    }

    /** Returns a set of all the files currently tracked. */
    public Set<String> getFiles() {
        return files.keySet();
    }

    /** Returns the array of tracked versions of a file. */
    public String[] getVersions(String fileName) {
        // TODO this reveals a mutable private property, should probably protect it somehow.
        return files.get(fileName);
    }

    /** Returns whether the index contains a file with the name fileName. */
    public boolean contains(String fileName) {
        return files.containsKey(fileName);
    }

    /** Returns a map with file names as keys and the staged versions of those files
     * in the repository as values. Used when committing. */
    public Map<String, String> getStaged() {
        Map<String, String> staged = new HashMap<>();
        for (String file: files.keySet()) {
            staged.put(file, files.get(file)[1]);
        }
        return staged;
    }

    /** Updates the repo version of the file to match the staged version.
     * Used when committing. */
    void commitStaged() {
        // TODO figure out how rm is gonna work.
        for (String file: files.keySet()) {
            if (files.get(file)[1] != "") {
                files.get(file)[2] = files.get(file)[1];
            }
        }
    }

    /** Returns whether the working directory version of a file has been modified
     * relative to the staged and repository versions. May be inadequate? */
    public boolean modified(String fileName) {
        assert contains(fileName);
        String[] versions = files.get(fileName);
        // TODO does this work? Do I need to distinguish which is modified here?
        return !versions[0].equals(versions[1]) || !versions[0].equals(versions[2]);
    }

    /** Copy the version of a file in the working directory to be the staged version.
     * Used when staging files with add. */
    public void stageModified(String fileName) {
        assert contains(fileName);
        String[] versions = files.get(fileName);
        versions[1] = versions[0];
    }

    /** Print a string representation of the index. */
    @Override
    public void dump() {
        System.out.println(this);
    }

    /** Returns a string representation of the index. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String f: files.keySet()) {
            s.append(f);
            s.append("\n");
            for (int i = 0; i < 3; i++) {
                s.append("\t");
                s.append(versionHeaders[i]);
                s.append("\t");
                s.append(files.get(f)[i]);
                s.append("\n");
            }
        }
        return s.toString();
    }
}
