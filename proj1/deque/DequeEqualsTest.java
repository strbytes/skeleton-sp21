package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class DequeEqualsTest {
    @Test
    public void LLDEqualsTest() {
        LinkedListDeque<Integer> LLD1 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> LLD2 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> LLD3 = LinkedListDeque.of(1, 1, 2);
        LinkedListDeque<String> LLD4 = LinkedListDeque.of("one", "two", "three");

        assertEquals(LLD1, LLD2);
        assertNotEquals(LLD1, LLD3);
        assertNotEquals(LLD2, LLD4);
    }

    @Test
    public void ADEqualsTest() {
        ArrayDeque<Integer> AD1 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<Integer> AD2 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<Integer> AD3 = ArrayDeque.of(1, 1, 2);
        ArrayDeque<String> AD4 = ArrayDeque.of("one", "two", "three");

        assertEquals(AD1, AD2);
        assertNotEquals(AD1, AD3);
        assertNotEquals(AD2, AD4);
    }

    @Test
    public void CrossTypeTest() {
        LinkedListDeque<Integer> LLD1 = LinkedListDeque.of(0, 1, 2);
        LinkedListDeque<Integer> LLD2 = LinkedListDeque.of(1, 1, 2);
        ArrayDeque<Integer> AD1 = ArrayDeque.of(0, 1, 2);
        ArrayDeque<String> AD2 = ArrayDeque.of("one", "two", "three");

        assertEquals(LLD1, AD1);
        assertNotEquals(LLD2, AD1);
        assertNotEquals(LLD1, AD2);
    }
}
