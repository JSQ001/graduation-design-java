package com.jsq.util;

import org.apache.poi.ss.usermodel.Cell;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by cbc on 17/11/14.
 */
public class ExcelUtils {

    private static final NumberFormat formatter = new DecimalFormat("#0");
    public static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue().trim();
            case Cell.CELL_TYPE_NUMERIC:
                return formatter.format(cell.getNumericCellValue());
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            default:
                return "";
        }
    }
}
