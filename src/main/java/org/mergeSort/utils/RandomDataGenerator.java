package org.mergeSort.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Utility class to generate a file with unique random numbers.
 */
public class RandomDataGenerator {

    // Constants
    private static final String OUTPUT_DIRECTORY = "src/main/resources/data_input";
    private static final int MAX_RANDOM_NUMBER = 100_000;

    private RandomDataGenerator() {
        // Private constructor to prevent instantiation of a utility class
    }

    /**
     * Generates a file with unique random integers.
     *
     * @param fileName The name of the file to generate.
     * @param dataSize The number of unique random integers to generate.
     */
    public static void generateRandomDataFile(String fileName, int dataSize) {
        validateInputs(fileName, dataSize);

        // Create the output directory if it does not exist
        ensureOutputDirectoryExists();

        String fullPath = OUTPUT_DIRECTORY + File.separator + fileName;
        Set<Integer> uniqueNumbers = new HashSet<>();

        try (FileWriter writer = new FileWriter(fullPath)) {
            generateUniqueNumbers(dataSize, uniqueNumbers, writer);
            System.out.printf("The data file '%s' was successfully generated in '%s'!%n", fileName, OUTPUT_DIRECTORY);
        } catch (IOException e) {
            System.err.println("Error while generating the file: " + e.getMessage());
        }
    }

    /**
     * Validates the inputs to ensure they are appropriate.
     *
     * @param fileName The file name.
     * @param dataSize The size of the data.
     */
    private static void validateInputs(String fileName, int dataSize) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }
        if (dataSize <= 0) {
            throw new IllegalArgumentException("Data size must be greater than 0.");
        }
    }

    /**
     * Ensures the output directory exists, creating it if necessary.
     */
    private static void ensureOutputDirectoryExists() {
        File directory = new File(OUTPUT_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalStateException("Failed to create output directory: " + OUTPUT_DIRECTORY);
        }
    }

    /**
     * Generates unique random integers and writes them to the file.
     *
     * @param dataSize      The number of unique integers to generate.
     * @param uniqueNumbers The set to store unique integers.
     * @param writer        The file writer to write the integers.
     * @throws IOException If an error occurs while writing to the file.
     */
    private static void generateUniqueNumbers(int dataSize, Set<Integer> uniqueNumbers, FileWriter writer)
            throws IOException {
        Random random = new Random();
        while (uniqueNumbers.size() < dataSize) {
            int number = random.nextInt(MAX_RANDOM_NUMBER); // Numbers between 0 and 99,999
            if (uniqueNumbers.add(number)) { // Add the number if it is unique
                writer.write(number + System.lineSeparator());
            }
        }
    }
}
