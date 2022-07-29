package gh2;

import deque.ArrayDeque;
import deque.Deque;

/** A guitar string synthesizer. */
public class GuitarString {
    /*** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */

    /** Sampling Rate. */
    private static final int SR = 44100;
    /** Energy decay factor. */
    private static final double DECAY = .996;

    /** Buffer for storing sound data. */
    private final Deque<Double> buffer;

    /** Create a guitar string of the given frequency.
     * @param frequency The frequency of the guitar string
     * */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque<Double>();
        for (int i = 0; i < SR / frequency; i++) {
            buffer.addLast(0.0);
        }
    }


    /** Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.addFirst(Math.random() - 0.5);
            buffer.removeLast();
        }
    }

    /** Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        Double first = buffer.removeFirst();
        Double next = buffer.get(0);
        Double newDouble = (first + next) * 0.5 * DECAY;
        buffer.addLast(newDouble);
    }

    /** Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
