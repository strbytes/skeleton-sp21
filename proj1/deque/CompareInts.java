package deque;

import java.util.Comparator;

public class CompareInts<Integer> implements Comparator<Integer> {
    public int compare(Integer a, Integer b) {
        return (int) a - (int) b;
    }
}
