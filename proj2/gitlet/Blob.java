package gitlet;

import java.io.Serializable;
import java.io.File;

import static gitlet.Methods.makeObjectDir;
import static gitlet.Utils.*;
import static gitlet.Repository.*;

/** Represent a blob object of gitlet commit object.*/

public class Blob implements Serializable {
   /** The content of the file blob points to.*/
    private final String content;

    /**  /**The SHA-1 id of this Blob. */
    private final String uid;

    /**
     * Instantiate a blob object with a File.
     * A blob means a snapshot of tracked file. */
    public Blob(File f) {
        this.content = readContentsAsString(f);
        this.uid = getBlobUid(f);
    }

    /**将blob对象写入磁盘；
     * @return blob对象的id。
     */
    public String makeBlob() {
        File f = makeObjectDir(this.uid);
        writeObject(f, this);
        return this.uid;
    }

    /** Creat blob id using content and filename.
     *and write blob in Objects.
     * @return The blob SHA-1 id.
     */
    public static String getBlobUid(File f) {
       return sha1(readContentsAsString(f) + f.getName());
    }

    public String getContent() {
        return content;
    }
    public String getUid() {
        return uid;
    }




    /** Writes Blob as object to
     *
     * @param f
     * @return blob id.
     */


}
