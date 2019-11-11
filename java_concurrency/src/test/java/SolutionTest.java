import org.junit.Test;

import java.util.List;

import static org.testng.Assert.*;

public class SolutionTest {
    private List singleThread = Solution.singleThreadSolution();
    private List concurrent = Solution.concurrentSolution();

    @Test
    public void test1() {
        assertTrue((Boolean) singleThread.get(2));
    }

    @Test
    public void test2() {
        assertTrue((Boolean) singleThread.get(192));
    }

    @Test
    public void test3() {
        assertTrue((Boolean) singleThread.get(100));
    }

    @Test
    public void test4() {
        assertTrue((Boolean) singleThread.get(70));
    }

    @Test
    public void test5() {
        assertTrue((Boolean) singleThread.get(198));
    }

    @Test
    public void test6() {
        assertTrue((Boolean) concurrent.get(2));
    }

    @Test
    public void test7() {
        assertTrue((Boolean) concurrent.get(192));
    }

    @Test
    public void test8() {
        assertTrue((Boolean) concurrent.get(100));
    }

    @Test
    public void test9() {
        assertTrue((Boolean) concurrent.get(70));
    }

    @Test
    public void test10() {
        assertTrue((Boolean) concurrent.get(198));
    }

    @Test
    public void test11() {
        assertEquals(singleThread.size(), 10_000_000);
    }

    @Test
    public void test12() {
        assertEquals(concurrent.size(), 10_000_000);
    }

    @Test
    public void test13() {
        assertFalse((Boolean) singleThread.get(99));
    }

    @Test
    public void test14() {
        assertFalse((Boolean) concurrent.get(99));
    }
}
