package deque;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class TestMaxArrayDeque {

    @Test
    public void TestMax() {
        Comparator<Integer> compare = new CompareInts<>();
        MaxArrayDeque<Integer> test = MaxArrayDeque.of(compare, 0, 1, 2, 3, 4);
        assertEquals(5, test.size());
        assertEquals(4, test.max(), 0);
        Comparator<Integer> compare2 = new CompareIntsReversed<>();
        assertEquals(0, test.max(compare2), 0);
    }
}
