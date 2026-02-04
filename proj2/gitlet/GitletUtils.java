package gitlet;

import static gitlet.Methods.*;
import static gitlet.Utils.*;
import static gitlet.Repository.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Represent gitlet commands. */

public class GitletUtils {

    /** Command 'init' initialize `.gitlet`
     * to initialize gitlet repository. */
    public static void init(String[] args) {
        judgeOperands(args, 0);
        File repo = join(CWD, ".gitlet");
        if (repo.exists()) {
            exit("A Gitlet version-control system already exists in the current directory.");
        }
        initializeRepo();
        Commit commit = new Commit("initial commit", null);
        commit.makeCommit();
    }


    /** Command 'add + filename' to add file to staging directory. */
    public static void add(String[] args) {
        judgeCommand(args, 1);
        File inFile = join(CWD,args[1]);
        if (!inFile.exists()) {
            exit("The file " + args[1] + " does not exist.");
        }
        readAsIndex().add(inFile);
    }

    /** Command 'commit + message' to make a commit.*/
    public static void commit(String[] args) {
        repoExits();
        if (args.length < 2 || args[1].equals("")) {
            exit("Please enter a commit message.");
        }
        judgeOperands(args, 1);
        String message = args[1];
        String h = readHEADContent();
        new Commit(message,h).makeCommit();
    }

    /** Command 'rm + filename' to remove file.*/
    public static void remove(String[] args) {
        judgeCommand(args, 1);
        File inFile = join(CWD,args[1]);
        Boolean result = readAsIndex().remove(inFile);
        if (!result) {
            exit("No reason to remove the file.");
        }
    }

    /** 命令‘log’ ，显示当前commit的所有历史提交信息。*/
    public static void log(String[] args) {
        judgeCommand(args, 0);
        Commit c = readHEADAsCommit();
        Log.log(c);
    }

    /** 命令‘global-log’,显示所有commit信息。
     * 不分顺序。
     */
    public static void globalLog(String[] args) {
        judgeCommand(args, 0);
        Log.globalLog();
    }

    /** 命令‘find',显示具有提交信息的所有commit id。
     */
    public static void find(String[] args) {
        judgeCommand(args, 1);
        List<String> uid = Commit.findCommitsWithMessage(args[1]);
        if (uid.isEmpty()) {
            exit("Found no commit with that message.");
        }
        uid.forEach(System.out::println);
    }

    /** 命令’status‘ 显示所有当前存在的分支。
     * 和哪些文件已被添加和删除。 */
    public static void status(String[] args) {
        judgeCommand(args, 0);
        Status.printStatus();
    }

    /** 命令’checkout--[file name]‘；
     * ’checkout [commit id] -- [file name]‘；
     * ’checkout [branch name]’
     */
    public static void checkout(String[] args) {
        repoExits();
        judgeOperands(1,3, args);
        if (args.length == 3 && args[1].equals("--")) {
            File file = join(CWD, args[2]);
            Checkout.checkoutFile(file);
        } else if (args.length == 4 && args[2].equals("--")) {
            Commit commit = Commit.findCommit(args[1]);
            if (commit == null) {
                exit("No commit with that id exists.");
            }
            File file = join(CWD, args[3]);
            Checkout.checkoutFile(commit,file);
        } else if (args.length == 2) {
            Checkout.checkoutBranch(args[1]);
        } else {
            exit("Incorrect operands.");
        }
    }

    /** 命令branch [branch name];
     * Create a new branch with [branch name],and points it at the current head commit;
     * Not switch to the newly created branch.
     */
    public static void branch(String[] args) {
        judgeCommand(args, 1);
        String head = readHEADContent();
        Branch b = new Branch(args[1], head);
        b.updateBranch();
    }


    /** The command rm-branch [branch name]
     * Only delete the pointer associated with the branch;
     */
    /**
    public static void rmbranch(String[] args) {
        judgeCommand(args, 1);
        String name = args[1];
        String currbranch = readHEADAsBranch().getName();
        File b = join(BRANCHES_DIR, name);
        if (name.equals(currbranch)) {
            exit("Cannot remove the current branch.");
        } else if (!b.exists()) {
            exit("A branch with that name does not exist.");
           }
        b.delete();
    }

    /** The command reset [commit id]
     *
     *
    public static void reset(String[] args) {
        judgeCommand(args, 1);
        Commit commit = toCommit(args[1]);
        if (commit == null) {
            exit("No commit with that id exists.");
        }
        Checkout.reset(commit);
    }*/



}
