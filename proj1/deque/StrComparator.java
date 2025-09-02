package deque;
import java.util.Comparator;

public class StrComparator implements Comparator<String>{
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }

    public static Comparator<String> getStrComparator() {
        return new StrComparator();
    }
}
