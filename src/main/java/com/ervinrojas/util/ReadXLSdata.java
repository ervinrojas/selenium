package com.ervinrojas.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadXLSdata {

    public static Object[][] getTestData(String filePath, String sheetName) {
        Object[][] data = null;

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            // Obtener número de filas (excluyendo encabezado) y columnas
            int totalRows = sheet.getPhysicalNumberOfRows() - 1; // -1 para saltar el encabezado
            Row headerRow = sheet.getRow(0);
            int totalCols = headerRow.getPhysicalNumberOfCells();

            data = new Object[totalRows][totalCols];

            // Iterar desde la fila 1 (saltamos encabezado)
            for (int i = 1; i <= totalRows; i++) {
                Row row = sheet.getRow(i);

                for (int j = 0; j < totalCols; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = getCellValueAsString(cell);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }

        return data;
    }

    // Método auxiliar para manejar diferentes tipos de datos en las celdas
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                // Si es un número entero, quitar el .0 que añade Excel por defecto
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double num = cell.getNumericCellValue();
                    if (num == Math.floor(num)) {
                        return String.valueOf((int) num);
                    }
                    return String.valueOf(num);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}