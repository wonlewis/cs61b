package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListTest = new AListNoResizing();
        BuggyAList<Integer> buggyAListTest = new BuggyAList();
        aListTest.addLast(4);
        buggyAListTest.addLast(4);
        aListTest.addLast(5);
        buggyAListTest.addLast(5);
        aListTest.addLast(6);
        buggyAListTest.addLast(6);

        aListTest.removeLast();
        buggyAListTest.removeLast();
        assertEquals(aListTest.get(0), buggyAListTest.get(0));
        assertEquals(aListTest.get(1), buggyAListTest.get(1));
        assertEquals(aListTest.get(2), buggyAListTest.get(2));

        aListTest.removeLast();
        buggyAListTest.removeLast();
        assertEquals(aListTest.get(0), buggyAListTest.get(0));
        assertEquals(aListTest.get(1), buggyAListTest.get(1));
        assertEquals(aListTest.get(2), buggyAListTest.get(2));

        aListTest.removeLast();
        buggyAListTest.removeLast();
        assertEquals(aListTest.get(0), buggyAListTest.get(0));
        assertEquals(aListTest.get(1), buggyAListTest.get(1));
        assertEquals(aListTest.get(2), buggyAListTest.get(2));
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
            } else if (operationNumber == 2) {
                // removeLast
                if (L.size() > 0){
                    Integer lGetLast = L.getLast();
                    Integer mGetLast = M.getLast();
                    assertEquals(L.getLast(), M.getLast());
                    assertEquals(L.removeLast(), M.removeLast());
                }
            }
        }
    }
}
