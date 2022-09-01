package gitlet;

import java.util.Iterator;
import java.util.Map;

/** Stores the file tree of a Commit.
 *
 * @author strbytes
 * */
public class Tree implements Dumpable, Iterable<String> {

    /** Maps file names to the hash codes of their version in the commit. */
    private final Map<String, String> files;
    /** The hash code of this Tree. */
    final String hash;

    /** Creates a tree out of a map of file names and their staged versions. */
    public Tree(Map<String, String> staged) {
        files = staged;
        hash = Utils.sha1(files.toString());
    }

    /** Returns the hash associated with fileName. */
    String fileHash(String fileName) {
        return files.get(fileName);
    }

    /** Print a string representation of the information stored in the Tree. */
    @Override
    public void dump() {
        System.out.println(this);
    }

    /** Return a string representation of the information stored in the Tree. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String file: files.keySet()) {
            s.append(file);
            s.append(":\t");
            s.append(files.get(file));
        }
        return s.toString();
    }

    /** Returns an iterator over the file names contained in the tree. Can be
     * combined with fileHash to iterate over all the file versions in a Tree. */
    @Override
    public Iterator<String> iterator() {
        return files.keySet().iterator();
    }
}
