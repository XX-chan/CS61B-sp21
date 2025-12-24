package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import java.util.List;

/** Represent gitlet branch pointer object. */

public class Branch implements Serializable {
    private String name;   //分支名字
    private String HEAD;   //分支指向的最新commit对象的 id;同时也是HEAD。

    public Branch(String name, String head) {
        if (isExists(name)) {
            throw new GitletException("Branch already exists");
        }
        this.name = name;
        this.HEAD= head;
    }

    /** Determine whether the branch name exists. */
    public boolean isExists(String name) {
        name = correctName(name);
        List<String> names = plainFilenamesIn(BRANCHES_DIR);
        return name != null && names.contains(name);
    }


    public String correctName(String name) {
        return name.replace("/", "_");
    }

    public String getName() {
        return name;
    }

    public String getHEADAsString() {
        return this.HEAD;
    }

    public Commit getHEADAsCommit() {
        return Methods.toCommit(this.HEAD);
    }

    /* 更新HEAD的指向。*/
    public String resetHEAD(String newid) {
        return this.HEAD = newid;
    }

    /* 更新Branch，写入branch磁盘。*/
    public void updateBranch() {
        String currHEAD = readObject(Repository.HEAD, Branch.class).getHEADAsString();
        this.HEAD = currHEAD;
        String n = this.name;
        n = correctName(n);
        File h = join(BRANCHES_DIR, n);
        writeObject(h, this);
    }
}
