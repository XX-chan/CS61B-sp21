package gitlet;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import static gitlet.Methods.*;
import static gitlet.Repository.COMMITS;
import static gitlet.Utils.*;

/**
 *  @author ChanX
 */
public class Commit implements Serializable {
    /**
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**The SHA-1 id of this Commit. */
    private String uid;

    /** The message of this Commit. */
    private String message;

    /** The parent commit of this commit. */
    private String parent;

    /** The second parent which not null when creates a merge commit. */
    private String secondparent;

    /** The timestamp of this commit. */
    private Date date;

    /** The snapshots of file of this commit;
     * The key is files in CWD with absolute path;
     * The value is SHA-1 of blob.
     */
    private Map<String, String> blobs;

    /** Instantiate a commit object with message and parent commit id;
     * The first commit with message "initial commit" has no parent.
     */
    public Commit(String message, String parent) {
        instantiateCommit(message, parent, null);
    }

    public Commit(String message, String parent, String secondparent) {
        instantiateCommit(message, parent, secondparent);
    }

    /** Write this commit object in COMMIT_DIR;
     * and reset the HEAD pointer;
     */
    public void makeCommit() {
        if (this.parent != null) {
            this.blobs = this.getParentAsCommit().blobs;
        }
        Index index = readAsIndex();
        //blobs中添加added中的文件。
        Boolean isStage = getStage(index);
        //blobs中删除removed中的文件。
        Boolean isunStage = unStage(index);
        if (this.parent != null && !isStage && !isunStage) {
            exit("No changes added to the commit.");
            return;
        }
        setUid();
        //OBJECT里面创建新文件夹。
        File commitpath = makeObjectDir(this.uid);
        //将新commit对象保存到OBJECT里
        writeObject(commitpath, this);
        //清空stage area。
        index.clearStageArea();

        //更新HEAD指向新commit对象
        setHead(this, readHEADAsBranch());
        //将新commit的id添加到COMMIT里。
        String cs = readContentsAsString(COMMITS);
        cs += this.uid;
        writeContents(COMMITS, cs);
    }


    /** 生成commit的id。 */
    private String setUid() {
        this.uid = sha1(this.parent + this.date + this.message);
        return this.uid;
    }



    public Commit getParentAsCommit() {
        return toCommit(this.parent);
    }

    /** 将stage area里面的added文件，添加到blobs里；
     */
    private boolean getStage(Index i) {
        boolean found = false;
        Map<String, String> added = i.getAdded();
        if (!added.isEmpty()) {
            found = true;
            this.blobs.putAll(added);
        }
        return found;
    }

    /** 在commit的blobs里面删除removed的文件；
     */
    private boolean unStage(Index i) {
        boolean found = false;
        Set<String> removed = i.getRemoved();
        if (!removed.isEmpty()) {
            found = true;
            for (String f : removed) {
                this.blobs.remove(f);
            }
        }
        return  found;
    }


    public Map<String, String> getBlobs() {
        return blobs;
    }

    /** Return the value of the blobs in the current commit.*/
    public String getBlob(File f) {
        return blobs.get(f.getAbsolutePath());
    }



    public void instantiateCommit(String message, String first, String second) {
        this.message = message;
        this.parent = first;
        this.secondparent = second;
        if (first == null)  {
            this.date = new Date(0);
        } else {
            this.date = new Date();
        }
        this.blobs = new HashMap<>();
    }

    public String getid() {
        return this.uid;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getDate() {
        return this.date;
    }

    public String getParent() {
        return this.parent;
    }

    public static List<String> findCommitsWithMessage(String message) {
        Set<Commit> commits = findAllCommits();
        List<String> result = new ArrayList<>();
        for (Commit c : commits) {
            if (c.getMessage().equals(message)) {
                result.add(c.getid());
            }
        }
        return result;
    }

    /* 找到所有已生成的Commit对象。 */
    public static Set<Commit> findAllCommits() {
        Set<Commit> commits = new HashSet<>();
        String cs = readContentsAsString(COMMITS);
        while (!cs.isEmpty()) {
            Commit curr = toCommit(cs.substring(0, 40));
            commits.add(curr);
            cs = cs.substring(40);
        }
        return commits;
    }

    /** 根据给定的参数id,找到对应的commit对象。*/
    public static Commit findCommit(String id) {
        if (id == null) {
            return null;
        }
        Commit curr = toCommit(id);
        if (curr != null) {
            return curr;
        }
        return toCommit(id.substring(0, 8));
    }





}
