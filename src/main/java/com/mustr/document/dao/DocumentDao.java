package com.mustr.document.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.mustr.document.entity.DocumentBean;

@Mapper
public interface DocumentDao {

    Long save(DocumentBean document);
    
    int update(DocumentBean document);
    
    Optional<DocumentBean> getById(long id);
    
    List<DocumentBean> getByProjectIdAndName(long projectId, String name);
    
    List<DocumentBean> getByProjectIdGroupByName(long projectId);
    
    int countByProjectIdAndName(long projectId, String name);
    
    int remove(long id);
}
