package gitlet;

import java.io.File;

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
public class Repository {
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
    public static final File HEAD = join(GITLET_DIR, ".HEAD");

    /** The references' directory. */
    public static final File REFS_DIR = join(GITLET_DIR, ".refs");

   /** The commit file contains all commits' id. */
   public static final File COMMITS = join(REFS_DIR, "commits");

    /** The branch directory. */
    public static final File BRANCHES_DIR = join(REFS_DIR, "heads");

    /** The index directory. */
    public static final File INDEX = join(GITLET_DIR, "inedx");

    /** The objects directory which stored commits and blobs. */
     public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");



    /** Command 'init' initialize `.gitlet`
     * to initialize gitlet repository. */
    public static void init(String[] args) {
        judgeOperands(args, 0);
        File repo = join(CWD, ".gitlet");
        if (repo.exists()) {
            exit("A Gitlet version-control system already exists in the current directory.");
        }
        System.exit(0);
        Commit commit = new Commit("initial commit", null);
        commit.makeCommit();
    }


    /** Command 'add + filename' to add file to staging directory. */
    public static void add(String[] args) {
        judgeCommand(args, 1);
        File inFile = join(CWD,args[1]);
        if (!inFile.exists()) {
            exit("The file " + args[1] + " does not exist.");
        }
        readAsIndex().add(inFile);
    }

    /** Command 'commit + message' to make a commit.*/
    public static void commit(String[] args) {
        repoExits();
        if (args.length < 2 || args[1].equals("")) {
            exit("Please enter a commit message.");
        }
        judgeOperands(args, 2);


    }

}
