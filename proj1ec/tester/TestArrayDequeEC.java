package tester;

import static org.junit.Assert.*;
import org.junit.Test;
import student.StudentArrayDeque;
import edu.princeton.cs.introcs.StdRandom;

public class TestArrayDequeEC {
    @Test
    public void testStudentArrayDeque() {
        int[] testSizes = {5, 50, 500, 5000};
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> control = new ArrayDequeSolution<>();
        ArrayDequeSolution<String> sequence = new ArrayDequeSolution<>();
        for (int testSize: testSizes) {
            for (int i = 0; i < testSize; i++) {
                int choice = StdRandom.uniform(6);
                int randNum = StdRandom.uniform(testSize);
                if (choice == 0) {
                    assertEquals(lastThreeOperations(sequence),
                            control.size(), test.size());
                    sequence.addLast("size()");
                } else if (choice == 1) {
                    test.addFirst(randNum);
                    control.addFirst(randNum);
                    sequence.addLast("addFirst(" + randNum + ")");
                } else if (choice == 2) {
                    test.addLast(randNum);
                    control.addLast(randNum);
                    sequence.addLast("addLast(" + randNum + ")");
                } else if (choice == 3 && control.size() > 0) {
                    Integer testVal = test.removeFirst();
                    Integer controlVal = control.removeFirst();
                    sequence.addLast("removeFirst()");
                    assertNotNull(lastThreeOperations(sequence), testVal);
                    assertEquals(lastThreeOperations(sequence),
                            controlVal, testVal, 0.0);
                } else if (choice == 4 && control.size() > 0) {
                    Integer testVal = test.removeLast();
                    Integer controlVal = control.removeLast();
                    sequence.addLast("removeLast()");
                    assertNotNull(lastThreeOperations(sequence), testVal);
                    assertEquals(lastThreeOperations(sequence),
                            controlVal, testVal, 0.0);
                } else if (choice == 5 && control.size() > 0) {
                    int index = (int) (Math.round(Math.random() * (control.size() - 1)));
                    Integer testVal = test.get(index);
                    Integer controlVal = control.get(index);
                    sequence.addLast("get(" + index + ")");
                    assertNotNull(lastThreeOperations(sequence), testVal);
                    assertEquals(lastThreeOperations(sequence),
                            controlVal, testVal, 0.0);
                }
            }
        }
    }

    private String lastThreeOperations(ArrayDequeSolution<String> sequence) {
        StringBuilder returnString = new StringBuilder();
        for (int i = 3; i > 1; i--) {
            if (sequence.size() - i >= 0) {
                returnString.append(sequence.get(sequence.size() - i));
                returnString.append("\n");
            }
        }
        if (sequence.size() > 0) {
            returnString.append(sequence.get(sequence.size() - 1));
        }
        return returnString.toString();
    }

    @Test
    public void testLastThree() {
        ArrayDequeSolution<String> a = new ArrayDequeSolution<>();
        assertEquals("String did not match expected value", "", lastThreeOperations(a));
        a.addLast("one");
        assertEquals("String did not match expected value", "one", lastThreeOperations(a));
        a.addLast("two");
        assertEquals("String did not match expected value", "one\ntwo", lastThreeOperations(a));
        a.addLast("three");
        assertEquals("String did not match expected value", "one\ntwo\nthree", lastThreeOperations(a));
        a.addLast("four");
        assertEquals("String did not match expected value", "two\nthree\nfour", lastThreeOperations(a));
        a.addLast("five");
        assertEquals("String did not match expected value", "three\nfour\nfive", lastThreeOperations(a));
    }
}
