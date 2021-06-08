package com.mustr.document.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mustr.common.entity.FileBean;
import com.mustr.common.service.FileService;
import com.mustr.document.dao.DocumentDao;
import com.mustr.document.entity.DocumentBean;
import com.mustr.document.entity.DocumentBean.DocumentBeanBuilder;
import com.mustr.document.service.DocumentService;

/**
 * 
 * @author chenxj
 * @Date 2021-6-7
 *
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private FileService fileService;
    @Autowired
    private DocumentDao documentDao;
    
    @Override
    @Transactional
    public Long createDocument(MultipartFile file, Long userId, long projectId) {
        
        FileBean upload = fileService.upload(file, null);
        if (upload == null) {
            throw new RuntimeException("上传文件失败");
        }
        
        Long convertOfficeFile = fileService.convertOfficeFile(upload.getId());
        
        String name = upload.getName();
        
        DocumentBeanBuilder builder = DocumentBean.builder()
        .createTime(LocalDateTime.now())
        .fileId(upload.getId())
        .creatorId(userId)
        .projectId(projectId)
        .size(upload.getSize())
        .status(DocumentBean.STATUS_NOT);
        
        if (convertOfficeFile != null) {
            builder.convertFileId(convertOfficeFile).status(DocumentBean.STATUS_NORMAL);
        }
        DocumentBean entity = builder.build();
        entity.setName(name);
        Long save = documentDao.save(entity);
        
        //删除记录
        int count = documentDao.countByProjectIdAndName(projectId, name);
        if (count > 10) {
            List<DocumentBean> list = documentDao.getByProjectIdAndName(projectId, name);
            documentDao.remove(list.get(list.size() - 1).getId());
        }
        
        return save;
    }

    @Override
    public List<DocumentBean> getByProjectId(long projectId) {
        List<DocumentBean> byProjectIdGroupByName = documentDao.getByProjectIdGroupByName(projectId);
        System.out.println(byProjectIdGroupByName.size());
        return byProjectIdGroupByName;
    }

    @Override
    public List<DocumentBean> getByProjectIdAndName(long projectId, String name) {
        return documentDao.getByProjectIdAndName(projectId, name);
    }

    @Override
    public DocumentBean getById(long id) {
        return documentDao.getById(id).orElse(null);
    }

    @Override
    public boolean deleteById(long id) {
        return documentDao.remove(id) > 0;
    }

    @Override
    public boolean update(DocumentBean bean) {
        if (bean.getId() != null) {
           return documentDao.update(bean) > 0;
        }
        return false;
    }

}
