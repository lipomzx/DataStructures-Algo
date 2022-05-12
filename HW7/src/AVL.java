import java.util.NoSuchElementException;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author zexin chen
 * @userid zchen849
 * @GTID 903649317
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data is null");
        }
        this.size = 0;
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        root = add(root, data);
    }

    /**
     * Helper method to add data
     * @param curr current node
     * @param data data been added
     * @return current node to reinforce the tree structure
     */
    private AVLNode<T> add(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(add(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(add(curr.getRight(), data));
        } else {
            return curr;
        }
        updateHeightBalanceFactor(curr);
        return balance(curr);
    }

    /**
     * Update the height and balance factor of node been rotated
     * @param curr current node
     */
    private void updateHeightBalanceFactor(AVLNode<T> curr) {
        curr.setHeight(Math.max(height(curr.getLeft()), height(curr.getRight())) + 1);
        curr.setBalanceFactor(height(curr.getLeft()) - height(curr.getRight()));
    }

    /**
     * left rotate
     * @param curr current node need rotate
     * @return node after rotate
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> t = curr.getRight();
        curr.setRight(t.getLeft());
        t.setLeft(curr);
        updateHeightBalanceFactor(curr);
        updateHeightBalanceFactor(t);
        return t;
    }

    /**
     * right rotate
     * @param curr current node need rotate
     * @return node after rotate
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> t = curr.getLeft();
        curr.setLeft(t.getRight());
        t.setRight(curr);
        updateHeightBalanceFactor(curr);
        updateHeightBalanceFactor(t);
        return t;
    }

    /**
     * Determine when to rotate the node
     * @param curr current node need to rotate
     * @return node after rotate
     */
    private AVLNode<T> balance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotate(curr.getLeft()));
                curr = rightRotate(curr);
            } else {
                curr = rightRotate(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotate(curr.getRight()));
                curr = leftRotate(curr);
            } else {
                curr = leftRotate(curr);
            }
        }
        return curr;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        this.root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for remove data
     * @param curr current node been checked
     * @param data data to remove
     * @param dummy dummy node to record removed data
     * @return tree with node removed
     */
    private AVLNode<T> remove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not found");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else {
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        updateHeightBalanceFactor(curr);
        return balance(curr);
    }

    /**
     * Find successor
     * @param curr current node
     * @param dummy dummy node to record successor
     * @return node after removed with successor
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
            updateHeightBalanceFactor(curr);
            return balance(curr);
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return get(root, data);
    }

    /**
     * Helper method for get
     * @param curr current node
     * @param data data to get
     * @return data found
     */
    private T get(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not found");
        }
        if (data.compareTo(curr.getData()) > 0) {
            return get(curr.getRight(), data);
        } else if (data.compareTo(curr.getData()) < 0) {
            return get(curr.getLeft(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        try {
            get(data);
        } catch (NoSuchElementException ne) {
            return false;
        }
        return true;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return predecessor(root, null, data).getData();
    }

    /**
     * get predecessor with given data
     * @param curr current node
     * @param pre predecessor
     * @param data given data
     * @return predecessor
     */
    private AVLNode<T> predecessor(AVLNode<T> curr, AVLNode<T> pre, T data) {
        if (curr == null) {
            return pre;
        }
        if (data.compareTo(curr.getData()) == 0) {
            if (curr.getLeft() != null) {
                return getPrecessor(curr.getLeft());
            }
        } else if (data.compareTo(curr.getData()) < 0) {
            return predecessor(curr.getLeft(), pre, data);
        } else {
            pre = curr;
            return predecessor(curr.getRight(), pre, data);
        }
        return pre;
    }

    /**
     * Helper method to get predecessor
     * @param curr current node
     * @return predecessor
     */
    private AVLNode<T> getPrecessor(AVLNode<T> curr) {
        while (curr.getRight() != null) {
            curr = curr.getRight();
        }
        return curr;
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     *  /
     * 10
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (k == 0) {
            return null;
        }
        List<T> kL = inorder(root, new ArrayList<>(), k);
        return kL.subList(0, k);
    }

    /**
     * Traverse the tree inorder to get a sorted list
     * @param curr current node
     * @param inL sorted inorder list
     * @return sorted inorder list of elements in AVL tree
     */
    private List<T> inorder(AVLNode<T> curr, List<T> inL, int k) {
        if (curr == null) {
            return inL;
        }
        if(inL.size() >= k) {
            return  inL;
        }
        inorder(curr.getLeft(), inL, k);
        if(inL.size() >= k) {
            return  inL;
        }
        inL.add(curr.getData());
        if(inL.size() >= k) {
            return  inL;
        }
        inorder(curr.getRight(), inL, k);
        return inL;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper method to get height of current node
     * @param curr current node
     * @return height of current node
     */
    private int height(AVLNode<T> curr) {
        return curr == null ? -1 : curr.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}