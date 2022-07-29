package deque;

public interface Deque<T> {
    void addFirst(T item);

    void addLast(T item);

    /** Evaluates whether the deque is empty or not.
     * @return Boolean of whether the deque is empty or not.
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    int size();

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);
}
