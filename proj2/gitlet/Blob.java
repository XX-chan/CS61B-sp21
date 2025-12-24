package gitlet;

import java.io.Serializable;
import java.io.File;
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

    /** Creat blob id using content and filename.
     *and write blob in Objects.
     * @return The blob SHA-1 id.
     */
    public static String getBlobUid(File f) {
        byte[] content = readContents(f);
        String uid = sha1(content);

        File blobFile = join(OBJECTS_DIR, uid);
        if (!blobFile.exists()) {
            writeContents(blobFile, content);
        }
        return uid;
    }




    /** Writes Blob as object to
     *
     * @param f
     * @return blob id.
     */

    /** 还不知道咋写，空着，后面再来补上。

    public String makeBlob(File f) {
        return ;
    }*/
}
