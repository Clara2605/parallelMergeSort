package org.mergeSort;

import org.mergeSort.metrics.ExcelDataRecorder;
import org.mergeSort.metrics.ParallelMetric;
import org.mergeSort.metrics.SequentialMetric;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to MergeSort Application!");
        System.out.println("Choose the sorting method:");
        System.out.println("1. Sequential Merge Sort");
        System.out.println("2. Parallel Merge Sort");
        int choice = scanner.nextInt();

        System.out.println("Choose the size of the dataset:");
        System.out.println("1. 1000 elements");
        System.out.println("2. 10,000 elements");
        System.out.println("3. 100,000 elements");
        int sizeChoice = scanner.nextInt();

        String fileName = getFileName(sizeChoice);
        if (fileName == null) {
            System.out.println("Invalid size choice. Exiting.");
            return;
        }

        try {
            int[] data = readDataFromFile(fileName);
            System.out.println("Array before sorting: " + arrayToString(data));

            int[] dataClone = data.clone(); // Clone the array for sorting
            Map<String, Double> metrics;

            if (choice == 1) {
                System.out.println("Executing Sequential Merge Sort...");
                SequentialMetric sequentialMetric = new SequentialMetric();
                metrics = sequentialMetric.executeSequentialSort(dataClone, getDataSize(sizeChoice));
                displayMetrics("Sequential", metrics);
            } else if (choice == 2) {
                System.out.println("Executing Parallel Merge Sort...");
                ParallelMetric parallelMetric = new ParallelMetric();
                metrics = parallelMetric.executeParallelSort(dataClone, getDataSize(sizeChoice));
                displayMetrics("Parallel", metrics);
            } else {
                System.out.println("Invalid choice. Exiting.");
                return;
            }

            // Display sorted array
            System.out.println("Array after sorting: " + arrayToString(dataClone));

            // Save metrics to Excel
            ExcelDataRecorder.saveMetrics(metrics, "PerformanceMetrics.xlsx");
            System.out.println("Metrics saved to PerformanceMetrics.xlsx");

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static String getFileName(int sizeChoice) {
        switch (sizeChoice) {
            case 1:
                return "data_input/data1000.txt";
            case 2:
                return "data_input/data10000.txt";
            case 3:
                return "data_input/data100000.txt";
            default:
                return null;
        }
    }

    private static int getDataSize(int sizeChoice) {
        switch (sizeChoice) {
            case 1:
                return 1000;
            case 2:
                return 10000;
            case 3:
                return 100000;
            default:
                throw new IllegalArgumentException("Invalid size choice.");
        }
    }

    private static int[] readDataFromFile(String fileName) throws IOException {
        return java.nio.file.Files.lines(java.nio.file.Paths.get("src/main/resources/" + fileName))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static void displayMetrics(String method, Map<String, Double> metrics) {
        System.out.println(method + " Sort Metrics:");
        metrics.forEach((key, value) -> {
            if (key.contains("Time")) {
                System.out.printf("%s: %.3f ms%n", key, value);
            } else if (key.contains("Memory")) {
                System.out.printf("%s: %.3f KB%n", key, value);
            }
        });
    }

    private static String arrayToString(int[] array) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i < array.length - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
