package deque;

import java.util.Iterator;

/**
 * Array implementation of a deque.
 * @author Str Bytes
 * @param <T> Type of data to be stored in the deque.
 */
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    /** The array used to implement the deque. */
    private T[] items;
    /** Integer representation of the size of the deque. */
    private int size;
    /** Index of the first item in the list. */
    private int first;
    /** Index of the last item in the list. */
    private int last;

    /** Constructs an empty ArrayDeque. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 3;
        last = 4;
    }

    /** Resize the array used to implement the deque.
     *
     * @param len The desired length of the new array.
     */
    private void resize(int len) {
        T[] a = (T[]) new Object[len];
        System.arraycopy(items, 0, a, 0, last);
        System.arraycopy(items, first + 1, a, len - (size - last), (size - last));
        first = len - (size - last) - 1;
        items = a;
    }

    /** Adds an item to the beginning of the deque.
     * @param item The item to be added to the deque.
     */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[first] = item;
        size += 1;
        first = Math.floorMod((first - 1), items.length);
    }

    /** Adds an item to the end of the deque.
     * @param item The item to be added to the deque.
     */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[last] = item;
        size += 1;
        last = Math.floorMod((last + 1), items.length);
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

    /** Returns a value in the deque by index.
     * @param i The index of the value to be retrieved.
     * @return Returns the value held at index i of the deque.
     */
    @Override
    public T get(int i) {
        assert (i < size);
        return items[(first + 1 + i) % items.length];
    }

    /** Construct an ArrayDeque from a list of arguments.
     * @param <T> The type of items to be contained in the Deque.
     * @param args Sequence of items to be added to the Deque.
     * @return An ArrayDeque with the values args already added.
     * */
    public static <T> ArrayDeque<T> of(T... args) {
        ArrayDeque<T> deque = new ArrayDeque<>();
        for (T arg: args) {
            deque.addLast(arg);
        }
        return deque;
    }

    /** Print a string representation of the deque. */
    @Override
    public void printDeque() {
        System.out.println(this.toString());
    }

    /** Return the size of the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Return a String representation of the deque. */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            str += this.get(i).toString();
            if (i < size - 1) {
                str += " ";
            }
        }
        return str;
    }

    /** Remove the first item from the deque and return its value.
     * @return The value of the item removed from the deque.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else if (size <= items.length * 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        first = Math.floorMod((first + 1), items.length);
        T val = items[first];
        items[first] = null;
        size -= 1;
        return val;
    }

    /** Remove the last item from the deque and return its value.
     * @return The value of the item removed from the deque.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else if (size <= items.length * 0.25 && items.length > 8) {
            resize(items.length / 2);
        }
        last = Math.floorMod((last - 1), items.length);
        T val = items[last];
        items[last] = null;
        size -= 1;
        return val;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    /** Iterator for ArrayDeque. */
    private class ArrayDequeIterator implements Iterator<T> {
        /** Maintains the iterator's position in the Deque. */
        private int pos;

        /** Construct an ArrayDequeIterator. */
        ArrayDequeIterator() {
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
