package com.mustr.common.excel.writer;

import java.io.OutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel页面的生产环境接口
 * @author mustr
 */
public interface GenerateContext {

    /**
     * 获取当前sheet
     * 
     * @return
     */
    Sheet getCurrentSheet();

    /**
     * 获取当前表头样式
     * 
     * @return
     */
    CellStyle getCurrentHeadCellStyle();

    /**
     * 获取当前内容样式
     * 
     * @return
     */
    CellStyle getCurrentContentStyle();

    /**
     * 获取workbook
     * 
     * @return
     */
    Workbook getWorkbook();

    /**
     * 获取输出流
     * 
     * @return
     */
    OutputStream getOutputStream();

    /**
     * 处理sheet
     * 
     * @param sheet
     */
    void buildCurrentSheet(com.mustr.common.excel.component.Sheet sheet);

}
