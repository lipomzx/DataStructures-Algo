import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author zexin chen
 * @version 1.0
 * @userid zchen849
 * @GTID 903649317
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: https://www.programiz.com/dsa/quick-sort
 * https://eddmann.com/posts/least-significant-digit-lsd-radix-sort-in-java/
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            swap(arr, min, i);
        }
    }

    /**
     * Helper function to swap element in array
     * @param arr array be sorted
     * @param a first index
     * @param b second index
     * @param <T> T type
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        int start = 0;
        int end = arr.length - 1;
        int swapped = start;
        while (end > start) {
            swapped = start;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i, i + 1);
                    swapped = i;
                }
            }
            end = swapped;
            for (int i = end; i > start; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    swap(arr, i, i - 1);
                    swapped = i;
                }
            }
            start = swapped;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array or comparator is null");
        }
        if (arr.length < 2) {
            return;
        }
        int length = arr.length;
        int mid = length / 2;
        T[] leftA = (T[]) new Object[mid];
        T[] rightA = (T[]) new Object[length - mid];
        for (int i = 0; i < leftA.length; i++) {
            leftA[i] = arr[i];
        }
        for (int i = 0; i < rightA.length; i++) {
            rightA[i] = arr[leftA.length + i];
        }
        mergeSort(leftA, comparator);
        mergeSort(rightA, comparator);

        int leftIdx = 0;
        int rightIdx = 0;
        int currIdx = 0;
        while (leftIdx < mid && rightIdx < (length - mid)) {
            if (comparator.compare(leftA[leftIdx], rightA[rightIdx]) <= 0) {
                arr[currIdx] = leftA[leftIdx];
                leftIdx++;
            } else {
                arr[currIdx] = rightA[rightIdx];
                rightIdx++;
            }
            currIdx++;
        }
        while (leftIdx < mid) {
            arr[currIdx] = leftA[leftIdx];
            currIdx++;
            leftIdx++;
        }
        while (rightIdx < (length - mid)) {
            arr[currIdx] = rightA[rightIdx];
            currIdx++;
            rightIdx++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array or comparator or rand is null");
        }
        quickSort(arr, 0, arr.length, rand, comparator);
    }

    /**
     * Helper function to quick sort
     * @param arr array to be sorted
     * @param left start index
     * @param right last index
     * @param rand random to pick index
     * @param comparator comparator to compare
     * @param <T> T type
     */
    private  static <T> void quickSort(T[] arr, int left, int right, Random rand, Comparator<T> comparator) {
        if (left < right - 1) {
            int pi = partition(arr, left, right, rand.nextInt(right - left) + left, comparator);
            quickSort(arr, left, pi, rand, comparator);
            quickSort(arr, pi + 1, right, rand, comparator);
        }
    }

    /**
     * Helper function to partition index
     * @param arr array to be sorted
     * @param start start index
     * @param end end index
     * @param pivotIdx pivot index
     * @param comparator comparator to compare T
     * @param <T> T typre
     * @return index
     */
    private static <T> int partition(T[] arr, int start, int end, int pivotIdx, Comparator<T> comparator) {
        T pivot = arr[pivotIdx];
        swap(arr, pivotIdx, start);
        pivotIdx = start;
        int i = start;
        int j = end;
        while (i < j) {
            j--;
            i++;
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
            }
        }
        swap(arr, j, pivotIdx);
        return j;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int num : arr) {
                int bucket = num / div;
                if (bucket / 10 != 0) {
                    cont = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<Integer>();
                }
                buckets[bucket % mod + 9].add(num);
            }
            div *= 10;
            int arrIdx = 0;
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int num : buckets[i]) {
                        arr[arrIdx++] = num;
                    }
                    buckets[i].clear();
                }
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list in sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(data);
        int[] arr = new int[data.size()];

        for (int i = 0; i < data.size(); i++) {
            arr[i] = pq.poll();
        }
        return arr;
    }
}
