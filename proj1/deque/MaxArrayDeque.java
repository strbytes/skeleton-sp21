package deque;

import java.util.Comparator;

/**
 * Array implementation of a deque that takes a required Comparator so it can
 * determine the max item in the deque at any time with a call to max().
 * @author Str Bytes
 * @param <T> Type of data to be stored in the deque.
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    /** Used to determine the natural ordering of the Deque. */
    private Comparator<T> comp;

    /** Create a new MaxArrayDeque with a supplied Comparator.
     * @param c Comparator used to determine the natural ordering of the Deque.
     */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

    /** Returns the maximum value in the Deque according to the Comparator it
     * was constructed with.
     */
    public T max() {
        T largest = null;
        for (int i = 0; i < size(); i++) {
            T test = get(i);
            if (test != null) {
                if (largest == null || comp.compare(test, largest) > 0) {
                    largest = get(i);
                }
            }
        }
        return largest;
    }

    /** Returns the maximum value in the Deque according to the Comparator c.
     * @param c Determines the ordering used.
     */
    public T max(Comparator<T> c) {
        T largest = null;
        for (int i = 0; i < size(); i++) {
            T test = get(i);
            if (test != null) {
                if (largest == null || c.compare(test, largest) > 0) {
                    largest = get(i);
                }
            }
        }
        return largest;
    }

    /** Construct an MaxArrayDeque from a list of arguments.
     * @param c The comparator used to determine the natural order of the Deque.
     * @param <T> The type of items to be contained in the Deque.
     * @param args Sequence of items to be added to the Deque.
     * @return An MaxArrayDeque with the values args already added.
     * */
    public static <T> MaxArrayDeque<T> of(Comparator<T> c, T... args) {
        MaxArrayDeque<T> deque = new MaxArrayDeque<>(c);
        for (T arg: args) {
            deque.addLast(arg);
        }
        return deque;
    }
}
