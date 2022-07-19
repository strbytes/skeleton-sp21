package deque;

/**
 * Array implementation of a deque.
 * @author Str Bytes
 * @param <T> Type of data to be stored in the deque.
 */
public class ArrayDeque<T> {
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
     *
     * @param item The item to be added to the deque.
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[first] = item;
        size += 1;
        first = Math.floorMod((first - 1), items.length);
    }

    /** Adds an item to the end of the deque.
     *
     * @param item The item to be added to the deque.
     */
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[last] = item;
        size += 1;
        last = Math.floorMod((last + 1), items.length);
    }

    /** Returns a value in the deque by index.
     *
     * @param i The index of the value to be retrieved.
     * @return Returns the value held at index i of the deque.
     */
    public T get(int i) {
        assert (i < size);
        return items[(first + 1 + i) % items.length];
    }

    /** Returns whether the deque is empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Print a string representation of the deque. */
    public void printDeque() {
        System.out.println(this.toString());
    }

    /** Return the size of the deque. */
    public int size() {
        return size;
    }

    /** Return a String representation of the deque. */
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
     *
     * @return The value of the item removed from the deque.
     */
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
     *
     * @return The value of the item removed from the deque.
     */
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
}
