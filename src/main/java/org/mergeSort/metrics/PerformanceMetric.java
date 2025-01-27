package org.mergeSort.metrics;

import org.mergeSort.parallel.MergeSortParallel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class PerformanceMetric {
    private long startTime;
    private long endTime;
    private long memoryUsed;
    ///   ///
    private long initialMemory;
    private long finalMemory;

    public Map<String, Double> executeParallelSort(int[] array, int dataSize) {
        Map<String, Double> metrics = new HashMap<>();
        startTimer();
        startMemoryTracking();
        //calculateMemoryUsage();
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSortParallel(array, 0, array.length));
        stopTimer();
        stopMemoryTracking();
        metrics.put("Parallel Execution Time (" + dataSize + ")", getExecutionTimeMillis());
        metrics.put("Parallel Memory Usage (" + dataSize + ")", (double) getMemoryUsedKB());
        return metrics;
    }

    public void startTimer() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Run garbage collection to minimize memory noise
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

//    public void calculateMemoryUsage() {
//        Runtime runtime = Runtime.getRuntime();
//        memoryUsed = runtime.totalMemory() - runtime.freeMemory();
//    }

    public double getExecutionTimeMillis() {
        return (endTime - startTime) / 1_000_000.0; // Return as double for precision
    }

    public void startMemoryTracking() {
        Runtime runtime = Runtime.getRuntime();
        initialMemory = runtime.totalMemory() - runtime.freeMemory();
    }

    public void stopMemoryTracking() {
        Runtime runtime = Runtime.getRuntime();
        finalMemory = runtime.totalMemory() - runtime.freeMemory();
        memoryUsed = finalMemory - initialMemory; // Correctly calculate the memory used
    }

    public long getMemoryUsedKB() {
        return memoryUsed / 1024;
    }
    public long getMemoryUsedBytes() {
        return memoryUsed; // Return memory usage in bytes
    }

}
