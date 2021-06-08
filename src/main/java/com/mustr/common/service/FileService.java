package com.mustr.common.service;

import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.mustr.common.entity.FileBean;

/**
 * 文件处理业务接口
 * @author chenxj
 * @Date 2021-6-7
 *
 */
public interface FileService {

    /**
     * 上传文件
     * @param file  文件
     * @param filename  文件名，null自动获取文件名
     * @return
     */
    FileBean upload(MultipartFile file, String filename);
    
    /**
     * 上传文件
     * @param file
     * @param filename
     * @return
     */
    FileBean upload(File file, String filename);
    
    /**
     * 删除指定的文件
     * @param id
     * @return
     */
    boolean deleteFile(long id);
    
    /**
     * 获取指定的文件信息
     * @param id
     * @return
     */
    FileBean getById(long id);
    
    /**
     * 转换指定文件的格式
     * @param id    word,execl,ppt --> pdf
     * @return 转换后文件的id
     */
    Long convertOfficeFile(long id);
    
    /**
     * 获取指定文件的文件流
     * @param id
     * @return
     */
    InputStream getFileStream(long id);
    
    /**
     * 获取指定文件的文件流
     * @param bucket
     * @param objectName
     * @return
     */
    InputStream getFileStream(String bucket, String objectName);
}
