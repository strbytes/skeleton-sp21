package gitlet;

import java.util.Map;

/** Stores the file tree of a Commit. */
public class Tree implements Dumpable {

    private Map<String, String> files;
    private String hash;

    public Tree(Map<String, String> staged) {
        files = staged;
        hash = Utils.sha1(files.toString());
    }

    String getHash() {
        return hash;
    }

    @Override
    public void dump() {
        for (String file: files.keySet()) {
            System.out.println(file + ":\t" + files.get(file));
        }
    }
}
