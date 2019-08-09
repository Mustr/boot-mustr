package com.mustr.common.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;
import com.mustr.common.excel.reader.AnalysisContext;
import com.mustr.common.excel.reader.AnalysisEventListener;
import com.mustr.common.excel.reader.ExcelAnalyser;
import com.mustr.common.excel.reader.ExcelAnalyserImpl;

/**
 * excel文件的读取
 * @author mustr
 */
public class ExcelReader {

    private ExcelAnalyser analyser = new ExcelAnalyserImpl();

    /**
     * 初始化Excel读取器
     * @param in 输入流
     * @param excelTypeEnum excel类型
     * @param eventListener 读取数据监听类
     */
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, AnalysisEventListener eventListener) {
        this(in, excelTypeEnum, eventListener, true);
    }

    /**
     * 初始化Excel读取器
     * @param in 输入流
     * @param excelTypeEnum  excel类型
     * @param eventListener 读取数据监听类
     * @param trim 是否去掉左右空格
     */
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, AnalysisEventListener eventListener, boolean trim) {
        validateParam(in, excelTypeEnum, eventListener);
        analyser.init(in, excelTypeEnum, eventListener, trim);
    }

    /**
     * 开始读取
     */
    public void read() {
        analyser.analysis();
    }

    /**
     * 读取指定的Sheet
     * @param sheet
     */
    public void read(Sheet sheet) {
        analyser.analysis(sheet);
    }

    /**
     * 获取所有的sheet
     * @return
     */
    public List<Sheet> getSheets() {
        return analyser.getSheets();
    }

    private void validateParam(InputStream in, ExcelTypeEnum excelTypeEnum, AnalysisEventListener eventListener) {
        if (eventListener == null) {
            throw new IllegalArgumentException("AnalysisEventListener can not null");
        } else if (in == null) {
            throw new IllegalArgumentException("InputStream can not null");
        } else if (excelTypeEnum == null) {
            throw new IllegalArgumentException("excelTypeEnum can not null");
        }
    }
    
    /**
     * 例子（example）
     * @param args
     */
    public static void main(String[] args) {
        test();
    }
    
    public static void test() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("D:/excelDemo.xls");
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, new AnalysisEventListener() {
                @Override
                public void invoke(Object object, AnalysisContext context) {
                    System.out.println("当前sheet:" + context.getCurrentSheet() + " 当前行："
                        + context.getCurrentRowNum() + " data:" + object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.out.println("end");
                }
            });
           
            // List<Sheet> sheets = reader.getSheets();
           // System.out.println(sheets);
           // Sheet sheet = new Sheet(1);
           // reader.read(sheet);
            
            reader.read();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
