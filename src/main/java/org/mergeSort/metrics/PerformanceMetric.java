package org.mergeSort.metrics;

import org.mergeSort.parallel.MergeSortParallel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class PerformanceMetric {
    private long startTime;
    private long endTime;
    private long memoryUsed;

    public Map<String, Double> executeParallelSort(int[] array, int dataSize) {
        Map<String, Double> metrics = new HashMap<>();
        startTimer();
        calculateMemoryUsage();
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSortParallel(array, 0, array.length));
        stopTimer();

        metrics.put("Parallel Execution Time (" + dataSize + ")", (double) getExecutionTimeMillis());
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

    public void calculateMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        memoryUsed = runtime.totalMemory() - runtime.freeMemory();
    }

    public long getExecutionTimeMillis() {
        return (endTime - startTime) / 1_000_000;
    }

    public long getMemoryUsedKB() {
        return memoryUsed / 1024;
    }
}
