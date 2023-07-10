package deque;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    Comparator<String> byLength = Comparator.comparing(String::length);
    Comparator<Integer> byValue = Comparator.comparing(Integer::valueOf);


    @Test
    @DisplayName("Test for longest string")
    public void stringLengthTest() {

        MaxArrayDeque<String> lld1 = new MaxArrayDeque<>(byLength);
        lld1.addFirst("hello");
        lld1.removeFirst();
        lld1.addFirst("h");
        lld1.addFirst("he");
        lld1.addFirst("hel");
        lld1.addFirst("hell");
        lld1.addFirst("hello");
        lld1.removeFirst();
        assertEquals("hell",lld1.max());
    }

    @Test
    @DisplayName("Test for highest value")
    public void valueTest() {

        MaxArrayDeque<Integer> lld1 = new MaxArrayDeque<>(byValue);
        lld1.addFirst(5);
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        lld1.addFirst(4);
        lld1.addFirst(5);
        lld1.addFirst(5);
        assertEquals(5,(int) lld1.max());
    }

}