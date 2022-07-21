package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    @Test
  public void testThreeAddThreeRemove() {
      AListNoResizing<Integer> noResize = new AListNoResizing<>();
      BuggyAList<Integer> buggy = new BuggyAList<>();
      for (int i = 0; i < 3; i++) {
          noResize.addLast(i);
          buggy.addLast(i);
      }
      for (int i = 0; i < 3; i++) {
          assertEquals(noResize.removeLast(), buggy.removeLast());
      }
  }

  @Test
  public void randomizedTest() {
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> B = new BuggyAList<>();

      int N = 500;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 4);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              B.addLast(randVal);
          } else if (operationNumber == 1) {
              // size
              int size = L.size();
              assertEquals(L.size(), B.size());
          } else if (operationNumber == 2) {
              // getLast
              if (L.size() > 0) {
                  assertEquals(L.getLast(), B.getLast());
              }
          } else if (operationNumber == 3) {
              // removeLast
              if (L.size() > 0) {
                  assertEquals(L.removeLast(), B.removeLast());
              }
          }
      }
  }
}
