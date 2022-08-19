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

    public boolean contains(String fileName) {
        return files.containsKey(fileName);
    }

    public boolean modified(String fileName) {
        assert contains(fileName);
        String[] versions = files.get(fileName);
        // TODO does this work? Do I need to distinguish which is modified here?
        return versions[0].equals(versions[1]) || versions[0].equals(versions[2]);
    }

    public void stageModified(String fileName) {
        assert contains(fileName);
        String[] versions = files.get(fileName);
        versions[1] = versions[0];
    }

    @Override
    public void dump() {
        for (String f: files.keySet()) {
            System.out.println(f);
            for (int i = 0; i < 3; i++) {
                String header = versionHeaders[i];
                String value = files.get(f)[i];
                System.out.println("\t" + header + "\t" + value);
            }
        }
        System.out.println();
    }
}
