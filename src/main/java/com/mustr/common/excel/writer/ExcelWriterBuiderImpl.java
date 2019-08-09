package com.mustr.common.excel.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;

public class ExcelWriterBuiderImpl implements ExcelWriterBuilder {

    private GenerateContext context;
    
    @Override
    public void init(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        context = new GenerateContextImpl(outputStream, typeEnum);
    }

    private void addData(List<List<Object>> data) {
        if (data != null && !data.isEmpty()) {
            int rowNum = context.getCurrentSheet().getLastRowNum();
            if (rowNum == 0) {
                Row row = context.getCurrentSheet().getRow(0);
                if(row == null) {
                    rowNum = -1;
                }
            }
            int size = data.size();
            for (int i = 0; i < size; i++) {
                int n = i + rowNum + 1;
                Row currRow = context.getCurrentSheet().createRow(n);
                addOneRowOfDataToExcel(data.get(i), currRow);
            }
        }
    }

    private void addOneRowOfDataToExcel(List<Object> oneRowData, Row row) {
        if (oneRowData != null && oneRowData.size() > 0) {
            for (int i = 0; i < oneRowData.size(); i++) {
                row.setHeight((short)(30 * 20));
                Cell cell = row.createCell(i);
                if (context.getCurrentContentStyle() != null) {
                    cell.setCellStyle(context.getCurrentContentStyle());
                }
                Object object = oneRowData.get(i);
                if (object instanceof Boolean) {
                    cell.setCellValue((Boolean) object);
                } else if (object instanceof Integer) {
                    cell.setCellValue((Integer) object);
                } else if (object instanceof Long) {
                    cell.setCellValue((Long) object);
                } else if (object instanceof Float) {
                    cell.setCellValue((Float) object);
                } else if (object instanceof Double) {
                    cell.setCellValue((Double) object);
                } else if (object instanceof Date) {
                    cell.setCellValue((Date) object);
                    cell.setCellStyle(getDateFormat());
                } else if (object instanceof Calendar) {
                    cell.setCellValue((Calendar) object);
                    cell.setCellStyle(getDateFormat());
                } else {
                    cell.setCellValue((String) object);
                }
            }
        }
    }
   
    private CellStyle getDateFormat() {
        CreationHelper createHelper = context.getWorkbook().getCreationHelper();
        CellStyle currentContentStyle = context.getWorkbook().createCellStyle();
        if (context.getCurrentContentStyle() != null) {
            currentContentStyle.cloneStyleFrom(context.getCurrentContentStyle());
        }
        currentContentStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        return currentContentStyle;
    }
    
    @Override
    public void addData(List<List<Object>> data, Sheet sheetParam) {
        context.buildCurrentSheet(sheetParam);
        addData(data);
    }

    @Override
    public void finish() {
        try {
            context.getWorkbook().write(context.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
