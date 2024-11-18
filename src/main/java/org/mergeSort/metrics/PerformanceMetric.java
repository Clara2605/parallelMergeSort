package org.mergeSort.metrics;

public class PerformanceMetric {
    private long startTime;
    private long endTime;
    private long memoryUsed;

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
