package gitlet;

import java.io.File;
import java.util.List;

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
        if (args.length < min + 1 || args.length > max + 1) {
            exit("Incorrect operands.");
        }
    }

    /** 判断.gitlet文件夹是否存在
     */
    public static void repoExits() {
        File repo = join(CWD, ".gitlet");
        if (!repo.exists()) {
            exit("Not in an initialized Gitlet directory.");
        }
    }

    /** 判断输入的命令，.gitlet文件夹是否存在，以及参数数量是否正确。
     */
    public static void judgeCommand(String[] args, int num) {
        repoExits();
        judgeOperands(args, num);
    }


    /** exit(0) before print message.*/
    public static void exit(String message) {
        if (message != null) {
            System.out.println(message);
            System.exit(0);
        }
    }

    /** return the Index object.
     */
    public static Index readAsIndex() {
        return readObject(INDEX, Index.class);
    }

    /* 将HEAD 指针指向一个commit对象。
     */
    public static void setHead(Commit commit, Branch branch) {
        setHead(commit, branch, GITLET_DIR);
    }

    public static void setHead(Commit c, Branch b, File dir) {
        //更新HEAD的指向。
        b.resetHEAD(c.getid());
        //更新branch的HEAD，写入branch磁盘。
        b.updateBranch();
        writeObject(join(dir, "HEAD"), b);

    }

    /** @return HEAD 指向的内容；
     */
    public static String readHEADContent() {
        return readHEADAsBranch().getHEADAsString();
    }

    /** @return The commit which HEAD points to. */
    public static Commit readHEADAsCommit() {
        String head = readHEADContent();
        return toCommit(head);
    }

    /** @return The Branch which HEAD points to. */
    public static Branch readHEADAsBranch() {
        return readObject(HEAD, Branch.class);
    }




    /**通过给的参数ID返回一个commit
     * @return a commit
     * 参数是一个commit id。
     */
    public static Commit toCommit(String uid) {
        return toCommit(uid, OBJECTS_DIR);
    }

    /**通过给的参数ID返回一个commit
     * @param uid 是commit的uid；
     * @param targetDir 是commit所在的文件夹。
     * @return 给定的id的commit对象。
     */
    public static Commit toCommit(String uid, File targetDir) {
        File c = getObject(uid, targetDir);
        if (c == null) {
            return null;
        }
        if (c.exists()) {
            return readObject(c, Commit.class);
        }
        return null;
    }

    /**
     * @return 对象的文件路径。
     */
    public static File getObject(String uid, File targetDir) {
        if (uid == null || uid.isEmpty()) {
            return null;
        }
        File obj = join(targetDir, uid.substring(0, 2));
        String rest = uid.substring(2);
        if (uid.length() == 8) {
            List<String> objects = plainFilenamesIn(obj);
            if (objects == null) {
                return null;
            }
            for (String commit : objects) {
                if (commit.substring(0, 6).equals(rest)) {
                    obj = join(obj, commit);
                    break;
                }
            }
        } else {
            obj = join(obj, rest);
        }
        return obj;
    }

    /* 在OBJECTS中新建一个保存commit或者blob对象的file；
     * 文件夹名称为id前2位，对象名称为id后38位。
     * 返回commit或者blob对象绝对路径。
     */
    public static File makeObjectDir(String id) {
        //获取id的绝对路径。
        File f = join(OBJECTS_DIR, id.substring(0, 2));
        if (!f.exists()) {
            f.mkdir();
        }
        //获取对象的名字。
        String objectName = id.substring(2);
        return join(f, objectName);
    }




    /**检查工作区中否是有未被追踪的文件。
     */
    public static void untrackedExit() {
        if (!Status.getUntrackedFileName().isEmpty()) {
            exit("There is an untracked file in the way; delete it, or add and commit it first");
        }
    }







}
