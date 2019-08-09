package com.mustr.common.excel.reader;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mustr.common.excel.component.Sheet;

public class XLSXAnalyser extends Analyser {
    public XLSXAnalyser(AnalysisContext analysisContext) {
        super(analysisContext);
        analysisContext.setCurrentRowNum(0);
        try {
            workbook = new XSSFWorkbook(analysisContext.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {
        super.doExecute();
    }

    @Override
    public List<Sheet> getSheets() {
        return super.handlerSheets();
    }

}
