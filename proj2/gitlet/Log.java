package gitlet;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

public class Log {

    /** 从当前HEAD指向的 commit开始，
     * 沿着提交树向后显示每个提交的信息
     * 直到初始提交为止。
     */
    public static void log(Commit c) {
        while (c != null) {
            printLog(c);
            c = Commit.findCommit(c.getParent());
        }
    }

    /** 显示所有提交信息，顺序不重要。
     * 意味着不止是沿着parent回溯。
     */
    public static void globalLog() {
        Set<Commit> allCommits = Commit.findAllCommits();
        for (Commit c : allCommits) {
            printLog(c);
        }
    }


    /** 打印commit的方法；
     * 设定好了显示的格式。
     * */
    public static void printLog(Commit c) {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        String dateStr = sdf.format(c.getDate());
        System.out.println("===");
        System.out.println("commit " + c.getid());
        System.out.println("Date: " + dateStr);
        System.out.println(c.getMessage());
        System.out.println();
    }

}
