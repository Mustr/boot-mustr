package com.mustr.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mustr.common.config.OssProperties;
import com.mustr.common.dao.FileDao;
import com.mustr.common.entity.FileBean;
import com.mustr.common.service.FileService;
import com.mustr.utils.ContentTypes;
import com.mustr.utils.IdGenerateUtil;
import com.mustr.utils.MediaType;
import com.mustr.utils.ResourceTypeUtil;

import cn.hutool.core.io.FileUtil;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author chenxj
 * @Date 2021-6-7
 *
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileDao fileDao;
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private OssProperties properites;
    @Autowired
    private LibreofficeConvert libreofficeConvert;
    
    
    @Override
    @Transactional
    public FileBean upload(MultipartFile file, String filename) {
        if (file == null) {
            throw new IllegalArgumentException("file is blank!");
        }
        String bucketName = getBucketName();
        try (InputStream stream = file.getInputStream();) {
            String contentType = getContentType(file.getOriginalFilename() ,file.getContentType());
            
            if (StringUtils.isBlank(filename)) {
                filename = file.getOriginalFilename();
            }
            String extName = FileUtil.extName(filename);
            
            long nextId = IdGenerateUtil.generateId();
            
            
            String toDay = LocalDate.now().toString().replace("-", "");
            
            String objectName = toDay + "/" + nextId + "." + extName;
            
            //long start = System.currentTimeMillis();
            //存文件
            PutObjectArgs args = PutObjectArgs.builder()
            .bucket(bucketName)
            .object(objectName)
            .stream(stream, file.getSize(), -1)
            .contentType(contentType)
            .build();
            minioClient.putObject(args);
            
            //System.out.println("上传耗时：" + (System.currentTimeMillis() - start));
            
            FileBean entity = FileBean.builder()
            .bucket(bucketName)
            .contentType(contentType)
            .createTime(LocalDateTime.now())
            .objectName(objectName)
            .size(file.getSize())
            .suffix(extName)
            .build();
            
            entity.setId(nextId);
            entity.setName(filename);
            
            fileDao.save(entity);
            return entity;
        } catch (Exception e) {
            log.error("{} ->> uploadFile failed", file.getOriginalFilename());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public FileBean upload(File file, String filename) {
        
        if (file == null) {
            throw new IllegalArgumentException("file is blank!");
        }
        String bucketName = getBucketName();
        try (InputStream stream = new FileInputStream(file)) {
            String contentType = getContentType(file.getName(), null);
            
            if (StringUtils.isBlank(filename)) {
                filename = file.getName();
            }
            String extName = FileUtil.extName(filename);
            
            long nextId = IdGenerateUtil.generateId();
            
            String toDay = LocalDate.now().toString().replace("-", "");
            
            String objectName = toDay + "/" + nextId + "." + extName;
            
            //long start = System.currentTimeMillis();
            //存文件
            PutObjectArgs args = PutObjectArgs.builder()
            .bucket(bucketName)
            .object(objectName)
            .stream(stream, file.length(), -1)
            .contentType(contentType)
            .build();
            minioClient.putObject(args);
            
            //System.out.println("上传耗时：" + (System.currentTimeMillis() - start));
            
            FileBean entity = FileBean.builder()
            .bucket(bucketName)
            .contentType(contentType)
            .createTime(LocalDateTime.now())
            .objectName(objectName)
            .size(file.length())
            .suffix(extName)
            .build();
            
            entity.setId(nextId);
            entity.setName(filename);
            
            fileDao.save(entity);
            return entity;
        } catch (Exception e) {
            log.error("{} ->> uploadFile failed", file.getName());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteFile(long id) {
        Optional<FileBean> result = fileDao.getById(id);
        result.ifPresent(entity -> {
            
            RemoveObjectArgs args = RemoveObjectArgs.builder().bucket(entity.getBucket())
            .object(entity.getObjectName())
            .build();
            
            try {
                minioClient.removeObject(args);
            } catch (Exception e) {
                log.error("{} ->> delete failed", id);
            }
            
            fileDao.remove(id);
        });
        return result.isPresent();
    }

    @Override
    public FileBean getById(long id) {
        Optional<FileBean> result = fileDao.getById(id);
        return result.orElse(null);
    }

    @Override
    public Long convertOfficeFile(long id) {
        Optional<FileBean> result = fileDao.getById(id);
        if (!result.isPresent()) {
            return null;
        }
        FileBean fileBean = result.get();
        
        
        String suffix = fileBean.getSuffix();
        MediaType mediaType = MediaType.getMediaType(suffix);
        if (!MediaType.TEXT.equals(mediaType)) {
            return null;
        }
        
        File tempFile = null;
        File targetFile = null;
        try {
            tempFile = File.createTempFile("textTemp", "." + suffix);
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                .bucket(fileBean.getBucket())
                .object(fileBean.getObjectName())
                .filename(tempFile.getAbsolutePath())
                .build();
            
            //下载源文件
            minioClient.downloadObject(args);
            
            targetFile = File.createTempFile("wordPdfTemp", ".pdf");
            
            libreofficeConvert.convert(tempFile, targetFile);
            
            String pdfName = StringUtils.substringBeforeLast(fileBean.getName(), ".") + ".pdf";
           
            //上传文件
            return upload(targetFile, pdfName).getId();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.del(tempFile);
            FileUtil.del(targetFile);
        }
        return null;
    }

    @Override
    public InputStream getFileStream(long id) {
        Optional<FileBean> result = fileDao.getById(id);
        if (!result.isPresent()) {
            return null;
        }
        return getFileStream(result.get().getBucket(), result.get().getObjectName());
    }

    @Override
    public InputStream getFileStream(String bucket, String objectName) {
        if (StringUtils.isAnyBlank(bucket, objectName)) {
            return null;
        }
        GetObjectArgs args = GetObjectArgs.builder()
        .bucket(bucket)
        .object(objectName)
        .build();
        
        try {
           return minioClient.getObject(args);
        } catch (Exception igonre) {
        }
        return null;
    }

    private String getBucketName() {
        return properites.getBucket();
    }
    
    /**
     * 获取MultipartFile contentType
     * @param file
     * @return
     */
    private String getContentType(String filename, String contentType) {
        if (StringUtils.isBlank(contentType) || ContentTypes.DEFAULT_MIMETYPE.equalsIgnoreCase(contentType)) {
            return ResourceTypeUtil.getContentTypeByName(filename);
        }
        return contentType;
    }
}
