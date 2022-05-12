import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * These tests are not exhaustive.
 * @author CS 1332 TAs
 * @version 1.0
 */
public class AVLStudentTest2 {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotation() {
        /*
                    5                   4
                   /                   / \
                  4         ->        3   5
                 /
                3
         */
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightLeftRotationRoot() {
        /*
                3               4
                 \             / \
                  5     ->    3   5
                 /
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }


    @Test(timeout = TIMEOUT)
    public void testRemove() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       526   856
                 / \                     /
               386 526                 386
         */
        Integer toBeRemoved = new Integer(477);
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);

        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        Integer maximum = new Integer(646);
        avlTree.add(477);
        avlTree.add(526);
        avlTree.add(386);
        avlTree.add(maximum);

        assertSame(maximum, avlTree.get(new Integer(646)));
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        avlTree.add(new Integer(477));
        avlTree.add(new Integer(526));
        avlTree.add(new Integer(386));
        avlTree.add(new Integer(646));

        assertEquals(true, avlTree.contains(new Integer(477)));
        assertEquals(true, avlTree.contains(new Integer(386)));
        assertEquals(true, avlTree.contains(new Integer(646)));
        assertEquals(false, avlTree.contains(new Integer(387)));
        assertEquals(false, avlTree.contains(new Integer(700)));
        assertEquals(false, avlTree.contains(new Integer(500)));
    }



    @Test(timeout = TIMEOUT)
    public void testHeight() {
        /*
                     646
                    /   \
                  477   856
                  / \
                386 526
         */
        avlTree.add(646);
        avlTree.add(386);
        avlTree.add(856);
        avlTree.add(526);
        avlTree.add(477);

        assertEquals(2, avlTree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testPredecessor() {
        /*
                76
              /    \
            34      90
             \      /
              40  81
         */

        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);
        avlTree.add(40);
        avlTree.add(81);

        assertEquals((Integer) 40, avlTree.predecessor(76));
        assertEquals((Integer) 34, avlTree.predecessor(40));
        assertEquals((Integer) 76, avlTree.predecessor(81));
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallest() {
        /*
                    76
                 /      \
               34        90
              /  \      /
            20    40  81
         */

        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);
        avlTree.add(20);
        avlTree.add(40);
        avlTree.add(81);

        List<Integer> smallest = new ArrayList<>();
        smallest.add(20);
        smallest.add(34);
        smallest.add(40);

        // Should be [20, 34, 40]
        assertEquals(smallest, avlTree.kSmallest(3));

        smallest.add(76);

        // Should be [20, 34, 40, 76]
        assertEquals(smallest, avlTree.kSmallest(4));

        smallest.add(81);
        smallest.add(90);

        // Should be [20, 34, 40, 81, 90]
        assertEquals(smallest, avlTree.kSmallest(6));
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorAndClear() {
        /*
                     7
                    / \
                   1   24
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(7);
        toAdd.add(24);
        toAdd.add(1);
        avlTree = new AVL<>(toAdd);

        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }

    // tests by suzan manasreh

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNull() {
        avlTree.add(1);
        avlTree.add(0);
        avlTree.add(null);
    }

    // test to verify that adding an element to AVL that already exists does nothing
    @Test(timeout = TIMEOUT)
    public void addExistingElement() {
        /*
              5
             / \
            1   9
        */
        avlTree.add(5);
        avlTree.add(9);
        avlTree.add(1);

        assertEquals(3, avlTree.size());

        assertEquals((Integer) 5, avlTree.getRoot().getData());
        assertEquals((Integer) 1, avlTree.getRoot().getLeft().getData());
        assertEquals((Integer) 9, avlTree.getRoot().getRight().getData());

        avlTree.add(1);
        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 5, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotation() {
        /*
                  3                 4
                   \               / \
                    4       ->    3   5
                     \
                      5
         */
        avlTree.add(3);
        avlTree.add(4);
        avlTree.add(5);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRightRotationRoot() {
        /*
                5               4
               /               / \
              3       ->      3   5
               \
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);

        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftRotationWithSubtrees() {
        /*
                     6                     6
                   /   \                 /   \
                  4     9      ->       4     9
                 / \   /               / \   /
                1   5 8               2   5 8
                 \                   / \
                  2                 1   3
                   \
                    3
         */
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(9);
        avlTree.add(1);
        avlTree.add(5);
        avlTree.add(8);
        avlTree.add(2);
        avlTree.add(3);

        assertEquals(8, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 6, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 4, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 2, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 8, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRightRotationWithSubtrees() {
        /*
                     6                     4
                   /   \                 /   \
                  4     9      ->       1     6
                 / \                   /     / \
                1   5                 0     5   9
               /
              0


         */
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(9);
        avlTree.add(1);
        avlTree.add(5);
        avlTree.add(0);

        assertEquals(6, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 6, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 0, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        avlTree.remove(null);
    }

    // test removing data that is not in the tree
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveElementNotInBST() {
        avlTree.add(6);
        avlTree.add(9);
        avlTree.add(3);
        avlTree.remove(2);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithRotation() {
        // there's a right and right-left rotation
        /*
                     6                     10
                   /   \                 /    \
                  4     12      ->      6     12
                 / \   /  \            / \    / \
                1   5 10  13          1   9  11 13
               /     /  \    \       / \  /      \
              0     9   11    14    0   4 8       14
                   /
                  8
         */
        Integer toBeRemoved = 5;
        avlTree.add(6);
        avlTree.add(4);
        avlTree.add(12);
        avlTree.add(1);
        avlTree.add(toBeRemoved);
        avlTree.add(10);
        avlTree.add(13);
        avlTree.add(0);
        avlTree.add(9);
        avlTree.add(11);
        avlTree.add(14);
        avlTree.add(8);

        assertSame(toBeRemoved, avlTree.remove(5));

        assertEquals(11, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 10, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 6, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 12, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());
        assertEquals((Integer) 1, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 9, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 11, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
        assertEquals((Integer) 13, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());
        assertEquals((Integer) 0, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());
        assertEquals((Integer) 4, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 8, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());
        assertEquals((Integer) 14, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullData() {
        avlTree.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetDataNotInAVL() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        avlTree.get(18);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullData() {
        avlTree.contains(null);
    }

    @Test(timeout = TIMEOUT)
    public void testContainsDataNotInTree() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        assertFalse(avlTree.contains(18));
        assertFalse(avlTree.contains(14));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPredecessorNullData() {
        avlTree.predecessor(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testPredecessorDataNotInTree() {
        avlTree.add(22);
        avlTree.add(15);
        avlTree.add(17);
        avlTree.add(34);
        avlTree.predecessor(18);
        avlTree.predecessor(14);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testKSmallestException() {
        avlTree.add(76);
        avlTree.add(34);
        avlTree.add(90);

        avlTree.kSmallest(-1);
        avlTree.kSmallest(3);
    }

    @Test(timeout = TIMEOUT)
    public void testHeightEmptyTree() {
        assertEquals(-1, avlTree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testKSmallestEmptyTree() {
        assertEquals(0, avlTree.kSmallest(0).size());
    }
    @Test(timeout = TIMEOUT)
    public void testKSmallestTreeTestA() {
        /*
         30
        /  \
      20    40
     /        \
   10          50
         */
        avlTree.add(30);
        avlTree.add(20);
        avlTree.add(40);
        avlTree.add(10);
        avlTree.add(50);
        assertEquals((Integer) 30, avlTree.getRoot().getData());
        List<Integer> smallest = new ArrayList<>();
        smallest.add(10);
        assertEquals(smallest, avlTree.kSmallest(1));
        smallest.add(20);
        assertEquals(smallest, avlTree.kSmallest(2));
        smallest.add(30);
        assertEquals(smallest, avlTree.kSmallest(3));
        smallest.add(40);
        assertEquals(smallest, avlTree.kSmallest(4));
        smallest.add(50);
        assertEquals(smallest, avlTree.kSmallest(5));
        avlTree.clear();
        smallest.clear();
    }
    @Test(timeout = TIMEOUT)
    public void testKSmallestTreeTestB() {
        /*
        30
      /    \
    10      50
      \    /
      20  40
         */
        avlTree.add(30);
        avlTree.add(10);
        avlTree.add(50);
        avlTree.add(20);
        avlTree.add(40);
        assertEquals((Integer) 30, avlTree.getRoot().getData());
        List<Integer> smallest = new ArrayList<>();
        smallest.add(10);
        assertEquals(smallest, avlTree.kSmallest(1));
        smallest.add(20);
        assertEquals(smallest, avlTree.kSmallest(2));
        smallest.add(30);
        assertEquals(smallest, avlTree.kSmallest(3));
        smallest.add(40);
        assertEquals(smallest, avlTree.kSmallest(4));
        smallest.add(50);
        assertEquals(smallest, avlTree.kSmallest(5));
    }
    @Test(timeout = TIMEOUT)
    public void testKSmallestTreeTestC() {
        /*
          3
        /   \
       2     6
      /    /   \
     1    4     8
          \     /
           5   7
         */
        avlTree.add(3);
        avlTree.add(2);
        avlTree.add(6);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(8);
        avlTree.add(5);
        avlTree.add(7);
        assertEquals((Integer) 3, avlTree.getRoot().getData());
        List<Integer> smallest = new ArrayList<>();
        smallest.add(1);
        assertEquals(smallest, avlTree.kSmallest(1));
        smallest.add(2);
        assertEquals(smallest, avlTree.kSmallest(2));
        smallest.add(3);
        assertEquals(smallest, avlTree.kSmallest(3));
        smallest.add(4);
        assertEquals(smallest, avlTree.kSmallest(4));
        smallest.add(5);
        assertEquals(smallest, avlTree.kSmallest(5));
        smallest.add(6);
        assertEquals(smallest, avlTree.kSmallest(6));
        smallest.add(7);
        assertEquals(smallest, avlTree.kSmallest(7));
        smallest.add(8);
        assertEquals(smallest, avlTree.kSmallest(8));
    }
}