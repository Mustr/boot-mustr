package com.mustr.common.service;

import java.util.List;
import java.util.Map;

import com.mustr.common.entity.RoleBean;

public interface RoleService {
    
    /**
     * 创建角色
     * @param role
     * @return
     */
    public Long createRole(RoleBean role);
    
    /**
     * 根据id获取角色
     * @param id
     * @return
     */
    public RoleBean getRoleById(long id);
    
    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    public boolean deleteRole(long id);
    
    /**
     * 更新角色
     * @param role
     * @return
     */
    public boolean updateRole(RoleBean role);
    
    /**
     * 根据参数获取角色s
     * @param cond
     * @return
     */
    public List<RoleBean> getRoles(Map<String, Object> cond);

}
