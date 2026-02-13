package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static gitlet.Methods.*;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *  .gielet/
 *  ├── HEAD
 *  ├── objects/
 *  ├── refs/
 *  │    └── heads/
 *  │    └── commits/
 *  ├── index
 *
 *  @author TODO
 */
public class Repository implements Serializable{
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The HEAD pointer */
    public static final File HEAD = join(GITLET_DIR, "HEAD");

    /** The references' directory. */
    public static final File REFS_DIR = join(GITLET_DIR, ".refs");

   /** The commit file contains all commits' id. */
   public static final File COMMITS = join(REFS_DIR, "commits");
    public static final File REMOTES= join(REFS_DIR, "commits");

    /** The branch directory. */
    public static final File BRANCHES_DIR = join(REFS_DIR, "heads");

    /** The index directory. */
    public static final File INDEX = join(GITLET_DIR, "index");

    /** The objects directory which stored commits and blobs. */
     public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");

     /** 在当前目录下创建一个新的gitlet控制板本系统。*/
     public static void initializeRepo() {
         List<File> dirs = List.of(GITLET_DIR, REFS_DIR,OBJECTS_DIR, BRANCHES_DIR);
         dirs.forEach(File::mkdir);
         Branch h = new Branch("master","");
         writeObject(HEAD, h);
         h.updateBranch();
         writeObject(INDEX, new Index());
         writeContents(COMMITS, "");
     }


    /** 删除文件夹中所有内容。
     */
    public static void clean(File dir) {
        List<String> files = plainFilenamesIn(dir);
        if (files != null) {
            for (String f : files) {
                File file = join(dir,f);
                file.delete();
            }
        }
    }
}






