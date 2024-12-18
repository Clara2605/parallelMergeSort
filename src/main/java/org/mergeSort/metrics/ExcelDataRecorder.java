package org.mergeSort.metrics;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

public class ExcelDataRecorder {

    private static final String OUTPUT_DIRECTORY = "src/main/resources/data_output/";

    public static void saveMetrics(Map<String, Double> metrics, String fileName) throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_DIRECTORY));
        String filePath = OUTPUT_DIRECTORY + fileName;

        Workbook workbook;
        Sheet sheet;

        // Load or create the workbook and sheet
        if (Files.exists(Paths.get(filePath))) {
            try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
                workbook = WorkbookFactory.create(is);
            }
        } else {
            workbook = new XSSFWorkbook();
        }

        sheet = workbook.getSheet("Metrics");
        if (sheet == null) {
            sheet = workbook.createSheet("Metrics");
            initializeHeaderRow(sheet);
        }

        // Ensure metrics are added to the correct row
        int rowIndex = findRowForMetrics(sheet, metrics);

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        // Populate the row with metric values under the correct columns
        for (Map.Entry<String, Double> entry : metrics.entrySet()) {
            int colIndex = getColumnIndex(sheet, entry.getKey());
            Cell cell = row.createCell(colIndex, CellType.NUMERIC);
            cell.setCellValue(entry.getValue());
        }

        // Write to the file
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(filePath))) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private static void initializeHeaderRow(Sheet sheet) {
        Row header = sheet.createRow(0);
        String[] headers = {
                "Sequential Execution Time (1000)", "Sequential Memory Usage (1000)",
                "Sequential Execution Time (10000)", "Sequential Memory Usage (10000)",
                "Sequential Execution Time (100000)", "Sequential Memory Usage (100000)",
                "Parallel Execution Time (1000)", "Parallel Memory Usage (1000)",
                "Parallel Execution Time (10000)", "Parallel Memory Usage (10000)",
                "Parallel Execution Time (100000)", "Parallel Memory Usage (100000)"
        };
        for (int i = 0; i < headers.length; i++) {
            header.createCell(i, CellType.STRING).setCellValue(headers[i]);
        }
    }

    private static int getColumnIndex(Sheet sheet, String headerName) {
        Row headerRow = sheet.getRow(0);
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equals(headerName)) {
                return cell.getColumnIndex();
            }
        }
        throw new IllegalArgumentException("Header not found: " + headerName);
    }

    private static int findRowForMetrics(Sheet sheet, Map<String, Double> metrics) {
        // Check each row to see if the metrics column is empty
        int colIndex = getColumnIndex(sheet, metrics.keySet().iterator().next());
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null || row.getCell(colIndex) == null || row.getCell(colIndex).getCellType() == CellType.BLANK) {
                return rowIndex;
            }
        }
        // If no suitable row is found, create a new row
        return sheet.getLastRowNum() + 1;
    }
}
