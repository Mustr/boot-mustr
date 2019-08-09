package com.mustr.common.excel.writer;

import java.io.OutputStream;
import java.util.List;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;

/**
 * excel写数据的处理类
 * @author mustr
 */
public interface ExcelWriterBuilder {

    /**
     * 初始化
     * @param outputStream
     * @param typeEnum
     */
    public void init(OutputStream outputStream, ExcelTypeEnum typeEnum);
    
    
    /**
     * 向excel中添加数据
     * @param data
     * @param sheetParam
     */
    public void addData(List<List<Object>> data, Sheet sheetParam);
    
    /**
     * 写出数据并关闭资源
     */
    public void finish();
}
