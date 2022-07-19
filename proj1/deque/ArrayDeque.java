package deque;

/**
 * Array implementation of a deque.
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

    /** Constructs an empty ArrayDeque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        first = 3;
        last = 4;
    }

    private void resize(int len) {
        T[] a = (T[]) new Object[len];
        System.arraycopy(items, 0, a, 0, last);
        System.arraycopy(items, first + 1, a, len - (size - last), (size - last));
        first = len - (size - last) - 1;
        items = a;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[first] = item;
        size += 1;
        first = Math.floorMod((first - 1),items.length);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[last] = item;
        size += 1;
        last = Math.floorMod((last + 1), items.length);
    }

    public T get(int i) {
        assert (i < size);
        return items[(first + 1 + i) % items.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        System.out.println(this.toString());
    }

    public int size() {
        return size;
    }

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
}