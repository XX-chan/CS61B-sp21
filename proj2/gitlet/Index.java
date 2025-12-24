package gitlet;


import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;
import java.io.*;
import static gitlet.Methods.*;
import static gitlet.Utils.*;

/** Represent a staging directory, gitlet-add, gitlet-rm. */

public class Index implements Serializable {

    /** The map of staged file.
     * key是文件路径，value是blob的id。*/
    private final Map<String, String> added;

    /** The set of removed files.*/
    private final Set<String> removed;


    public Index() {
        this.added = new HashMap<>();
        this.removed = new HashSet<>();
    }


    /**Add a copy of the file to the staging area;
     * If the add file exists in the staging area, add file overwrite the existing file.
     * Compare  the added file to the current commit.
     * If they are the same, do not copy add file to the staging area.
     * If added file is already in the temporary storage area, you need to remove rm.
     */
    public void add(File f) {
        if (!f.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        String file = f.getAbsolutePath();
        if (isRemoved(f)) {
            removed.remove(file);
        }
        Commit currCommit = readHEADAsCommit();
        //将文件blob写入磁盘。
        String blobUid = Blob.getBlobUid(f);
        if(isModified(f, currCommit)) {
            added.put(file, blobUid);
        }
        save();
    }

    /** 移除文件。
     *如果文件在staged内，added移除文件；
     * 文件不在added内且被追踪的状态下，移除文件
     */
    public boolean remove(File f) {
        boolean found = false;
        String file = f.getAbsolutePath();
        if (isStaged(f)) {
            added.remove(file);
            found = true;
        }
        if (!found && isTracked(f,readHEADAsCommit())) {
            removed.add(file);
            restrictedDelete(f);
            found = true;
        }
        save();
        return found;
    }


    /** 判断文件是否被追踪
     * return True 如果是被追踪的状态
     */
    private boolean isTracked(File f, Commit c) {
        return c.getBlob(f) != null;
    }


    /** Determine whether the file has been modified compared to the current commit object.
     * return false, if and only if file exits and isn't modified.
     */

    public boolean isModified(File f, Commit c) {
        if (!f.exists()) {
            return false;
        }
        String current = Blob.getBlobUid(f);
        String prevBlobs = c.getBlob(f);
        if (prevBlobs != null || current.equals(prevBlobs)) {
            return false;
        }
        return true;
    }


    /** 判断文件是否在暂存区内；
     * return true 表示在暂存区；
     */
    public boolean isStaged(File f) {
        return added.containsKey(f.getAbsolutePath());
    }

    /** 判断是否要移除
     * return true  文件在removed里面的
     */
    public boolean isRemoved(File f) {
        return removed.contains(f.getAbsolutePath());
    }

    /** Write Index object.
     */
    public void save(){
        writeObject(Repository.INDEX, this);
    }

    /** 获取Inedx的added。*/
    public Map<String,String> getAdded(){
        return added;
    }
    public Set<String> getRemoved(){
        return removed;
    }

    /** 清空stage area。
     * 也就是清空Index类的added，removed，并且保存写入磁盘。
     * */
    public void clearStageArea() {
        added.clear();
        removed.clear();
        save();
    }





}
