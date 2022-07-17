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
            prev = p;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node temp = sentinel.next;
        sentinel.next = new Node(item, sentinel, sentinel.next);
        temp.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        Node temp = sentinel.prev;
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        temp.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node pos = sentinel.next;
        while (pos.next != sentinel) {
            System.out.print(pos.val + " ");
            pos = pos.next;
        }
        System.out.println(pos.val);
    }
}
