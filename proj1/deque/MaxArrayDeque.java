package deque;

import net.sf.saxon.expr.Component;

import java.util.Comparator;

/**
 * Array implementation of a deque that takes a required Comparator so it can
 * determine the max item in the deque at any time with a call to max().
 * @author Str Bytes
 * @param <T> Type of data to be stored in the deque.
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

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
}
