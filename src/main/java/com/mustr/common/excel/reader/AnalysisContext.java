package com.mustr.common.excel.reader;

import java.io.InputStream;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;

public interface AnalysisContext {

    /**
     * 获取当前sheet
     * @return
     */
    Sheet getCurrentSheet();

    /**
     * 设置当前sheet
     * @param sheet
     */
    void setCurrentSheet(Sheet sheet);

    /**
     * 获取Excel格式
     * @return
     */
    ExcelTypeEnum getExcelType();

    /**
     * 获取输入流
     * @return
     */
    InputStream getInputStream();

    /**
     * 获取监听类
     * @return
     */
    AnalysisEventListener getEventListener();

    /**
     * 获取当前行号
     * @return
     */
    Integer getCurrentRowNum();

    /**
     * 设置当前行号
     * @param row
     */
    void setCurrentRowNum(Integer row);

    /**
     * 是否去空格
     * @return
     */
    boolean trim();

    /**
     * 设置数据
     * @param result
     */
    void setCurrentRowAnalysisResult(Object result);

    /**
     * 得到数据
     * @return
     */
    Object getCurrentRowAnalysisResult();
}
