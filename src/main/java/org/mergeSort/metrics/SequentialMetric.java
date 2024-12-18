package org.mergeSort.metrics;

import org.mergeSort.sequential.MergeSortSequential;

import java.util.HashMap;
import java.util.Map;

public class SequentialMetric extends PerformanceMetric {

//    public void executeSequentialSort(int[] array) {
//        startTimer();
//        calculateMemoryUsage();
//        MergeSortSequential.sort(array);
//        stopTimer();
//    }
    public Map<String, Double> executeSequentialSort(int[] array, int dataSize) {
        Map<String, Double> metrics = new HashMap<>();
        startTimer();
        calculateMemoryUsage();
        MergeSortSequential.sort(array);
        stopTimer();

        metrics.put("Sequential Execution Time (" + dataSize + ")", (double) getExecutionTimeMillis());
        metrics.put("Sequential Memory Usage (" + dataSize + ")", (double) getMemoryUsedKB());
        return metrics;
    }

    public void printMetrics(int[] array) {
        System.out.println("Sequential Sort Metrics:");
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
