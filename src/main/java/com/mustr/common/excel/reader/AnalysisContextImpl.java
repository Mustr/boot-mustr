package com.mustr.common.excel.reader;

import java.io.InputStream;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;

public class AnalysisContextImpl implements AnalysisContext {

    private Sheet currentSheet;

    private ExcelTypeEnum excelType;

    private InputStream inputStream;

    private AnalysisEventListener eventListener;

    private Integer currentRowNum;

    private boolean trim;

    private Object currentRowAnalysisResult;

    public Object getCurrentRowAnalysisResult() {
        return currentRowAnalysisResult;
    }

    public void setCurrentRowAnalysisResult(Object currentRowAnalysisResult) {
        this.currentRowAnalysisResult = currentRowAnalysisResult;
    }

    public AnalysisContextImpl(InputStream inputStream, ExcelTypeEnum excelTypeEnum, AnalysisEventListener listener,
        boolean trim) {
        this.eventListener = listener;
        this.inputStream = inputStream;
        this.excelType = excelTypeEnum;
        this.trim = trim;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public AnalysisEventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(AnalysisEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public Integer getCurrentRowNum() {
        return this.currentRowNum;
    }

    public void setCurrentRowNum(Integer row) {
        this.currentRowNum = row;
    }

    public boolean trim() {
        return this.trim;
    }
}
