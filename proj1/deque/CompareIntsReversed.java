package deque;

import java.util.Comparator;

public class CompareIntsReversed<Integer> implements Comparator<Integer> {
    public int compare(Integer a, Integer b) {
        return (int) b - (int) a;
    }
}
