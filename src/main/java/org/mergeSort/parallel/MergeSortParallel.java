//package org.mergeSort.parallel;
//
//import java.util.concurrent.RecursiveAction;
//
//public class MergeSortParallel extends RecursiveAction {
//
//    private static final int THRESHOLD = 1000; // Threshold for direct sorting
//
//    private final int[] array;
//    private final int start;
//    private final int end;
//
//    /**
//     * Constructor for MergeSortParallel.
//     *
//     * @param array The array to be sorted.
//     * @param start The starting index of the array segment.
//     * @param end   The ending index of the array segment.
//     */
//    public MergeSortParallel(int[] array, int start, int end) {
//        this.array = array;
//        this.start = start;
//        this.end = end;
//    }
//
//    @Override
//    protected void compute() {
//        if (end - start <= 1) {
//            return; // Base case: single element or empty array
//        }
//
//        // If the segment size is below the threshold, sort directly
//        if (end - start <= THRESHOLD) {
//            sequentialSort(array, start, end);
//            return;
//        }
//
//        int mid = (start + end) / 2;
//
//        // Create tasks for left and right halves
//        MergeSortParallel leftTask = new MergeSortParallel(array, start, mid);
//        MergeSortParallel rightTask = new MergeSortParallel(array, mid, end);
//
//        // Fork the tasks to execute them in parallel
//        invokeAll(leftTask, rightTask);
//
//        // Merge the sorted halves
//        merge(array, start, mid, end);
//    }
//
//    /**
//     * Performs a sequential sort using a built-in method.
//     *
//     * @param array The array to be sorted.
//     * @param start The starting index of the segment.
//     * @param end   The ending index of the segment.
//     */
//    private static void sequentialSort(int[] array, int start, int end) {
//        // Sort using built-in method for simplicity
//        java.util.Arrays.sort(array, start, end);
//    }
//
//    /**
//     * Merges two sorted segments of the array.
//     *
//     * @param array The array containing the segments.
//     * @param start The starting index of the first segment.
//     * @param mid   The ending index of the first segment and starting index of the second.
//     * @param end   The ending index of the second segment.
//     */
//    private static void merge(int[] array, int start, int mid, int end) {
//        int[] temp = new int[end - start];
//        int i = start, j = mid, k = 0;
//
//        // Merge the two sorted segments
//        while (i < mid && j < end) {
//            if (array[i] <= array[j]) {
//                temp[k++] = array[i++];
//            } else {
//                temp[k++] = array[j++];
//            }
//        }
//
//        // Copy remaining elements from the left segment
//        while (i < mid) {
//            temp[k++] = array[i++];
//        }
//
//        // Copy remaining elements from the right segment
//        while (j < end) {
//            temp[k++] = array[j++];
//        }
//
//        // Copy the merged elements back into the original array
//        System.arraycopy(temp, 0, array, start, temp.length);
//    }
//}
package org.mergeSort.parallel;

import java.util.concurrent.RecursiveAction;

public class MergeSortParallel extends RecursiveAction {

    private static final int THRESHOLD = 500; // Reduced threshold for parallelism efficiency

    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortParallel(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= 1) {
            return;
        }

        if (end - start <= THRESHOLD) {
            java.util.Arrays.sort(array, start, end);
            return;
        }

        int mid = (start + end) / 2;

        MergeSortParallel leftTask = new MergeSortParallel(array, start, mid);
        MergeSortParallel rightTask = new MergeSortParallel(array, mid, end);

        invokeAll(leftTask, rightTask);

        merge(array, start, mid, end);
    }

    private static void merge(int[] array, int start, int mid, int end) {
        int[] temp = new int[end - start];
        int i = start, j = mid, k = 0;

        while (i < mid && j < end) {
            temp[k++] = array[i] <= array[j] ? array[i++] : array[j++];
        }

        System.arraycopy(array, i, temp, k, mid - i);
        System.arraycopy(array, j, temp, k, end - j);
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
