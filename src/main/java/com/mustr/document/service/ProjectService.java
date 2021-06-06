package com.mustr.document.service;

import java.util.List;
import java.util.Map;

import com.mustr.common.entity.Tree;
import com.mustr.document.entity.ProjectBean;

/**
 * 项目业务接口
 * @author mustr
 * @date 2021-6-6
 *
 */
public interface ProjectService {

    /**
     * 获取指定的所有的项目
     * @param params
     * @return
     */
    public List<ProjectBean> getAllProjects(Map<String, Object> params);
    
    /**
     * 根据id获取指定的项目
     * @param id
     * @return
     */
    public ProjectBean getProjectById(Long id);
    
    /**
     * 获取项目树
     * @return
     */
    public Tree<ProjectBean> getProjectTree(Map<String, Object> params);
    
    /**
     * 保存项目
     * @param project
     * @return
     */
    public long saveProject(ProjectBean project);
    
    /**
     * 删除项目
     * @param id
     * @return
     */
    public boolean deleteProject(long id);
    
    /**
     * 更新项目
     * @param project
     * @return
     */
    public boolean upateProject(ProjectBean project);
    
    
}
