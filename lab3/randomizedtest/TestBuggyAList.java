package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.om.Item;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */

public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testTreeAddTreeRemove() {
        BuggyAList <Integer> bAlist = new BuggyAList<>();
        AListNoResizing<Integer> alistNo = new AListNoResizing<>();

        bAlist.addLast(4);
        bAlist.addLast(5);
        bAlist.addLast(6);

        alistNo.addLast(4);
        alistNo.addLast(5);
        alistNo.addLast(6);

        assertEquals(bAlist.size(), alistNo.size());


        assertEquals(bAlist.removeLast(), alistNo.removeLast());
        assertEquals(bAlist.removeLast(), alistNo.removeLast());
        assertEquals(bAlist.removeLast(), alistNo.removeLast());
        }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList <Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);

            } else if (operationNumber == 1) {
                // size
                int csize = correct.size();
                int bsize = broken.size();

                if (csize > 0 && bsize > 0) {
                    Integer correctLast = correct.getLast();
                    Integer brokenLast = broken.getLast();

                    correct.removeLast();
                    broken.removeLast();

                    assertEquals(brokenLast, correctLast);
                }
            }
        }
    }



}
