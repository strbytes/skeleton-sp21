package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class DequeEqualsTest {
    @Test
    public void LinkedListDequeEqualsTest() {
        LinkedListDeque<Integer> LinkedListDeque1 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> LinkedListDeque2 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> LinkedListDeque3 = LinkedListDeque.of(1, 1, 2);
        LinkedListDeque<String> LinkedListDeque4 = LinkedListDeque.of("one", "two", "three");

        assertEquals(LinkedListDeque1, LinkedListDeque2);
        assertNotEquals(LinkedListDeque1, LinkedListDeque3);
        assertNotEquals(LinkedListDeque2, LinkedListDeque4);
    }

    @Test
    public void ArrayDequeEqualsTest() {
        ArrayDeque<Integer> ArrayDeque1 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<Integer> ArrayDeque2 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<Integer> ArrayDeque3 = ArrayDeque.of(1, 1, 2);
        ArrayDeque<String> ArrayDeque4 = ArrayDeque.of("one", "two", "three");

        assertEquals(ArrayDeque1, ArrayDeque2);
        assertNotEquals(ArrayDeque1, ArrayDeque3);
        assertNotEquals(ArrayDeque2, ArrayDeque4);
    }

    @Test
    public void CrossTypeTest() {
        LinkedListDeque<Integer> LinkedListDeque1 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> LinkedListDeque2 = LinkedListDeque.of(1, 1, 2);
        ArrayDeque<Integer> ArrayDeque1 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<String> ArrayDeque2 = ArrayDeque.of("one", "two", "three");

        assertEquals(LinkedListDeque1, ArrayDeque1);
        assertNotEquals(LinkedListDeque2, ArrayDeque1);
        assertNotEquals(LinkedListDeque1, ArrayDeque2);
    }
}
