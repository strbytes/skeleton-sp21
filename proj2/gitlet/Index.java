package gitlet;
import java.util.*;

public class Index implements Dumpable {
    private List<String> files;
    private Map<String, String> repo;
    private Map<String, String> staged;
    private Map<String, String> commit;

    public Index() {
        files = new ArrayList<>();
        repo = new HashMap<>();
        staged = new HashMap<>();
        commit = new HashMap<>();
    }

    @Override
    public void dump() {
        System.out.println(files);
    }
}
