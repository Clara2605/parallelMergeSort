package org.mergeSort.metrics;

import org.mergeSort.parallel.MergeSortParallel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class ParallelMetric extends PerformanceMetric {

//    public void executeParallelSort(int[] array) {
//        startTimer();
//        //calculateMemoryUsage();
//        startMemoryTracking(); // Start tracking memory usage
//        ForkJoinPool pool = new ForkJoinPool();
//        MergeSortParallel task = new MergeSortParallel(array, 0, array.length);
//
//        pool.invoke(task);
//        stopMemoryTracking();  // Stop tracking memory usage
//        stopTimer();
//    }
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();
    public Map<String, Double> executeParallelSort(int[] array, int dataSize) {
        Map<String, Double> metrics = new HashMap<>();
        //ForkJoinPool pool = new ForkJoinPool(MAX_THREADS);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        startTimer();
        startMemoryTracking();


        // Măsurăm memoria înainte de sortare
        trackMemoryUsage("Before sorting");
        try {
            pool.invoke(new MergeSortParallel(array, 0, array.length));
        } finally {
            pool.shutdown();
        }


        // Măsurăm memoria după sortare
        trackMemoryUsage("After sorting");
        stopMemoryTracking();
        stopTimer();

        metrics.put("Parallel Execution Time (" + dataSize + ")", getExecutionTimeMillis());
        metrics.put("Parallel Memory Usage (" + dataSize + ")", (double) getMemoryUsedKB());
        return metrics;
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
