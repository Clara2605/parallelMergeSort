package org.mergeSort.parallel;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortParallel extends RecursiveAction {

    private static final int THRESHOLD = 500; // Reduced threshold for parallelism efficiency
    //private static final int THRESHOLD = 1000;
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
        int[] temp = Arrays.copyOfRange(array, start, end);
        int i = 0, j = mid - start, k = start;

        while (i < mid - start && j < temp.length) {
            array[k++] = (temp[i] <= temp[j]) ? temp[i++] : temp[j++];
        }
        while (i < mid - start) array[k++] = temp[i++];
        while (j < temp.length) array[k++] = temp[j++];
    }
}
