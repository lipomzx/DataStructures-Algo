import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author zexin chen
 * @version 1.0
 * @userid zchen849
 * @GTID 903649317
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: https://knowm.org/iterating-through-a-collection-in-java/
 * https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
 * https://www.geeksforgeeks.org/level-order-traversal-line-line-set-3-using-one-queue/
 * https://stackoverflow.com/questions/2597637/finding-height-in-binary-search-tree
 * https://medium.com/algorithm-problems/distance-between-2-nodes-in-a-bst-8be78f71871e
 * https://www.geeksforgeeks.org/print-path-between-any-two-nodes-in-a-binary-tree/
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data is null");
        }
        this.size = 0; //initialize the size for add method in for-each loop
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        this.root = add(root, data);
    }

    /**
     * Helper method for add()
     * @param curr current node in the tree
     * @param data data being added
     * @return BSTNode curr to reconstruct the tree
     */
    private BSTNode<T> add(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(add(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(add(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        this.root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method for remove()
     * @param curr current node in the tree
     * @param data data being removed
     * @param dummy node store removed node
     * @return removed node
     */
    private BSTNode<T> remove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not found");
        } else if (data.compareTo(curr.getData()) < 0) {
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
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * Helper method for remove() by finding the successor when current removed node
     * has two children
     * @param curr current removed node
     * @param dummy node store removed node.data
     * @return return current new node to reconstruct the tree
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
            return curr;
        }
    }


    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        return get(data, root);
    }

    /**
     * Helper method for get()
     * @param data data try to find
     * @param node current node being compared with data
     * @return found data
     */
    private T get(T data, BSTNode<T> node) {
        if (node.getData().compareTo(data) == 0) {
            return node.getData();
        } else if (data.compareTo(node.getData()) < 0) {
            return get(data, node.getLeft());
        } else if (data.compareTo(node.getData()) > 0) {
            return get(data, node.getRight());
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        return contains(data, root);
    }

    /**
     * Helper method for contains()
     * @param data data to check whether exists
     * @param node current node being compared with data
     * @return found data
     */
    private boolean contains(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else if (data.compareTo(node.getData()) > 0) {
            return contains(data, node.getRight());
        } else {
            return true;
        }
    }


    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> preOrder = new ArrayList<>();
        preorder(root, preOrder);
        return preOrder;
    }

    /**
     * Helper method for preorder()
     * @param temp current node
     * @param preList list stored nodes
     */
    private void preorder(BSTNode<T> temp, List<T> preList) {
        if (temp == null) {
            return;
        }
        preList.add(temp.getData());
        preorder(temp.getLeft(), preList);
        preorder(temp.getRight(), preList);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> inOrder = new ArrayList<>();
        inorder(root, inOrder);
        return inOrder;
    }

    /**
     * Helper method for inorder()
     * @param temp current node
     * @param inList list stored nodes
     */
    private void inorder(BSTNode<T> temp, List<T> inList) {
        if (temp == null) {
            return;
        }
        inorder(temp.getLeft(), inList);
        inList.add(temp.getData());
        inorder(temp.getRight(), inList);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> postOrder = new ArrayList<>();
        postorder(root, postOrder);
        return postOrder;
    }

    /**
     * Helper method for postorder()
     * @param temp current node
     * @param postList list stored nodes
     */
    private void postorder(BSTNode<T> temp, List<T> postList) {
        if (temp == null) {
            return;
        }
        postorder(temp.getLeft(), postList);
        postorder(temp.getRight(), postList);
        postList.add(temp.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> l = new ArrayList<>();
        if (size == 0) {
            return l;
        } else {
            q.add(root);
            while (!q.isEmpty()) {
                BSTNode<T> curr = q.remove();
                l.add(curr.getData());
                if (curr.getLeft() != null) {
                    q.add(curr.getLeft());
                }
                if (curr.getRight() != null) {
                    q.add(curr.getRight());
                }
            }
            return l;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper method for height()
     * @param node current node traversed
     * @return height
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int left = height(node.getLeft());
            int right = height(node.getRight());
            if (left > right) {
                return left + 1;
            } else {
                return right + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException();
        }
        if (!contains(data1) || !contains(data2)) {
            throw new NoSuchElementException();
        }
        List<T> path = new LinkedList<>();
        if (data1.compareTo(data2) > 0) {
            T t = data1;
            data1 = data2;
            data2 = t;
        }
        BSTNode<T> ancestor = findLCA(root, data1, data2);
        getPath(ancestor, path, data1, true);
        path.remove(path.size() - 1);
        getPath(ancestor, path, data2, false);
        return path;
    }

    /**
     * Helper method for findPathBetween()
     * @param node current node
     * @param arr list stored the path
     * @param data data need to find path from
     * @param flag check if the first half path or second
     * @return whether exists a pth
     */
    private boolean getPath(BSTNode<T> node, List<T> arr, T data, boolean flag) {
        if (node == null) {
            return  false;
        }
        if (flag) { // to make final path list in order from data1 to data2
            arr.add(0, node.getData());
        } else {
            arr.add(node.getData());
        }
        if (node.getData() == data) {
            return true;
        }
        if (getPath(node.getLeft(), arr, data, flag) || getPath(node.getRight(), arr, data, flag)) {
            return true;
        }
        arr.remove(arr.size() - 1);
        return false;
    }

    /**
     * Helper method for findPathbetween() by finding lowest common ancestor
     * @param node current node
     * @param data1 data 1 to compare
     * @param data2 data 2 to compare
     * @return lca
     */
    private BSTNode<T> findLCA(BSTNode<T> node, T data1, T data2) {
        if (node == null) {
            return null;
        }
        if (node.getData() == data1 || node.getData() == data2) {
            return node;
        }
        BSTNode<T> leftN = null;
        BSTNode<T> rightN = null;
        if (node.getData().compareTo(data2) > 0) {
            leftN = findLCA(node.getLeft(), data1, data2);
        } else if (node.getData().compareTo(data1) < 0) {
            rightN = findLCA(node.getRight(), data1, data2);
        } else {
            leftN = findLCA(node.getLeft(), data1, data2);
            rightN = findLCA(node.getRight(), data1, data2);
        }
        if (leftN != null && rightN != null) {
            return node;
        }
        else if (leftN == null) {
            return rightN;
        } else {
            return leftN;
        }
    }



    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
