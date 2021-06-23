package com.mustr.document.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mustr.document.entity.DocumentBean;

/**
 * 文档管理业务接口
 * @author chenxj
 * @Date 2021-6-7
 *
 */
public interface DocumentService {

    /**
     * 创建文档
     * @param file        文件 
     * @param userId      上传者
     * @param projectId   所属项目
     * @return
     */
    Long createDocument(MultipartFile file, Long userId, long projectId);
    
    
    /**
     * 获取指定项目下的文档
     * @param projectId
     * @return
     */
    List<DocumentBean> getByProjectId(long projectId);
    
    
    /**
     * 获取指定文档的记录
     * @param projectId
     * @param name
     * @return
     */
    List<DocumentBean> getByProjectIdAndName(long projectId, String name);
    
    /**
     * 根据id获取
     * @param id
     * @return
     */
    DocumentBean getById(long id);
    
    
    /**
     * 删除指定的文档,连日志一并删除
     * @param id
     * @return
     */
    boolean deleteById(long id);
    
    /**
     * 删除记录文件
     * @param id
     * @return
     */
    boolean deleteLog(long id);
    
    /**
     * 更新
     * @param bean
     * @return
     */
    boolean update(DocumentBean bean);
    
    /**
     * 发送提示消息
     * @param id
     * @return 0正常 1文件不存在  2没有配置webhook
     */
    int sendMsg(long id);
}
