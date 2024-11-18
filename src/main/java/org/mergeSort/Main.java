package org.mergeSort;

import org.mergeSort.metrics.ParallelMetric;
import org.mergeSort.metrics.SequentialMetric;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

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
            System.out.println("Initial Array: " + Arrays.toString(data));
            if (choice == 1) {
                System.out.println("Executing Sequential Merge Sort...");
                SequentialMetric sequentialMetric = new SequentialMetric();
                int[] dataClone = data.clone(); // Clone to preserve the original dataset
                sequentialMetric.executeSequentialSort(dataClone);
                sequentialMetric.printMetrics(dataClone);
                System.out.println("Sorted Array: " + arrayToString(dataClone));
            } else if (choice == 2) {
                System.out.println("Executing Parallel Merge Sort...");
                ParallelMetric parallelMetric = new ParallelMetric();
                int[] dataClone = data.clone(); // Clone to preserve the original dataset
                parallelMetric.executeParallelSort(dataClone);
                parallelMetric.printMetrics(dataClone);
                System.out.println("Sorted Array: " + arrayToString(dataClone));
            } else {
                System.out.println("Invalid choice. Exiting.");
            }
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

    private static int[] readDataFromFile(String fileName) throws IOException {
        File file = new File("src/main/resources/" + fileName);
        if (!file.exists()) {
            throw new IOException("File not found: " + fileName);
        }

        return Files.lines(Paths.get(file.getPath()))
                .mapToInt(Integer::parseInt)
                .toArray();
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
