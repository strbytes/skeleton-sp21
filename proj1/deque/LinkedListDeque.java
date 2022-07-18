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

    public T get(int i) {
        Node lst = sentinel.next;
        while (lst != sentinel && i < size) {   // short-circuit if index is out of bounds
            if (i == 0) return lst.val;
            lst = lst.next;
            i--;
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        System.out.println(this);
    }

    public T removeFirst() {
        if (size == 0) { return null; }
        T item = sentinel.next.val;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    public T removeLast() {
        if (size == 0) { return null; }
        T item = sentinel.prev.val;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    public String toString() {
        Node pos = sentinel.next;
        String str = "";
        while (pos.next != sentinel) {
            str += pos.val + " ";
            pos = pos.next;
        }
        return str + pos.val;
    }
}
