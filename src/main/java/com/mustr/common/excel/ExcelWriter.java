package com.mustr.common.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.mustr.common.excel.component.ExcelTypeEnum;
import com.mustr.common.excel.component.Sheet;
import com.mustr.common.excel.writer.ExcelWriterBuiderImpl;
import com.mustr.common.excel.writer.ExcelWriterBuilder;


/**
 * excel写操作类
 * <br/>1、包含自动合并单元格。
 * <br/>2、支持树Tree型结构的数据表头自动处理
 * 
 * @author mustr
 */
public class ExcelWriter {

    private ExcelWriterBuilder builder;

    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        builder = new ExcelWriterBuiderImpl();
        builder.init(outputStream, typeEnum);
    }

    /**
     * 写出数据到excel
     * @param data --每一个list<Object>就是一行数据。null数据表示要合并单元格的
     * @return
     */
    public ExcelWriter write(List<List<Object>> data) {
        Sheet sheet = new Sheet(1);
        sheet.setSheetName("sheet");
        builder.addData(data, sheet);
        return this;
    }
    
    /**
     * 写出指定的sheet，其中包含表头或表头树
     * @param sheetParam sheet参数对象
     * @return
     */
    public ExcelWriter write(Sheet sheetParam) {
        builder.addData(null, sheetParam);
        return this;
    }
    
    /**
     * 写出指定的数据到指定的sheet页面中
     * @param data  写出的数据
     * @param sheetParam  sheet参数对象
     * @return
     */
    public ExcelWriter write(List<List<Object>> data, Sheet sheetParam) {
        builder.addData(data, sheetParam);
        return this;
    }

    /**
     * 写出数据
     */
    public void finish() {
        builder.finish();
    }
    
  //deomo
    public static void main(String[] args) {
        String h1 = "课程代码, 课程名称(全称), 负责人, null, null, 精品课程, 使用教材, null, null, null, null, 授课年级";
        String h2 = "null, null, 教工号, 姓名, 任职日期（年月）, null, 教材名称（全称）, 出版社, 教材性质, null, 教材类型, null";
        String h3 = "null, null, null, null, null, null, null, null, 教育部规划教材, 自编教材, null, null";
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> head1 = new ArrayList<String>();
        addList(head1, h1);
        List<String> head2 = new ArrayList<String>();
        addList(head2, h2);
        List<String> head3 = new ArrayList<String>();
        addList(head3, h3);
        head.add(head1);
        head.add(head2);
        head.add(head3);
        try {
            OutputStream out = new FileOutputStream("D:/excelDemo.xls");
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLS);
            Sheet sheet1 = new Sheet(1);
            sheet1.setSheetName("第一个sheet");
            sheet1.setHead(head);
            writer.write(sheet1);
            writer.finish();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void addList(List<String> head1, String h) {
        String[] split = h.split(",");
        for (int i = 0; i < split.length; i++) {
            String value = split[i].trim();
            if ("null".equals(value)) {
                head1.add(null);
            } else {
                head1.add(value);
            }
        }
    }
}
