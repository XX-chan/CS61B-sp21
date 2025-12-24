package gitlet;

import java.io.File;

import static gitlet.Utils.*;
import static gitlet.Repository.*;

/** Represent helper methods.*/

public class Methods {

    /** judge whether the number of operands if correct;
     * exit(0) if operands are incorrect.
     */
    public static void judgeOperands(String[] args, int num) {
        judgeOperands(num, num, args);
    }

    public static void judgeOperands(int min, int max, String[] args) {
        if (args.length < min + 1 || args.length > max +1) {
            exit("Incorrect operands.");
        }
    }

    /** 判断.gitlet文件夹是否存在
     */
    public static void repoExits() {
        File repo = join(CWD,".gitlet");
        if (!repo.exists()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }

    /** 判断输入的命令，.gitlet文件夹是否存在，以及参数数量是否正确。
     */
    public static void judgeCommand(String[] args, int num) {
        repoExits();
        judgeOperands(args,num);
    }


    /** exit(0) before print message.*/
    public static void exit(String message) {
        if (message == null) {
            System.out.println(message);
        }
        System.exit(0);
    }

    /** return the Index object.
     */
    public static Index readAsIndex() {
        return readObject(INDEX, Index.class);
    }

    /** @return The barnch which HEAD points to. */
     public static Branch readHEADAsBranch() {
         return readObject(HEAD, Branch.class);
     }





     /** @return The commit id which HEAD points to. */
     public static String readHEADAsCommitId() {
        return readHEADAsBranch().getHEAD();
     }

     /** @return The commit which HEAD points to. */
     public static Commit readHEADAsCommit() { return readObject(HEAD, Commit.class); }

    /**通过给的参数ID返回一个commit
     * @return a commit
     * 参数是一个commit id。
     */
    public static Commit toCommit(String uid) {
        File commitFile = join(OBJECTS_DIR,uid);
        Commit c = readObject(commitFile, Commit.class);
        return c;
    }







}
