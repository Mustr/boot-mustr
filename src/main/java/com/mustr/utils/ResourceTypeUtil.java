package com.mustr.utils;

import java.io.File;
import java.io.InputStream;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IORuntimeException;

/**
 * 资源文件类型获取工具
 * @author chenxj
 * @Date 2021-6-7
 *
 */
public class ResourceTypeUtil {
    
    /**
     * 根据文件流的头部信息获得文件类型
     *
     * @param in {@link InputStream}
     * @param extName 文件的扩展名，辅助判断
     * @return 类型，文件的扩展名，未找到为<code>null</code>
     * @param in
     * @param extName
     * @return
     */
    public static String getType(InputStream in, String extName) throws IORuntimeException{
        String typeName = FileTypeUtil.getType(in);
        if (null == typeName) {
            // 未成功识别类型，扩展名辅助识别
            typeName = extName;
        } else if ("xls".equals(typeName)) {
            // xls、doc、msi的头一样，使用扩展名辅助判断
            if ("doc".equalsIgnoreCase(extName)) {
                typeName = "doc";
            } else if ("msi".equalsIgnoreCase(extName)) {
                typeName = "msi";
            } else if ("pptx".equalsIgnoreCase(extName)) {
                typeName = "pptx";
            }
        } else if ("zip".equals(typeName)) {
            // zip可能为docx、xlsx、pptx、jar、war等格式，扩展名辅助判断
            if ("docx".equalsIgnoreCase(extName)) {
                typeName = "docx";
            } else if ("xlsx".equalsIgnoreCase(extName)) {
                typeName = "xlsx";
            } else if ("pptx".equalsIgnoreCase(extName)) {
                typeName = "pptx";
            } else if ("jar".equalsIgnoreCase(extName)) {
                typeName = "jar";
            } else if ("war".equalsIgnoreCase(extName)) {
                typeName = "war";
            }
        }
        
        return typeName;
    }
    
    /**
     * 获取文件的contentType
     * @param file  文件
     * @return
     */
    public static String getContentType(File file) {
        return ContentTypes.getInstance().getMimetype(file);
    }
    
    /**
     * 根据扩展名获取contentType
     * @param extName   文件的扩展名
     * @return
     */
    public static String getContentType(String extName) {
        return ContentTypes.getInstance().getMimetypeByExtName(extName);
    }
    
    /**
     * 更具文件名获取contentType
     * @param filename  文件名
     * @return
     */
    public static String getContentTypeByName(String filename) {
        return ContentTypes.getInstance().getMimetype(filename);
    }
    
}
