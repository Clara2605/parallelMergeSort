package org.mergeSort.metrics;


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


    public void startTimer() {
        //Runtime runtime = Runtime.getRuntime();
        //runtime.gc(); // Run garbage collection to minimize memory noise
        //System.gc();
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }


    public double getExecutionTimeMillis() {
        return (endTime - startTime) / 1_000_000.0; // Return as double for precision
    }

    public void startMemoryTracking() {
        //System.gc();
        Runtime runtime = Runtime.getRuntime();
        //runtime.gc();
        initialMemory = runtime.totalMemory() - runtime.freeMemory();
    }

    public void stopMemoryTracking() {
        Runtime runtime = Runtime.getRuntime();
        finalMemory = runtime.totalMemory() - runtime.freeMemory();
        memoryUsed = finalMemory - initialMemory; // Correctly calculate the memory used
    }

    public long getMemoryUsedKB() {
        return memoryUsed / 1024;
        //return memoryUsed;
    }
    public long getMemoryUsedBytes() {
        return memoryUsed; // Return memory usage in bytes
    }
    public void trackMemoryUsage(String stage) {
        Runtime runtime = Runtime.getRuntime();
        long currentMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory used at stage '" + stage + "': " + (currentMemory / 1024) + " KB");

    }

}
