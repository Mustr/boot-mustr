package com.mustr.common.excel.reader;

import java.io.InputStream;
import java.util.List;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;

public interface ExcelAnalyser {

    void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, AnalysisEventListener eventListener,
              boolean trim);

    void analysis(Sheet sheetParam);


    void analysis();

   
    List<Sheet> getSheets();

}
