package deque;

import org.junit.Test;

import static org.junit.Assert.*;

public class DequeEqualsTest {
    @Test
    public void linkedListDequeEqualsTest() {
        LinkedListDeque<Integer> linkedListDeque1 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> linkedListDeque2 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> linkedListDeque3 = LinkedListDeque.of(1, 1, 2);
        LinkedListDeque<String> linkedListDeque4 = LinkedListDeque.of("one", "two", "three");
        LinkedListDeque<String> linkedListDeque5 = LinkedListDeque.of("one", "two", "three");

        assertEquals(linkedListDeque4, linkedListDeque5);
        assertEquals(linkedListDeque1, linkedListDeque2);
        assertNotEquals(linkedListDeque1, linkedListDeque3);
        assertNotEquals(linkedListDeque2, linkedListDeque4);
    }

    @Test
    public void arrayDequeEqualsTest() {
        ArrayDeque<Integer> arrayDeque1 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<Integer> arrayDeque2 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<Integer> arrayDeque3 = ArrayDeque.of(1, 1, 2);
        ArrayDeque<String> arrayDeque4 = ArrayDeque.of("one", "two", "three");
        ArrayDeque<String> arrayDeque5 = ArrayDeque.of("one", "two", "three");

        assertEquals(arrayDeque1, arrayDeque2);
        assertEquals(arrayDeque4, arrayDeque5);
        assertNotEquals(arrayDeque1, arrayDeque3);
        assertNotEquals(arrayDeque2, arrayDeque4);
    }

    @Test
    public void crossTypeTest() {
        LinkedListDeque<Integer> linkedListDeque1 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> linkedListDeque2 = LinkedListDeque.of(1, 1, 2);
        ArrayDeque<Integer> arrayDeque1 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<String> arrayDeque2 = ArrayDeque.of("one", "two", "three");

        assertEquals(linkedListDeque1, arrayDeque1);
        assertNotEquals(linkedListDeque2, arrayDeque1);
        assertNotEquals(linkedListDeque1, arrayDeque2);
    }
}
