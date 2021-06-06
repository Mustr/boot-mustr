package com.mustr.document.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mustr.document.entity.ProjectBean;

@Mapper
public interface ProjectDao {
    
    public List<ProjectBean> getProjects(Map<String, Object> params);

    public ProjectBean getById(long id);
    
    public Long save(ProjectBean project);
    
    public int update(ProjectBean project);
    
    public int remove(long id);
}
