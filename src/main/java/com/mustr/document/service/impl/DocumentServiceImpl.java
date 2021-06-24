package com.mustr.document.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mustr.common.config.MustrProperties;
import com.mustr.common.entity.FileBean;
import com.mustr.common.service.FileService;
import com.mustr.document.dao.DocumentDao;
import com.mustr.document.dao.ProjectDao;
import com.mustr.document.entity.DocumentBean;
import com.mustr.document.entity.DocumentBean.DocumentBeanBuilder;
import com.mustr.document.entity.ProjectBean;
import com.mustr.document.service.DocumentService;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

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
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private MustrProperties mustrProperties;
    
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
            delete(list.get(list.size() - 1));
        }
        
        return save;
    }

    @Override
    public List<DocumentBean> getByProjectId(long projectId) {
        List<DocumentBean> result = documentDao.getByProjectIdGroupByName(projectId);
        return result.stream().map(this::wrap).collect(Collectors.toList());
    }

    @Override
    public List<DocumentBean> getByProjectIdAndName(long projectId, String name) {
        List<DocumentBean> result = documentDao.getByProjectIdAndName(projectId, name);
        return result.stream().map(this::wrap).collect(Collectors.toList());
    }

    @Override
    public DocumentBean getById(long id) {
        return documentDao.getById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        Optional<DocumentBean> restult = documentDao.getById(id);
        restult.ifPresent(entity -> {
            if (entity.getProjectId() != null && entity.getName() != null) {
                List<DocumentBean> result = documentDao.getByProjectIdAndName(entity.getProjectId(), entity.getName());
                if (CollectionUtils.isNotEmpty(result)) {
                    for (DocumentBean documentBean : result) {
                        delete(documentBean);
                    }
                }
            }
        });
        return restult.isPresent();
    }

    @Override
    @Transactional
    public boolean deleteLog(long id) {
        Optional<DocumentBean> result = documentDao.getById(id);
        result.ifPresent(this::delete);
        return result.isPresent();
    }
    
    private void delete(DocumentBean bean) {
        if (bean.getConvertFileId() != null) {
            fileService.deleteFile(bean.getConvertFileId());
        }
        if (bean.getFileId() != null) {
            fileService.deleteFile(bean.getFileId());
        }
        documentDao.remove(bean.getId());
    }

    @Override
    @Transactional
    public boolean update(DocumentBean bean) {
        if (bean.getId() != null) {
           return documentDao.update(bean) > 0;
        }
        return false;
    }

    private DocumentBean wrap(DocumentBean bean) {
        if (bean.getFileId() != null) {
            bean.setFileInfo(fileService.getById(bean.getFileId()));
        }
        return bean;
    }

    @Override
    public int sendMsg(long id) {
        Optional<DocumentBean> result = documentDao.getById(id);
        if (!result.isPresent()) {
            return 1;// 文件不存在
        }
        StringBuilder message = new StringBuilder();
        DocumentBean doc = result.get();

        String serverUrl = mustrProperties.getServerUrl();
        if (StringUtils.isNoneBlank(serverUrl)) {
            message.append("文件更新了！<font color=\"info\">(点击标题查看)</font>\n");
            //[这是一个链接](http://work.weixin.qq.com/api/doc)
            String url =  serverUrl + "/document/file/view/" + id;
            message.append(">文件:  [" + doc.getName() + "](" + url +")\n");
        } else {
            message.append("文件更新了！\n");
            message.append(">文件: <font color=\"info\">" + doc.getName() + "</font>\n");
        }

        Long projectId = doc.getProjectId();
        String webhook = null;
        if (projectId != null) {
            Optional<ProjectBean> pro = projectDao.getById(projectId);
            if (pro.isPresent()) {
                if (StringUtils.isNotBlank(pro.get().getWebhook())) {
                    webhook = pro.get().getWebhook();
                }

                if (pro.get().getParentId() != null) {
                    Optional<ProjectBean> parent = projectDao.getById(pro.get().getParentId());
                    if (parent.isPresent()) {
                        if (StringUtils.isBlank(webhook)) {
                            webhook = parent.get().getWebhook();
                        }
                        message.append(">位置:  <font color=\"comment\">" + parent.get().getName() + " >> "
                            + pro.get().getName() + "</font>\n");
                    }
                }

            }
        }

        if (StringUtils.isNotBlank(doc.getRemark())) {
            message.append(">备注:  <font color=\"comment\">" + doc.getRemark() + "</font>");
        }

        if (StringUtils.isBlank(webhook)) {
            return 2;// 没有配置webhook
        }

        Map<String, Object> msg = new HashedMap<>();
        msg.put("msgtype", "markdown");

        Map<String, String> content = new HashMap<>();
        content.put("content", message.toString());
        msg.put("markdown", content);

        HttpUtil.post(webhook, JSONUtil.toJsonStr(msg));

        return 0;
    }
}
