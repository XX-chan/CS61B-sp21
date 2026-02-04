package gitlet;

import java.io.File;
import java.util.Map;
import java.util.Set;

import static gitlet.Methods.*;
import static gitlet.Repository.HEAD;
import static gitlet.Utils.*;

public class Checkout {

    /** 获取file在头提交中存在的文件版本，
     * 并将其放到工作目录中；
     * 如果文件已存在，覆盖已经存在的文件版本。新版本的文件不会暂存。
     */
    public static void checkoutFile(File name) {
        checkoutFile(Methods.readHEADAsCommit(), name);
    }

    /**获取指定 id 的提交中存在的文件版本，并将其放到工作目录中,
     * 如果有的话，覆盖已经存在的文件版本。新版本的文件不会暂存。
    */
    public static void checkoutFile(Commit c, File name) {
        String oldBlob = c.getBlob(name);
        if (oldBlob == null) {
            exit("File does not exist in that commit.");
        }
        File oldBlobpath = join(Methods.makeObjectDir(oldBlob));

        reStoreBlob(name, oldBlobpath);
    }

    /**读取blob文件内容，然后将内容写入文件。
     * @param file 要checkout的文件；
     * @param checkFrom 指向文件的blob。
     */
    private static void reStoreBlob(File file, File checkFrom) {
        Blob  oldBlob = readObject(checkFrom, Blob.class);
        String blobcontent = oldBlob.getContent();
        writeContents(file, blobcontent);

    }

    /**获取指定分支头部提交中的所有文件，并将其放入工作目录，
     * 如果存在，则覆盖已存在的文件版本;
     * 在该命令结束时，给定分支将被视为当前分支（HEAD）
     */
    public static void checkoutBranch(String name) {
        if (! Branch.isExists(name)) {
            exit("No such branch exists.");
        }

        //读取HEA指向的分支
        Branch currbranch = readObject(HEAD, Branch.class);
        if (currbranch.toString().equals(name)) {
            exit("No need to checkout the current branch.");
        }

        //检查工作区的当前分支文件是否有被追踪；
        Methods.untrackedExit();

        Repository.clean(Repository.CWD);

        Branch branch = Branch.readBranch(name);
        //分支指向的最新Commit对象。
        Commit oldCommit = branch.getHEADAsCommit();

        //获取Commit对象的所有blobs。
        Map<String,String> oldblobs = oldCommit.getBlobs();
        //遍历Commit对象的所有blob，并将它们都写入工作目录。
        for (String key : oldblobs.keySet()) {
            String blobvalue = oldblobs.get(key);
            reStoreBlob(join(key), join(Methods.makeObjectDir(blobvalue)));
        }
        Methods.readAsIndex().clearStageArea();
        Methods.setHead(oldCommit, branch);
    }

    /** Checks out all the files tracked by the given commit.
     * Removes tracked files that are not present in that commit
     * checkout 给的commitId 的所有文件到工作目录中；
     * Also moves the current branch’s head to that commit node.
     */
    public static void reset(Commit c) {
        Map<String,String> oldblobs = c.getBlobs();
        Set<String> untracked = Status.getUntrackedFileName();
        for (String filename : untracked) {
            if(oldblobs.containsKey(filename)) {
                exit("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
        Repository.clean(Repository.CWD);
        Methods.readAsIndex().clearStageArea();
        for (String key : oldblobs.keySet()) {
            File f = join(key);
           checkoutFile(c, f);
        }
        setHead(c, readHEADAsBranch());
    }




}

