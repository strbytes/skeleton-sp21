package deque;

import java.util.Iterator;

/** Linked list implementation of a deque.
 * @author Str Bytes */
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    /** Sentinel node provides access to the underlying data structure and
     * acts as the list terminator.
     */
    private final Node sentinel;
    /** Integer representation of the number of elements in the deque. */
    private int size;
    /** Dually-linked list data structure. */
    private class Node {
        /** The value stored in the node. */
        private T val;
        /** A reference to the previous node in the deque. */
        private Node prev;
        /** A reference to the next node in the deque. */
        private Node next;

        /** Construct a single dually-linked list node.
         * @param item The value stored in the node.
         * @param p A reference to the previous node in the deque.
         * @param n A reference to the next node in the deque.
         */
        Node(T item, Node p, Node n) {
            val = item;
            prev = p;
            next = n;
        }
    }

    /** Constructs an empty deque. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Add a single item to the front of the deque.
     * @param item Value to add to the deque. */
    @Override
    public void addFirst(T item) {
        Node temp = sentinel.next;
        sentinel.next = new Node(item, sentinel, sentinel.next);
        temp.prev = sentinel.next;
        size += 1;
    }

    /** Add a single item to the end of the deque.
     * @param item Value to add to the deque. */
    @Override
    public void addLast(T item) {
        Node temp = sentinel.prev;
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        temp.next = sentinel.prev;
        size += 1;
    }

    /** Return whether two Deques have equal values. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Deque) || ((Deque<?>) o).size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!((Deque<?>) o).get(i).equals(get(i))) {
                return false;
            }
        }
        return true;
    }

    /** Return the value stored at deque index i.
     * @param i Index of the value to be retrieved. */
    @Override
    public T get(int i) {
        Node lst = sentinel.next;
        while (lst != sentinel && i < size) {
            if (i == 0) {
                return lst.val;
            }
            lst = lst.next;
            i--;
        }
        return null;
    }

    /** Private recursive implementation of get.
     * @param i Index of node to access, relative to current position.
     * @param lst The current node in the list.
     * @param stnl The sentinel node, used as list terminus.
     * @return The value of the node at index 0.
     */
    private T getRecursive(int i, Node lst, Node stnl) {
        if (lst == stnl) {
            return null;
        } else if (i == 0) {
            return lst.val;
        } else {
            return getRecursive(i - 1, lst.next, stnl);
        }
    }

    /** Public access to the recursive implementation of get.
     * @param i Index of node value to be accessed.
     * @return The value of the node at index i. Null if i is out of bounds.
     */
    public T getRecursive(int i) {
        if (i >= size) {
            return null;
        }
        return getRecursive(i, sentinel.next, sentinel);
    }

    /** Construct an LinkedListDeque from a list of arguments.
     * @param <T> The type of items to be contained in the Deque.
     * @param args Sequence of items to be added to the Deque.
     * @return An LinkedListDeque with the values args already added.
     * */
    public static <T> LinkedListDeque<T> of(T... args) {
        LinkedListDeque<T> deque = new LinkedListDeque<>();
        for (T arg: args) {
            deque.addLast(arg);
        }
        return deque;
    }

    /** Evaluate the number of elements in the deque.
     * @return Integer representation of the number of elements in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /** Prints the contents of the deque to the console. */
    @Override
    public void printDeque() {
        System.out.println(this);
    }

    /** Remove a single item to the front of the deque.
     * @return The value of the deque item being removed. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.next.val;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    /** Remove a single item to the end of the deque.
     * @return The value of the deque item being removed. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.prev.val;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    /** Constructs a string representation of the values in the deque.
     * @return A string representation of the values in the deque.
     */
    @Override
    public String toString() {
        Node pos = sentinel.next;
        String str = "";
        while (pos.next != sentinel) {
            str += pos.val + " ";
            pos = pos.next;
        }
        return str + pos.val;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    /** Iterator for LinkedListDeque. */
    private class LinkedListDequeIterator implements Iterator<T> {
        /** Maintains the iterator's position in the Deque. */
        private int pos;

        /** Construct an LinkedListDequeIterator. */
        LinkedListDequeIterator() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size();
        }

        @Override
        public T next() {
            T val = get(pos);
            pos += 1;
            return val;
        }
    }
}
