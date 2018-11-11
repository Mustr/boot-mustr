package com.mustr.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mustr.common.dao.RoleDao;
import com.mustr.common.dao.RolePermissionsDao;
import com.mustr.common.entity.RoleBean;
import com.mustr.common.service.RoleService;
import com.mustr.common.service.ServiceBase;

@Service
public class RoleServiceImpl extends ServiceBase implements RoleService {

    @Autowired
    RoleDao roleDao;
    
    @Autowired
    RolePermissionsDao rolePermissionsDao;
    
    @Override
    public Long createRole(RoleBean role) {
        if (role != null) {
            roleDao.saveRole(role);
            Long roleId =  role.getId();
            List<String> permissionses = role.getPermissionses();
            if (permissionses != null && !permissionses.isEmpty() && roleId != null) {
                rolePermissionsDao.batchSaveRolePermissions(roleId, permissionses);
            }
            return roleId;
        }
        return null;
    }

    @Override
    public RoleBean getRoleById(long id) {
        RoleBean roleBean = roleDao.getRoleById(id);
        if (roleBean != null) {
            roleBean.setPermissionses(rolePermissionsDao.getPermissionsByRoleId(id));
        }
        return roleBean;
    }

    @Override
    public boolean deleteRole(long id) {
        int deleteRole = roleDao.deleteRole(id);
        if (deleteRole > 0) {
            rolePermissionsDao.deleteRoleAllPermissions(id);
        }
        return deleteRole > 0;
    }

    @Override
    public boolean updateRole(RoleBean role) {
        if (role != null && role.getId() != null) {
            role.setLastModifiedTime(new Date());
            int updateRole = roleDao.updateRole(role);
            List<String> permissionses = role.getPermissionses();
            rolePermissionsDao.deleteRoleAllPermissions(role.getId());
            rolePermissionsDao.batchSaveRolePermissions(role.getId(), permissionses);
            return updateRole > 0;
        }
        return false;
    }

    @Override
    public List<RoleBean> getRoles(Map<String, Object> cond) {
        List<RoleBean> roles = roleDao.getRoles(cond);
        if (roles != null && !roles.isEmpty()) {
            for (RoleBean roleBean : roles) {
                roleBean.setPermissionses(rolePermissionsDao.getPermissionsByRoleId(roleBean.getId()));
            }
        }
        return roles;
    }

}
