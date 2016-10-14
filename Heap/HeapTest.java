import java.util.ArrayList;
import java.util.Random;

/**
 * The test class HeapTest.
 *
 * @author  Todd O'Bryan
 * @version January 2006
 */
public class HeapTest extends junit.framework.TestCase {
    private Heap<Integer> h;

    protected void setUp() {
        h = new Heap<Integer>();
    }

    public void testEmptyHeap() {
        assertEquals("[]", h.toString());
    }
    
    public void testInsertSingleElement() {
        h.add(1);
        assertEquals("[1]", h.toString());
    }
    
    public void testInsertSeveralNoPercUp() {
        h.add(1);
        h.add(2);
        h.add(3);
        h.add(4);
        h.add(5);
        assertEquals("[1, 2, 3, 4, 5]", h.toString());
    }
    
    public void testInsertOnePercUp() {
        h.add(2);
        h.add(1);
        assertEquals("[1, 2]", h.toString());
    }
    
    public void testPercAllTheWay() {
        for (int i = 1; i <= 10; i++) {
            h.add(i);
        }
        h.add(0);
        assertEquals("[0, 1, 3, 4, 2, 6, 7, 8, 9, 10, 5]", h.toString());
    }
    
    public void testPercPartWay() {
        for (int i = 0; i <= 18; i += 2) {
            h.add(i);
        }
        h.add(1);
        assertEquals("[0, 1, 4, 6, 2, 10, 12, 14, 16, 18, 8]", h.toString());
    }
    
    public void testPercDownSingleElement() {
        h.add(1);
        h.add(2);
        h.remove();
        assertEquals("[2]", h.toString());
    }
    
    public void testPercDownTwoElements() {
        h.add(1);
        h.add(2);
        h.add(3);
        h.remove();
        assertEquals("[2, 3]", h.toString());
    }
    
    public void testPercRight() {
        h.add(3);
        h.add(7);
        h.add(5);
        h.add(10);
        h.remove();
        assertEquals("[5, 7, 10]", h.toString());
    }
    
    public void testPercAllTheWayLeft() {
        for (int i = 1; i <= 17; i++) {
            h.add(i);
        }
        h.remove();
        assertEquals("[2, 4, 3, 8, 5, 6, 7, 16, 9, 10, 11, 12, 13, 14, 15, 17]", 
            h.toString());
    }
    
    public void testPercAllTheWayRight() {
        h.add(1);
        h.add(3);
        h.add(2);
        h.add(7);
        h.add(6);
        h.add(5);
        h.add(4);
        for (int i=15; i>=8; i--) {
            h.add(i);
        }
        h.add(16);
        h.remove();
        assertEquals("[2, 3, 4, 7, 6, 5, 8, 15, 14, 13, 12, 11, 10, 9, 16]",
            h.toString());
    }
    
    public void testPercBackAndForth() {
        h.add(1);
        h.add(2);
        h.add(3);
        h.add(5);
        h.add(4);
        h.add(10);
        h.add(11);
        h.add(8);
        h.add(9);
        h.add(6);
        h.add(10);
        h.add(50);
        h.remove();
        assertEquals("[2, 4, 3, 5, 6, 10, 11, 8, 9, 50, 10]", h.toString());
    }
    
    public void testHeapSort10() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            h.add(rand.nextInt(100));
        }
        Integer lastMin = h.remove();
        Integer thisMin;
        for (int i = 0; i < 9; i++) {
            thisMin = h.remove();
            assertTrue(lastMin.compareTo(thisMin) <= 0);
            lastMin = thisMin;
        }
    }
    
    public void testHeapSort100() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            h.add(rand.nextInt(1000));
        }
        Integer lastMin = h.remove();
        Integer thisMin;
        for (int i = 0; i < 99; i++) {
            thisMin = h.remove();
            assertTrue(lastMin.compareTo(thisMin) <= 0);
            lastMin = thisMin;
        }
    }
    
    public void testHeapSort1000() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            h.add(rand.nextInt(1000));
        }
        Integer lastMin = h.remove();
        Integer thisMin;
        for (int i = 0; i < 99; i++) {
            thisMin = h.remove();
            assertTrue(lastMin.compareTo(thisMin) <= 0);
            lastMin = thisMin;
        }
    }   
}
