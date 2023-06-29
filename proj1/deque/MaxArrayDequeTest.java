package deque;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    Comparator<String> byLength = Comparator.comparing(String::length);

    @Test
    @DisplayName("Test for longest string")
    public void stringLengthTest() {

        MaxArrayDeque<String> lld1 = new MaxArrayDeque<>(byLength);
        lld1.addFirst("h");
        lld1.addFirst("he");
        lld1.addFirst("hel");
        lld1.addFirst("hell");
        lld1.addFirst("hello");
        assertEquals("hello",lld1.max());
    }

}