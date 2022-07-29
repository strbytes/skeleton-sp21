package deque;

import org.junit.Test;
import static org.junit.Assert.*;

/** Basic tests for the ArrayDeque class. */
public class ArrayDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {
        ArrayDeque<String> arrayD = new ArrayDeque<String>();

		assertTrue("A newly initialized ArrayDeque should be empty", arrayD.isEmpty());
		arrayD.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, arrayD.size());
        assertFalse("arrayD should now contain 1 item", arrayD.isEmpty());

		arrayD.addLast("middle");
		assertEquals(2, arrayD.size());

		arrayD.addLast("back");
		assertEquals(3, arrayD.size());

		System.out.println("Printing out deque: ");
		arrayD.printDeque();
        assertEquals("front middle back", arrayD.toString());
    }

	@Test
	public void addRolloverTest() {
		ArrayDeque<Integer> array1 = new ArrayDeque<>();
		for (int i = 1; i < 7; i++)  {
			array1.addLast(i);
		}
		assertEquals("1 2 3 4 5 6", array1.toString());

		ArrayDeque<Integer> array2 = new ArrayDeque<>();
		for (int i = 1; i < 7; i++) {
			array2.addFirst(i);
		}
		assertEquals("6 5 4 3 2 1", array2.toString());
	}

	@Test
	public void addResizeTest() {
		ArrayDeque<Integer> array1 = new ArrayDeque<>();
		for (int i = 0; i < 32; i++) {
			array1.addFirst(i);
		}
		assertEquals(0, array1.get(31), 0);
		ArrayDeque<Integer> array2 = new ArrayDeque<>();
		for (int i = 0; i < 32; i++) {
			array2.addLast(i);
		}
		assertEquals(31, array2.get(31), 0);
	}


	@Test
	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	public void addRemoveTest() {
		ArrayDeque<Integer> arrayD = new ArrayDeque<Integer>();
		// should be empty
		assertTrue("arrayD should be empty upon initialization", arrayD.isEmpty());

		arrayD.addFirst(10);
		// should not be empty
		assertFalse("arrayD should contain 1 item", arrayD.isEmpty());

		assertEquals(10,  arrayD.removeFirst(), 0);
		// should be empty
		assertTrue("arrayD should be empty after removal", arrayD.isEmpty());

		arrayD.addLast(10);
		// should not be empty
		assertFalse("arrayD should contain 1 item", arrayD.isEmpty());

		assertEquals(10, arrayD.removeLast(), 0);
		// should be empty
		assertTrue("arrayD should be empty after removal", arrayD.isEmpty());
	}

	@Test
	public void addRemoveResizeTest() {
		ArrayDeque<Integer> arrayD = new ArrayDeque<Integer>();
		for (int i = 0; i < 32; i++) {
			arrayD.addLast(i);
		}
		for (int i = 0; i < 32; i++) {
			arrayD.removeLast();
		}
		assertTrue(arrayD.isEmpty());

		for (int i = 0; i < 32; i++) {
			arrayD.addFirst(i);
		}
		for (int i = 0; i < 32; i++) {
			arrayD.removeFirst();
		}
		assertTrue(arrayD.isEmpty());
	}

	@Test
	public void removeEmptyTest() {
		ArrayDeque<Integer> arrayD = new ArrayDeque<Integer>();
		assertEquals(null, arrayD.removeFirst());
		assertEquals(0, arrayD.size());
		assertEquals(null, arrayD.removeLast());
		assertEquals(0, arrayD.size());
	}

	@Test
	public void largeResizeTest() {
		ArrayDeque<Integer> arrayD = new ArrayDeque<Integer>();
		for (int i = 0; i < 1048576; i++) {
			arrayD.addLast(i);
		}
		assertEquals(1048575, arrayD.get(arrayD.size() - 1), 0);
		assertEquals(1048576, arrayD.size());

		for (int i = 0; i < 1048576; i++) {
			arrayD.removeLast();
		}
		assertEquals(0, arrayD.size());
	}

	@Test
	public void getTest() {
		ArrayDeque<Integer> test = new ArrayDeque<>();
		test.addLast(0);
		test.addLast(1);
		test.addLast(2);
		test.addLast(3);
		test.addLast(4);
		for (int i = 0; i < test.size(); i++) {
			assertEquals(i, test.get(i), 0);
		}
	}

	@Test
	public void ofTest() {
		ArrayDeque<Integer> test = ArrayDeque.of(0, 1, 2, 3, 4);
		assertEquals(5, test.size());
		for (int i = 0; i < test.size(); i++) {
			assertEquals(i, test.get(i), 0);
		}
	}

	@Test
	public void iteratorTest() {
		ArrayDeque<Integer> test = ArrayDeque.of(0, 1, 2, 3, 4);
		int i = 0;
		for (Integer t: test) {
			assert t == i;
			i++;
		}
	}
}
