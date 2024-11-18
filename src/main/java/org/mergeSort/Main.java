package org.mergeSort;

import org.mergeSort.sequential.MergeSortSequential;
import org.mergeSort.parallel.MergeSortParallel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

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
                System.out.println("Performing Sequential Merge Sort...");
                long startTime = System.nanoTime();
                MergeSortSequential.sort(data);
                long endTime = System.nanoTime();
                System.out.println("Sorted Array: " + Arrays.toString(data));
                System.out.println("Time Taken (Sequential): " + (endTime - startTime) / 1_000_000 + " ms");
            } else if (choice == 2) {
                System.out.println("Performing Parallel Merge Sort...");
                ForkJoinPool pool = new ForkJoinPool();
                MergeSortParallel task = new MergeSortParallel(data, 0, data.length);
                long startTime = System.nanoTime();
                pool.invoke(task);
                long endTime = System.nanoTime();
                System.out.println("Sorted Array: " + Arrays.toString(data));
                System.out.println("Time Taken (Parallel): " + (endTime - startTime) / 1_000_000 + " ms");
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
}
