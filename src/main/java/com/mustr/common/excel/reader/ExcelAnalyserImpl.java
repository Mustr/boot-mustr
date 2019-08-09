package com.mustr.common.excel.reader;

import java.io.InputStream;
import java.util.List;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;

public class ExcelAnalyserImpl implements ExcelAnalyser {

    private AnalysisContext analysisContext;

    private Analyser analyser;

    private Analyser getAnalyser() {
        if (analyser == null) {
            if (ExcelTypeEnum.XLS.equals(analysisContext.getExcelType())) {
                this.analyser = new XLSAnalyser(analysisContext);
            } else if (ExcelTypeEnum.XLSX.equals(analysisContext.getExcelType())){
                this.analyser = new XLSXAnalyser(analysisContext);
            } else {
                throw new UnsupportedOperationException("��֧�ָ����͵�Excel�ļ���ʽ������");
            }
        }
        return this.analyser;
    }

    @Override
    public void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, AnalysisEventListener eventListener,
        boolean trim) {
        analysisContext = new AnalysisContextImpl(inputStream, excelTypeEnum,
            eventListener, trim);
    }
    
    public void analysis(Sheet sheetParam) {
        analysisContext.setCurrentSheet(sheetParam);
        analysis();
    }

    public void analysis() {
        Analyser analyser = getAnalyser();
        appendListeners(analyser);
        analyser.execute();

        analysisContext.getEventListener().doAfterAllAnalysed(analysisContext);
    }

    public List<Sheet> getSheets() {
        Analyser saxAnalyser = getAnalyser();
        return saxAnalyser.getSheets();
    }

    private void appendListeners(Analyser analyser) {
        if (analysisContext.getEventListener() != null) {
            analyser.appendLister("user_define_listener", analysisContext.getEventListener());
        }
    }
}
