package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                GitletUtils.init(args);
                break;
            case "add":
                GitletUtils.add(args);
                break;
            case "commit":
                GitletUtils.commit(args);
                break;
            case "remove":
                GitletUtils.remove(args);
                break;
            case "log":
                GitletUtils.log(args);
                break;
            case "global-log":
                GitletUtils.globalLog(args);
                break;
            case "find":
                GitletUtils.find(args);
                break;
            case "status":
                GitletUtils.status(args);
                break;
            case "checkout":
                GitletUtils.checkout(args);
                break;
            case "branch":
                GitletUtils.branch(args);
                break;
            case "rm-branch":
                GitletUtils.rmbranch(args);
                break;
            case "reset":
                GitletUtils.reset(args);
                break;




        }
    }
}
