package com.mustr.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mustr.common.entity.RoleBean;

@Mapper
public interface RoleDao {

    public int saveRole(RoleBean role);
    
    public int updateRole(RoleBean role);
    
    public int deleteRole(long id);
    
    public RoleBean getRoleById(long id);
    
    public List<RoleBean> getRolesByUser(long userId);
    
    public List<RoleBean> getRoles(Map<String, Object> cond);
}
