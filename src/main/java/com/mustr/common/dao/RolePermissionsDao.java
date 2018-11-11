package com.mustr.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RolePermissionsDao {

    public List<String> getPermissionsByRoleId(long roleId);
    
    public int saveRolePermissions(long roleId, String permissions);
    
    public int deleteRolePermissions(long roleId, String permissions);
    
    public int deleteRoleAllPermissions(long roleId);
    
    public int batchSaveRolePermissions(@Param("roleId") long roleId, @Param("permissionses") List<String> permissionses);
}
