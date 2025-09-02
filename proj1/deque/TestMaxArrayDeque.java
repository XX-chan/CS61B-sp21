package deque;
import org.junit.Test;
import org.junit.Assert;
import java.util.*;
import java.util.Comparator;


public class TestMaxArrayDeque {
    /* Test the MaxArrayDeque using the StrComparator.*/
    @Test
    public void testStrMaxArrayDeque() {
        List<String> testlist = Arrays.asList("Lisa", "John", "Betty", "Michael");
        Comparator<String> comparator = StrComparator.getStrComparator();
        MaxArrayDeque<String> testdeque = new MaxArrayDeque<>(comparator);
        for (String s : testlist) {
            testdeque.addLast(s);
        }

        String maxElemet = testdeque.max();
        String expected = "Michael";
        Assert.assertEquals("Test failed for max() method", expected, maxElemet);

        String maxElemet2 = testdeque.max(new StrComparator());
        String expected2 = "Michael";
        Assert.assertEquals("Test failed for max(Comparator) method", expected2, maxElemet2);
    }

    /* Test the MaxArrayDeque using the IntComparator.*/
    @Test
    public void testIntMaxArrayDeque() {
        List<Integer> testlist = Arrays.asList(7, 9, 12, 69, 0, 25);
        Comparator<Integer> comparator = IntComparator.getIntComparator();
        MaxArrayDeque<Integer> testdeque = new MaxArrayDeque<>(comparator);
        for (Integer s : testlist) {
            testdeque.addLast(s);
        }

        int maxElemet = testdeque.max();
        int expected = 69;
        Assert.assertEquals("Test failed for max() method in int", expected, maxElemet);

        int maxElemet2 = testdeque.max(IntComparator.getIntComparator());
        int expected2 = 69;
        Assert.assertEquals("Test failed for max() method in int", expected2, maxElemet2);
    }

    public static void main(String[] args) {
        TestMaxArrayDeque test = new TestMaxArrayDeque();
        test.testStrMaxArrayDeque();
        test.testIntMaxArrayDeque();
    }
}
