package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Utils.message("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        // TODO argument validation
        // TODO test if in a gitlet directory before running commands other than init
        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1, 1);
                Repository.init();
                break;
            case "add":
                validateNumArgs(args, 2, 2);
                validateRepo();
                // TODO: handle the `add [filename]` command
                break;
            case "cat-file":
                validateRepo();
                validateNumArgs(args, 3, 3);
                String type = args[1];
                String hash = args[2];
                Repository.catFile(type, hash);
                break;
            default:
                System.out.println("No command with that name exists.");
        }
    }

    public static void validateNumArgs(String[] args, int min, int max) {
        if (args.length < min || args.length > max) {
            Utils.message("Incorrect operands.");
            System.exit(0);
        }
    }

    public static void validateRepo() {
        if (!Repository.GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
        }
    }
}
