package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import java.util.List;


/** Represent gitlet branch pointer object. */

public class Branch implements Serializable {
    private String name;   //分支名字
    private String HEAD;   //分支指向的最新commit对象的 id。

    public Branch(String name, String head) {
        if (isExists(name)) {
            Methods.exit("A branch with that name already exists.");
        }
        this.name = name;
        this.HEAD = head;
    }

    /** Determine whether the branch name exists. */
    public static boolean isExists(String name) {
        List<String> names = plainFilenamesIn(BRANCHES_DIR);
        return name != null && names.contains(name);
    }


    public static String correctName(String name) {
        return name.replace("/", "_");
    }

    /* 根据给的名字，获取Branch对象。
     */
    public static Branch readBranch(String branchName) {
        return readBranch(branchName, BRANCHES_DIR);
    }

    public static Branch readBranch(String branchName, File file) {
        String name = correctName(branchName);
        File branchfile = join(file, name);
        if (!branchfile.exists()) {
            return null;
        }
        return readObject(branchfile, Branch.class);
    }

    public String getName() {
        return this.name;
    }

    public String getHEADAsString() {
        return this.HEAD;
    }

    public Commit getHEADAsCommit() {
        return Methods.toCommit(this.HEAD);
    }

    /* 更新HEAD的指向。*/
    public String resetHEAD(String newid) {
        this.HEAD = newid;
        return this.HEAD;
    }

    /* 更新Branch，写入branch磁盘。*/
    public void updateBranch() {
        String n = this.name;
        n = correctName(n);
        File h = join(BRANCHES_DIR, n);
        writeObject(h, this);
    }


}
