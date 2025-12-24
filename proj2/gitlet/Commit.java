package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.*;
import static gitlet.Methods.*;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
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
     * The key is name;
     * The value is SHA-1 of blob.
     */
    private Map<String,String> blobs;


    /** Instantiate a commit object with message and parent commit id;
     * The first commit with message "initial commit" has no parent.
     */
    public Commit(String message, String parent) {
        instantiateCommit(message,parent,null);
    }

    public Commit(String uid, String message, String parent, String secondparent) {
        instantiateCommit(message,parent,secondparent);
    }

    /** Write this commit object in COMMIT_DIR;
     * and reset the HEAD pointer;
     */
    public void makeCommit() {
       if (this.parent != null) {
           Commit cparent = toCommit(this.parent);
           this.blobs = new HashMap<> (cparent.blobs);
       }
       Index index = readAsIndex();
       //blobs添加added的文件。
       Boolean isStage = getStage(index);
       //blobs删除removed的文件。
       Boolean isunStage = unStage(index);
       if (this.parent != null && !isStage && !isunStage) {
           exit("No changes added to the commit.");
       }
       setUid();


    }

    /** 生成commit的id。 */
    private String setUid() {
        return this.uid = sha1(this.parent + this.date + this.message);
    }

    /** 将stage area里面的added文件，添加到blobs里；
     */
    private boolean getStage(Index i) {
        boolean found = false;
        Map<String,String> added = i.getAdded();
        if (!added.isEmpty()) {
            this.blobs.putAll(added);
            found = true;
        }
        return found;
    }

    /** 在commit的blobs里面删除removed的文件；
     */
    private boolean unStage(Index i) {
        boolean found = false;
        Set<String> removed = i.getRemoved();
        if (!removed.isEmpty()) {
            for (String f : removed) {
                this.blobs.remove(f);
                restrictedDelete(f);
            }
            found = true;
        }
        return  found;
    }


    public Map<String,String> getBlobs() {
        return blobs;
    }

    /** Return the key of the blobs in the current commit.*/
    public String getBlob(File f) {
        return blobs.get(f.getName());
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
}
