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

        switch(firstArg) {
            case "init":
                validateNumArgs(args, 1, 1);
                Repository.init();
                break;
            case "add":
                validateNumArgs(args, 2, 2);
                validateRepo();
                String fileName = args[1];
                Repository.add(fileName);
                break;
            case "cat-file":
                validateNumArgs(args, 3, 3);
                validateRepo();
                String type = args[1];
                Repository.catFile(type, Utils.getFullHash(args[2]));
                break;
            case "ls-files":
                validateNumArgs(args, 1, 1);
                validateRepo();
                Repository.lsFiles();
                break;
            case "commit":
                validateNumArgs(args, 2, 2);
                validateRepo();
                Repository.commit(args[1]);
                break;
            case "checkout":
                validateNumArgs(args, 2, 4);
                validateRepo();
                switch(args.length) {
                    case 2:
                        // TODO this is supposed to be checkout branch
                        Repository.checkoutCommit(Utils.getFullHash(args[1]));
                        break;
                    case 3:
                        Repository.checkoutFile(args[2]);
                        break;
                    case 4:
                        Repository.checkoutFileFromCommit(args[1], args[3]);
                        break;
                    default:
                        break;
                }
                break;
            case "log":
                validateNumArgs(args, 1, 1);
                validateAndUpdateRepo();
                Repository.printLog();
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

    /** Ensure program is being run in a valid Gitlet directory. Update any
     * changes to the working directory in the index. */
    public static void validateRepo() {
        if (!Repository.GITLET_DIR.exists()) {
            Utils.message("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        Repository.updateIndex();
    }
}
