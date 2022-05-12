import java.util.LinkedList;
import java.util.NoSuchElementException;
/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author zexin chen
 * @version 1.0
 * @userid zchen849
 * @GTID 903649317
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        CircularSinglyLinkedListNode<T> tmp = new CircularSinglyLinkedListNode<>(data);
        CircularSinglyLinkedListNode<T> current = head;
        if (size == 0) {
            head = tmp;
            head.setNext(head);
        } else if (index == 0 || index == size) {
            tmp.setNext(head.getNext());
            tmp.setData(head.getData());
            head.setNext(tmp);
            head.setData(data);
            if (index == size) {
                head = tmp;
            }
        }/* else if (index == size) {
            while (current.getNext() != head) {
                current = current.getNext();
            }
            current.setNext(tmp);
            tmp.setNext(head);
        }*/ else {
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            tmp.setNext(current.getNext());
            current.setNext(tmp);
        }
        size++;
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        if (isEmpty()) {
            return null;
        }
        T tmp;
        CircularSinglyLinkedListNode<T> curr = head;
        if (index == 0) {
            tmp = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        } else if (index == size - 1) {
            while (curr.getNext().getNext() != head) {
                curr = curr.getNext();
            }
            tmp = curr.getNext().getData();
            curr.setNext(head);
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            tmp = curr.getNext().getData();
            curr.setNext(curr.getNext().getNext());
        }
        if (--size == 0) {
            head = null;
        }
        return tmp;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        CircularSinglyLinkedListNode<T> curr = head;
        if (index == 0) {
            return head.getData();
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            return curr.getNext().getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            return null;
        }
        CircularSinglyLinkedListNode<T> curr = head;
        CircularSinglyLinkedListNode<T> prev = head;
        T lastOcc = null;
        if (curr.getData().equals(data)) {
            lastOcc = curr.getData();
            prev = curr;
        }
        while (curr.getNext().getNext() != head) {
            if (curr.getNext().getData().equals(data)) {
                lastOcc = curr.getNext().getData();
                prev = curr;
            }
            curr = curr.getNext();
        }
        if (curr.getNext().getData().equals(data)) {
            lastOcc = curr.getNext().getData();
            prev = curr;
        }
        prev.setNext(prev.getNext().getNext());
        size--;
        return lastOcc;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] newArr = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            newArr[i] = curr.getData();
            curr = curr.getNext();
        }
        return newArr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
