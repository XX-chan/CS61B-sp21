package gitlet;


import java.io.File;
import java.util.*;

import static gitlet.Methods.readAsIndex;
import static gitlet.Utils.*;

public class Status {
    /**打印以下列表
     * Branch，所有分支名称，当前分支名称前加 *’
     * Stage File，暂存区域内的added文件；
     * Removed File, 暂存区域中removed的文件。
     * Modifications Not Staged For Commit，我没做留空；
     * Untracked Files 我没做留空。
     */
    public static void printStatus() {
        Index idx = readAsIndex();
        printFilename("=== Branches ===", findAllBranches());
        printFilename("\n=== Staged Files ===", idx.getAddedFilesname());
        printFilename("\n=== Removed Files ===", idx.getRemovedFilesname());
        printFilename("\n=== Modifications Not Staged For Commit ===", modificationsNotStaged());
        printFilename("\n=== Untracked Files ===", getUntrackedFileName());
        System.out.println();
    }

    public static Set<String> modificationsNotStaged() {
        return new HashSet<>();
    }

    /** 打印message 和文件名。 */
    private static void printFilename(String message, Collection<String> names) {
        System.out.println(message);
        if (names != null) {
            names.forEach(System.out::println);
        }
    }

    private static void printFilename(String message, Set<String> names) {
        printFilename(message, new ArrayList<>(names));
    }


    /** 找到所有Branch的名字。
     * 且将当前分支的名称前加*。
     */
    private static List<String> findAllBranches() {
        List<String> bs = plainFilenamesIn(Repository.BRANCHES_DIR);
        if (bs == null) {
            return null;
        }
        List<String> branches = new ArrayList<>();
        String currname = Methods.readHEADAsBranch().getName();
        for (String name : bs) {
            if (name.equals(currname)) {
                branches.add("*" + name);
            } else {
                branches.add(name);
            }
        }
        return branches;
    }

    /**获取当前工作区中未被追踪的所有文件名称。*/
    public static Set<String> getUntrackedFileName() {
        Set<String> untracked = new HashSet<>();
        Commit currCommit = Methods.readHEADAsCommit();
        List<String> files = plainFilenamesIn(Repository.CWD);
        if (files == null) {
            return untracked;
        }
        for (String f : files) {
            File file = join(Repository.CWD, f);
            Index idx = readAsIndex();
            if (!idx.isTracked(file, currCommit)) {
                untracked.add(file.getName());
            }
        }
        return untracked;
    }



}
