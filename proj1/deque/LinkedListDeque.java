package deque;

/** Linked list implementation of a deque. */
public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;
    /** Dually-linked list data structure */
    private class Node {
        public T val;
        public Node next;
        public Node prev;

        public Node(T item, Node p, Node n) {
            val = item;
            prev = n;
            next = n;
        }
    }
}
