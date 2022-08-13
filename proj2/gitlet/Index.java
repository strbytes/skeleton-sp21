package gitlet;
import java.io.File;
import java.util.*;

public class Index implements Dumpable {
    private List<String> files;
    private Map<String, String[]> versions;

    public Index() {
        files = new ArrayList<>();
        versions = new HashMap<>();
    }

    public void addFile(File file) {
        assert (!contains(file));
        String fileName = file.getName();
        String fileContents = Utils.readContentsAsString(file);
        String hash = Utils.sha1(fileContents);
        files.add(fileName);
        versions.put(fileName, new String[] {"", "", hash});
    }

    public boolean contains(String fileName) {
        return files.contains(fileName);
    }

    public boolean contains(File file) {
        return files.contains(file.getName());
    }

    @Override
    public void dump() {
        System.out.println(files);
    }
}
