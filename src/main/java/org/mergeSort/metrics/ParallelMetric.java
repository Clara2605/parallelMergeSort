package org.mergeSort.metrics;

import org.mergeSort.parallel.MergeSortParallel;

import java.util.concurrent.ForkJoinPool;

public class ParallelMetric extends PerformanceMetric {

    public void executeParallelSort(int[] array) {
        startTimer();
        //calculateMemoryUsage();
        startMemoryTracking(); // Start tracking memory usage
        ForkJoinPool pool = new ForkJoinPool();
        MergeSortParallel task = new MergeSortParallel(array, 0, array.length);

        pool.invoke(task);
        stopMemoryTracking();  // Stop tracking memory usage
        stopTimer();
    }

    public void printMetrics(int[] array) {
        System.out.println("Parallel Sort Metrics:");
        System.out.println("Execution Time: " + getExecutionTimeMillis() + " ms");
        System.out.println("Memory Used: " + getMemoryUsedKB() + " KB");
        System.out.println("Is Sorted: " + isSorted(array));
    }

    private boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
