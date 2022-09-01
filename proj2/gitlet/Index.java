package gitlet;
import java.util.*;

public class Index implements Dumpable {
    private final Map<String, String[]> files;
    private final String[] versionHeaders = new String[] {"wdir", "stage", "repo"};

    public Index() {
        files = new HashMap<>();
    }

    public void addFile(String fileName, String hash) {
        files.put(fileName, new String[] {hash, hash, ""});
    }

    public void updateFile(String fileName, String hash) {
        assert contains(fileName);
        files.get(fileName)[0] = hash;
    }

    public void checkout(Tree tree) {
        files.clear();
        for (String fileName : tree) {
            String hash = tree.fileHash(fileName);
            files.put(fileName, new String[] {hash, hash, hash});
        }
    }

    public Set<String> getFiles() {
        return files.keySet();
    }

    public String[] getVersions(String fileName) {
        return files.get(fileName);
    }

    public boolean contains(String fileName) {
        return files.containsKey(fileName);
    }

    public Map<String, String> getStaged() {
        Map<String, String> staged = new HashMap<>();
        for (String file: files.keySet()) {
            staged.put(file, files.get(file)[1]);
        }
        return staged;
    }

    void commitStaged() {
        for (String file: files.keySet()) {
            if (files.get(file)[1] != "") {
                files.get(file)[2] = files.get(file)[1];
            }
        }
    }

    public boolean modified(String fileName) {
        assert contains(fileName);
        String[] versions = files.get(fileName);
        // TODO does this work? Do I need to distinguish which is modified here?
        return !versions[0].equals(versions[1]) || !versions[0].equals(versions[2]);
    }

    public void stageModified(String fileName) {
        assert contains(fileName);
        String[] versions = files.get(fileName);
        versions[1] = versions[0];
    }

    @Override
    public void dump() {
        System.out.println(this);
    }

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
