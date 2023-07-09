package deque;

import org.junit.Test;

import java.awt.datatransfer.FlavorListener;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {


    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        Deque<String> lld1 = new ArrayDeque();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        Deque<Integer> lld1 = new ArrayDeque();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {


        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        Deque<String>  lld1 = new ArrayDeque();
        Deque<Double>  lld2 = new ArrayDeque();
        Deque<Boolean> lld3 = new ArrayDeque();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        Deque<Integer> lld1 = new ArrayDeque();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        Deque<Integer> lld1 = new ArrayDeque();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeReverseTest() {

        Deque<Integer> lld1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            lld1.addFirst(i);
        }

        for (double i = 0; i < 50; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

        for (double i = 99; i > 50; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

    }

    @Test
    public void addGet() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(1);
        lld1.addFirst(0);
        lld1.addLast(2);
        assertEquals((Integer) 0, lld1.get(0));
        assertEquals((Integer) 1, lld1.get(1));
        assertEquals((Integer) 2, lld1.get(2));

    }

    @Test
    public void addGetMore() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(0);
        lld1.addFirst(1);
        assertEquals(0, (int) lld1.get(1));
        lld1.removeLast();
        lld1.addLast(4);
        lld1.addFirst(5);
        lld1.addLast(6);
        assertEquals(5,(int) lld1.get(0));
        lld1.removeFirst();
        lld1.addLast(9);
        assertEquals(6, (int) lld1.get(2));
        lld1.removeLast();
        lld1.addFirst(12);
        lld1.addLast(13);
        assertEquals(12, (int) lld1.removeFirst());
        lld1.addLast(15);
        lld1.addFirst(16);
        lld1.addLast(17);
        lld1.addLast(18);
        lld1.addLast(19);
        assertEquals(16,(int) lld1.removeFirst());
    }

    @Test
    public void isEmptyTest(){

        Deque<Integer> lld1 = new ArrayDeque<>();
        assertTrue(lld1.isEmpty());
    }

    @Test
    public void basicGetTest() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(0);
        assertEquals(0, (int) lld1.removeLast());
        lld1.addLast(2);
        assertEquals(2, (int) lld1.removeFirst());
    }

    @Test
    public void testEqualItself() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        assertTrue(lld1.equals(lld1));
    }

    @Test
    public void addRemoveEmpty() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addFirst(0);
        assertFalse(lld1.isEmpty());
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addFirst(6);
        lld1.addFirst(7);
        lld1.addFirst(8);
        assertEquals(0,(int) lld1.removeLast());
        lld1.addFirst(10);
    }

}