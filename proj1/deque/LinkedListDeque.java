package deque;

/** Linked list implementation of a deque.
 * @author Str Bytes */
public class LinkedListDeque<T> {
    /** Sentinel node provides access to the underlying data structure and
     * acts as the list terminator.
     */
    private final Node sentinel;
    /** Integer representation of the number of elements in the deque. */
    private int size;
    /** Dually-linked list data structure. */
    private class Node {
        /** The value stored in the node. */
        public T val;
        /** A reference to the previous node in the deque. */
        public Node prev;
        /** A reference to the next node in the deque. */
        public Node next;

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
    public void addFirst(T item) {
        Node temp = sentinel.next;
        sentinel.next = new Node(item, sentinel, sentinel.next);
        temp.prev = sentinel.next;
        size += 1;
    }

    /** Add a single item to the end of the deque.
     * @param item Value to add to the deque. */
    public void addLast(T item) {
        Node temp = sentinel.prev;
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        temp.next = sentinel.prev;
        size += 1;
    }

    /** Return the value stored at deque index i.
     * @param i Index of the value to be retrieved. */
    public T get(int i) {
        Node lst = sentinel.next;
        /* Don't evaluate deque items if the index is out of bounds. */
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

    /** Evaluates whether the deque is empty or now.
     * @return Boolean of whether the deque is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Evaluate the number of elements in the deque.
     * @return Integer representation of the number of elements in the deque.
     */
    public int size() {
        return size;
    }

    /** Prints the contents of the deque to the console. */
    public void printDeque() {
        System.out.println(this);
    }

    /** Remove a single item to the front of the deque.
     * @return The value of the deque item being removed. */
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
