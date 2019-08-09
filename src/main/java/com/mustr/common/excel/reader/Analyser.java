package com.mustr.common.excel.reader;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.mustr.common.excel.component.Sheet;

public abstract class Analyser implements AnalysisEventRegisterCenter{
    protected AnalysisContext analysisContext;
    
    protected boolean analyAllSheet = false;
    
    protected Workbook workbook = null;
    
    public Analyser(AnalysisContext analysisContext) {
        this.analysisContext = analysisContext;
    }
    
    private LinkedHashMap<String, AnalysisEventListener> listeners = new LinkedHashMap<String, AnalysisEventListener>();

    /**
     * 执行读取操作
     */
    public abstract void execute();
    
    /**
     * 获取所有的sheet
     * @return
     */
    public abstract List<Sheet> getSheets();

    public void appendLister(String name, AnalysisEventListener listener) {
        if (!listeners.containsKey(name)) {
            listeners.put(name, listener);
        }
    }

    public void cleanAllListeners() {
        listeners = new LinkedHashMap<String, AnalysisEventListener>();
    }

    public void notifyListeners(OneRowAnalysisFinishEvent event) {
        analysisContext.setCurrentRowAnalysisResult(event.getData());
        for (Map.Entry<String, AnalysisEventListener> entry : listeners.entrySet()) {
            entry.getValue().invoke(analysisContext.getCurrentRowAnalysisResult(), analysisContext);
        }
    }
    
    public void doExecute() {
        if (analysisContext.getCurrentSheet() == null) {
            this.analyAllSheet = true;
        }
        
        int sheetNum = workbook.getNumberOfSheets();
        if (sheetNum > 0) {
            for (int i = 0; i < sheetNum; i++) {
                if (!analyAllSheet) {
                    int sheetNo = analysisContext.getCurrentSheet().getSheetNo();
                    if (sheetNo != i) {
                        continue;
                    }
                }
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(i);
                analysisContext.setCurrentSheet(new Sheet(i, sheet.getSheetName()));
                for (Row row : sheet) {
                    List<String> data = new ArrayList<String>();
                    analysisContext.setCurrentRowNum(row.getRowNum() + 1);
                    short lastCellNum = row.getLastCellNum();
                    if (lastCellNum <= 0) {
                        continue;
                    }
                    
                    for (short j = 0; j < lastCellNum; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            data.add(null);
                        } else {
                            data.add(convertVal(cell));
                        }
                    }
                   
                    if (!data.isEmpty() && isNotContentEmpty(data)) {
                        notifyListeners(new OneRowAnalysisFinishEvent(data));
                    } else {
                        data = null; //GC
                    }
                }
            }
        }

    }
    
    private boolean isNotContentEmpty(List<String> datas) {
        for (String data : datas) {
            if (StringUtils.isNotBlank(data)) {
                return true;
            }
        }
        return false;
    }
    
    private static String formatDateString(Date date) {
        String dateString = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dateString = df.format(date);
        }
        return dateString;
    }
    
    protected String convertVal(Cell cell) {
        String val = "";
        switch (cell.getCellTypeEnum()) {
        case STRING:// 字符串类型
            if (analysisContext.trim()) {
                val = String.valueOf(cell.getStringCellValue().trim());
            } else {
                val = String.valueOf(cell.getStringCellValue());
            }
            break;
        case NUMERIC:// 数值类型
            if (DateUtil.isCellDateFormatted(cell)) {// 如果是日期
                val = formatDateString(DateUtil.getJavaDate(cell.getNumericCellValue()));
            } else {// 非日期
                DecimalFormat df = new DecimalFormat("#.#");
                double value = cell.getNumericCellValue();
                String suffix = StringUtils.substringAfter(df.format(value), ".");
                long valueSuffix = 0;
                if (StringUtils.isNotBlank(suffix) && StringUtils.isNotBlank(suffix.trim())) {
                    valueSuffix = Long.parseLong(suffix.trim());
                }

                if (valueSuffix == 0) {
                    val = String.valueOf((long) value);
                } else {
                    val = String.valueOf(value);
                }
            }
            break;
        case BOOLEAN:// boolean类型
            val = String.valueOf(cell.getBooleanCellValue());
            break;
        case FORMULA:// 表达式类型
            try {
                if (DateUtil.isCellDateFormatted(cell)) {
                    val = formatDateString(cell.getDateCellValue());
                } else {
                    val = String.valueOf(cell.getNumericCellValue());
                }
            } catch (Exception e) {
                val = cell.getStringCellValue();
            }

            break;
        case BLANK:// 空类型
            val = null;
            break;
        case ERROR:// 异常类型
            val = String.valueOf(cell.getErrorCellValue());
            break;
        default:
            val = cell.getStringCellValue();
        }
        return val;
    }

    protected List<Sheet> handlerSheets() {
        int sheetNum = workbook.getNumberOfSheets();
        List<Sheet> sheets = new ArrayList<Sheet>();
        if (sheetNum > 0) {
            for (int i = 0; i < sheetNum; i++) {
                org.apache.poi.ss.usermodel.Sheet sheetAt = workbook.getSheetAt(i);
                sheets.add(new Sheet(i, sheetAt.getSheetName()));
            }
        }
        return sheets;
    }
}
