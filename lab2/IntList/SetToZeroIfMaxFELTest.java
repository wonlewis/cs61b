package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SetToZeroIfMaxFELTest {

    @Test
    public void testZeroOutFELMaxes1() {
        IntList L = IntList.of(1, 22, 15);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 15", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes2() {
        IntList L = IntList.of(55, 22, 45, 44, 5);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 22 -> 45 -> 0 -> 0", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes3() {
        IntList L = IntList.of(5, 535, 35, 11, 10, 0);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 35 -> 0 -> 10 -> 0", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes4() {
        IntList L = IntList.of();
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals(null, L);
    }

    @Test
    public void testZeroOutFELMaxes5() {
        IntList L = IntList.of(0, 0, 0, 0, 0, 0);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 0 -> 0 -> 0 -> 0", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes6() {
        IntList L = IntList.of(44, 44, 44, 44, 44, 44);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 0 -> 0 -> 0 -> 0", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes7() {
        IntList L = IntList.of(44, 33, 22, 11, 22, 33);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 0 -> 0 -> 0 -> 0", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes8() {
        IntList L = IntList.of(0, 11, 22, 33, 44, 55);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 0 -> 0 -> 0 -> 0", L.toString());
    }

    @Test
    public void testZeroOutFELMaxes9() {
        IntList L = IntList.of(0, 1424122411, 22, 33, 44, 55);
        IntListExercises.setToZeroIfMaxFEL(L);
        assertEquals("0 -> 0 -> 0 -> 0 -> 0 -> 0", L.toString());
    }
}
