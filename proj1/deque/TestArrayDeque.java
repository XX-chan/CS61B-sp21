package deque;
import org.junit.Assert;
import org.junit.Test;



public class TestArrayDeque {
    @Test
    public void testArrayDeque() {
        ArrayDeque<String> testdeque= new ArrayDeque<>();
        testdeque.addFirst("Apple");
        testdeque.addFirst("Banana");
        testdeque.addLast("Pear");
        testdeque.addLast("Pineapple");
        testdeque.addLast("Pineapple");
        testdeque.addFirst("Melon");
        testdeque.addFirst("Grapefruit");
        testdeque.addLast("strawberry");
        testdeque.addLast("watermelon");
        testdeque.addLast("cherry");

        /* Testing Size.*/
        int resultSize1 = testdeque.size();
        int expectedSize1 = 10;

        System.out.println("resultSize1: " + resultSize1);
        Assert.assertEquals(expectedSize1, resultSize1);

        /* Testing length.*/
        int resultlength1 = testdeque.getItemsLength();
        int expectedlength1 = 16;

        System.out.println("resultlength1: " + resultlength1);
        Assert.assertEquals(expectedlength1, resultlength1);

        /* Testing removeFirst.*/
        String resultremoveFirst = testdeque.removeFirst();
        String expectedremoveFirst = "Grapefruit";

        System.out.println("resultremoveFirst: " + resultremoveFirst);
        Assert.assertEquals(expectedremoveFirst, resultremoveFirst);

        /* Testing removeFirst.*/
        String resultremoveFirst2 = testdeque.removeFirst();
        String expectedremoveFirst2 = "Melon";

        System.out.println("resultremoveFirst2: " + resultremoveFirst2);
        Assert.assertEquals(expectedremoveFirst2, resultremoveFirst2);

        /* Testing removeLast.*/
        String resultremoveLast = testdeque.removeLast();
        String expectedremoveLast = "cherry";
        System.out.println("resultremoveLast: " + resultremoveLast);
        Assert.assertEquals(expectedremoveLast, resultremoveLast);



    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("Deque.TestArrayDeque");
    }
}
