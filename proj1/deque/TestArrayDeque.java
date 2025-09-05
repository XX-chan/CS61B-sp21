package deque;

import org.junit.Test;
import static org.junit.Assert.*;

/** ArrayDeque test.*/
public class TestArrayDeque {

    /** Test add functionality.*/
    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();
        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());

        ad1.addFirst("Hei!");

        assertEquals("ArrayDeque should now contain 1 item", 1, ad1.size());
        assertFalse("ArrayDeque should now be empty", ad1.isEmpty());

        ad1.addLast("testing");
        assertEquals(2, ad1.size());

        ad1.addLast("AD");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    public void removeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());

        ad1.addFirst(88);
        assertFalse("ArrayDeque should not be empty", ad1.isEmpty());

        ad1.removeFirst();
        assertTrue("ArrayDeque should now be empty", ad1.isEmpty());

    }

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();

        int targetsize = 1000;
        for (int i = 0; i < targetsize; i++) {
            ad1.addLast(i);
        }

        for (int i = 0; i < targetsize; i++) {
            ad2.addFirst(i);
        }

        assertEquals(targetsize, ad1.size());
        assertEquals(targetsize, ad2.size());

        int getItem1 = ad1.get(99);
        assertEquals(99, getItem1);

        int getItem2 = ad2.get(0);
        assertEquals(targetsize - 1, getItem2);

        assertNull(ad1.get(targetsize));
    }


}
