package org.mergeSort;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomDataGeneratorTest {
    @Test
    void generate1000DataTest() {
        int dataSize = 1000; // Numărul de date din fișier
        String fileName = "data1000.txt"; // Numele fișierului de date

        RandomDataGenerator.generateRandomDataFile(fileName, dataSize);
        System.out.printf("The data file '%s' was successfully generated.%n", fileName);
    }

    @Test
    void generate10000DataTest() {
        int dataSize = 10000; // Numărul de date din fișier
        String fileName = "data10000.txt"; // Numele fișierului de date

        RandomDataGenerator.generateRandomDataFile(fileName, dataSize);
        System.out.printf("The data file '%s' was successfully generated.%n", fileName);
    }

    @Test
    void generate100000DataTest() {
        int dataSize = 100000; // Numărul de date din fișier
        String fileName = "data100000.txt"; // Numele fișierului de date

        RandomDataGenerator.generateRandomDataFile(fileName, dataSize);
        System.out.printf("The data file '%s' was successfully generated.%n", fileName);
    }
}