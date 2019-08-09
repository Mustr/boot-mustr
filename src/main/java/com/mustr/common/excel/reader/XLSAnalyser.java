package com.mustr.common.excel.reader;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mustr.common.excel.component.Sheet;

public class XLSAnalyser extends Analyser {

    public XLSAnalyser(AnalysisContext analysisContext) {
        super(analysisContext);
        analysisContext.setCurrentRowNum(0);

        try {
            workbook = new HSSFWorkbook(analysisContext.getInputStream());
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
