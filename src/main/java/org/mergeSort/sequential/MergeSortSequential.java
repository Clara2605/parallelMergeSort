package org.mergeSort.sequential;

import java.util.Arrays;

public class MergeSortSequential {

    /**
     * Sorts the given array using the Merge Sort algorithm.
     *
     * @param array The array to sort.
     */
    public static void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        int mid = array.length / 2;

        // Split the array into two halves
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        // Recursively sort the two halves
        sort(left);
        sort(right);

        // Merge the sorted halves
        merge(array, left, right);
    }

    /**
     * Merges two sorted arrays into a single sorted array.
     *
     * @param result The resulting array.
     * @param left   The left sorted array.
     * @param right  The right sorted array.
     */
    private static void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        // Merge elements from left and right arrays
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        // Copy remaining elements from the left array
        while (i < left.length) {
            result[k++] = left[i++];
        }

        // Copy remaining elements from the right array
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }
}
