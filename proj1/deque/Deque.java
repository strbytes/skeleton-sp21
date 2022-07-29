package deque;

/**
 * Interface for implementing a Deque.
 * @author Str Bytes
 * @param <T> Type of data to be stored in the deque.
 */
public interface Deque<T> {

    /** Adds an item to the beginning of the deque.
     * @param item The item to be added to the deque.
     */
    void addFirst(T item);

    /** Adds an item to the end of the deque.
     * @param item The item to be added to the deque.
     */
    void addLast(T item);

    /** Evaluates whether the deque is empty or not.
     * @return Boolean of whether the deque is empty or not.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Return the size of the deque. */
    int size();

    /** Print a string representation of the deque. */
    void printDeque();

    /** Remove the first item from the deque and return its value.
     * @return The value of the item removed from the deque.
     */
    T removeFirst();

    /** Remove the last item from the deque and return its value.
     * @return The value of the item removed from the deque.
     */
    T removeLast();

    /** Returns a value in the deque by index.
     * @param index The index of the value to be retrieved.
     * @return Returns the value held at index i of the deque.
     */
    T get(int index);
}
