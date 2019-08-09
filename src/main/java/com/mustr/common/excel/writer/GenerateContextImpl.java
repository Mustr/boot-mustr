package com.mustr.common.excel.writer;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mustr.common.excel.Util;
import com.mustr.common.excel.component.DropdownMenu;
import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.SheetStyle;


public class GenerateContextImpl implements GenerateContext {

    private Sheet currentSheet;

    private Workbook workbook;

    private OutputStream outputStream;

    private CellStyle defaultCellStyle;

    private CellStyle currentHeadCellStyle;

    private CellStyle currentContentCellStyle;
    
    private SheetHead sheetHead;
    
    private SheetStyle sheetStyle;

    public GenerateContextImpl(OutputStream out, ExcelTypeEnum excelType) {
        if (ExcelTypeEnum.XLS.equals(excelType)) {
            this.workbook = new HSSFWorkbook();
        } else {
            this.workbook = new XSSFWorkbook();
        }
        this.outputStream = out;
        this.defaultCellStyle = buildDefaultCellStyle();
    }

    private CellStyle buildDefaultCellStyle() {
        CellStyle newCellStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        newCellStyle.setLocked(true);
        newCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        newCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        newCellStyle.setBorderBottom(BorderStyle.THIN);
        newCellStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        newCellStyle.setBorderLeft(BorderStyle.THIN);
        newCellStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        newCellStyle.setBorderRight(BorderStyle.THIN);
        newCellStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        newCellStyle.setBorderTop(BorderStyle.THIN);
        newCellStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        return newCellStyle;
    }
    
    private CellStyle buildeDefaultContentStyle() {
        CellStyle newCellStyle = this.workbook.createCellStyle();
        Font font = this.workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        newCellStyle.setFont(font);
        newCellStyle.setWrapText(true);
        newCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        newCellStyle.setAlignment(HorizontalAlignment.CENTER);
        return newCellStyle;
    }
    
    @Override
    public void buildCurrentSheet(com.mustr.common.excel.component.Sheet sheet) {
        if (sheet == null) {
            return;
        }
        this.currentSheet = this.workbook.createSheet(sheet.getSheetName() != null ? sheet.getSheetName() : String
            .valueOf(sheet.getSheetNo()));
        this.currentSheet.setDefaultColumnWidth(20);
        buildHead(sheet);
        buildSheetStyle(sheet.getStyle());
        if (sheetHead != null) {
            appendHead();
        }
       // 创建下拉选菜单
        List<DropdownMenu> dropdownMenus = sheet.getDropdownMenus();
        if(Util.isNotEmpty(dropdownMenus)){
            buildDropdownMenus(dropdownMenus);
        }
    }

    private void buildDropdownMenus(List<DropdownMenu> dropdownMenus) {
        for (DropdownMenu menu : dropdownMenus) {
            List<String> menuItems = menu.getMenuItems();
            if (Util.isNotEmpty(menuItems)) {
                final Sheet s = currentSheet;
                DataValidationHelper dvh = s.getDataValidationHelper();
                String[] arr = new String[menuItems.size()];
                menuItems.toArray(arr);
                DataValidationConstraint constraint = dvh.createExplicitListConstraint(arr);
                DataValidation validation = dvh.createValidation(constraint,
                    new CellRangeAddressList(menu.getFirstRow(), menu.getLastRow(), menu.getFirstCol(), menu.getLastCol()));
                if (validation instanceof XSSFDataValidation) {
                    validation.setSuppressDropDownArrow(true);
                    validation.setShowErrorBox(true);
                } else {
                    validation.setSuppressDropDownArrow(false);
                }
                s.addValidationData(validation);
            }
        }
    }
    

    private void buildHead(com.mustr.common.excel.component.Sheet sheet) {
        this.sheetHead = new SheetHead(sheet);
    }
    
    private void appendHead() {
        if (!sheetHead.isEmpty()) {
            List<CellRangeAddress> rangs = sheetHead.getCellRangeAddress();
            if (rangs != null && rangs.size() > 0) {
                for (CellRangeAddress cellRangeAddress : rangs) {
                    currentSheet.addMergedRegion(cellRangeAddress);
                }
            }
            int rowNum = sheetHead.getRowNum();
            for (int i = 0; i < rowNum; i++) {
                Row row = currentSheet.createRow(i);
                CellStyle currRowStyle = null;
                if (sheetStyle != null && i == 0) {
                    currRowStyle = getFirstHeadCellStyle();
                    if(sheetStyle.getFirstHeadRowHeight() != null){
                        row.setHeightInPoints(sheetStyle.getFirstHeadRowHeight());
                    }
                }
                addOneRowOfHeadDataToExcel(row, sheetHead.getRowByRowIdx(i), currRowStyle);
            }
        }
    }
    
    private void addOneRowOfHeadDataToExcel(Row row, List<String> headByRowNum, CellStyle style) {
        if (headByRowNum != null && headByRowNum.size() > 0) {
            for (int i = 0; i < headByRowNum.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(getCellFontStyle(style, headByRowNum.get(i)));
                cell.setCellValue(headByRowNum.get(i));
            }
        }
    }
    
    private CellStyle getCellFontStyle(CellStyle style, String value) {
        CellStyle curr = style;
        if (curr == null) {
            curr = this.getCurrentHeadCellStyle();
        }
        
        if (value != null && value.startsWith("*")) {
            CellStyle newStyle = this.workbook.createCellStyle();
            newStyle.cloneStyleFrom(curr);
            Font font = this.workbook.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setColor(IndexedColors.RED.getIndex());
            newStyle.setFont(font);
            curr = newStyle;
        }
        return curr;
    }
    
    private CellStyle getFirstHeadCellStyle() {
        if (this.sheetStyle != null
            && (sheetStyle.getFirstHeadFont() != null || sheetStyle.getFirstHeadBGColor() != null || sheetStyle
                .getFirstHeadAlignment() != null)) {
            CellStyle headStyle = this.workbook.createCellStyle();
            headStyle.cloneStyleFrom(buildDefaultCellStyle());
            if (sheetStyle.getFirstHeadFont() != null) {
                headStyle.setFont(wrapFont(sheetStyle.getFirstHeadFont()));
            }
            if (sheetStyle.getFirstHeadBGColor() != null) {
                headStyle.setFillForegroundColor(sheetStyle.getFirstHeadBGColor().getIndex());
            }
            if (sheetStyle.getFirstHeadAlignment() != null) {
                headStyle.setAlignment(sheetStyle.getFirstHeadAlignment());
            }
            return headStyle;
        }
        return null;
    }
    
    private void buildSheetStyle(SheetStyle style) {
        this.sheetStyle = style;
        CellStyle headStyle = buildDefaultCellStyle();
        this.currentHeadCellStyle = headStyle;

        CellStyle contentStyle = buildeDefaultContentStyle();
        this.currentContentCellStyle = contentStyle;
        if (sheetStyle != null) {
            if (sheetStyle.getHeadFont() != null) {
                currentHeadCellStyle.setFont(wrapFont(sheetStyle.getHeadFont()));
            }
            if (sheetStyle.getHeadBGColor() != null) {
                currentHeadCellStyle.setFillForegroundColor(sheetStyle.getHeadBGColor().getIndex());
            }
            
            if (sheetStyle.getContentFont() != null) {
                currentContentCellStyle.setFont(wrapFont(sheetStyle.getContentFont()));
            }
            if (sheetStyle.getContentBGColor() != null) {
                currentContentCellStyle.setFillForegroundColor(sheetStyle.getContentBGColor().getIndex());
            }
            if(sheetStyle.isBodyText()){
                currentContentCellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("text"));
            }
        }
    }
    
    private Font wrapFont(com.mustr.common.excel.component.Font font) {
        Font newfont = this.workbook.createFont();
        if (font.getFontName() != null) {
            newfont.setFontName(font.getFontName());
        }
        if (font.getFontHeightInPoints() != null) {
            newfont.setFontHeightInPoints(font.getFontHeightInPoints());
        }
        if (font.getBoldweight() != null) {
            newfont.setBold(true);
        }
        if (font.getColor() != null) {
            newfont.setColor(font.getColor().getIndex());
        }
        return newfont;
    }
    
    @Override
    public Sheet getCurrentSheet() {
        return this.currentSheet;
    }

    @Override
    public CellStyle getCurrentHeadCellStyle() {
        return this.currentHeadCellStyle == null ? this.defaultCellStyle : this.currentHeadCellStyle;
    }

    @Override
    public CellStyle getCurrentContentStyle() {
        return this.currentContentCellStyle;
    }

    @Override
    public Workbook getWorkbook() {
        return this.workbook;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.outputStream;
    }

}
