package com.mustr.oa.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.mustr.common.entity.Tree;
import com.mustr.oa.entity.DeptBean;

/**
 * 部门业务管理类
 * @author mustr
 */

@Transactional
public interface DeptService {

    /**
     * 获取指定的所有的部门
     * @param params
     * @return
     */
    public List<DeptBean> getAllDepts(Map<String, Object> params);
    
    /**
     * 根据id获取指定的部门
     * @param id
     * @return
     */
    public DeptBean getDeptById(Long id);
    
    /**
     * 获取部门树
     * @return
     */
    public Tree<DeptBean> getDeptTree(Map<String, Object> params);
    
    /**
     * 保存部门
     * @param dept
     * @return
     */
    public long saveDept(DeptBean dept);
    
    /**
     * 删除部门
     * @param id
     * @return
     */
    public boolean deleteDept(long id);
    
    /**
     * 更新部门
     * @param dept
     * @return
     */
    public boolean upateDept(DeptBean dept);
}
