package com.mustr.common.service.impl;

import java.io.File;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * libreoffice online 文件转换
 * @author chenxj
 * @Date 2021-6-7
 *
 */
@Service
public class LibreofficeConvert {

    @Autowired
    private DocumentConverter documentConverter;
    
    /**
     * 转换文件
     * @param srcFile  源文件
     * @param targetFile 转换后的文件
     * @throws Exception
     */
    public void convert(File srcFile, File targetFile) throws Exception {
        documentConverter.convert(srcFile)
        .to(targetFile)
        .as(DefaultDocumentFormatRegistry.PDF)
        .execute();
    }
    
}
