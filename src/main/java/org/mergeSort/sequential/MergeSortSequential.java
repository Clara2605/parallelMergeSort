package org.mergeSort.sequential;

import java.util.Arrays;

public class MergeSortSequential {

    public static void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        int mid = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        sort(left);
        sort(right);

        merge(array, left, right);
    }

    private static void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            result[k++] = left[i] <= right[j] ? left[i++] : right[j++];
        }

        System.arraycopy(left, i, result, k, left.length - i);
        System.arraycopy(right, j, result, k, right.length - j);
    }
}
