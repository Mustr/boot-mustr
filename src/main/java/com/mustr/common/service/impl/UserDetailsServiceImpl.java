package com.mustr.common.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mustr.common.constant.PermissionsConstant;
import com.mustr.common.dao.RoleDao;
import com.mustr.common.dao.RolePermissionsDao;
import com.mustr.common.dao.UserDao;
import com.mustr.common.entity.RoleBean;
import com.mustr.common.entity.SecurityUser;
import com.mustr.common.entity.UserBean;
import com.mustr.common.utils.HttpContextUtils;
import com.mustr.common.utils.IPUtil;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;
    
    @Autowired
    RoleDao roleDao;
    
    @Autowired
    RolePermissionsDao rolePermissionsDao;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isNotBlank(username)) {
            // 从数据库中获取用户信息
            UserBean user = userDao.getUserByUsername(username);
            if (user != null) {
                // 获取角色
                List<RoleBean> roles = roleDao.getRolesByUser(user.getId());
                Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
                if (roles != null && !roles.isEmpty()) {
                    for (RoleBean role : roles) {
                        // 获取角色下的权限
                        List<String> permissionses = rolePermissionsDao.getPermissionsByRoleId(role.getId());
                        if (permissionses != null && !permissionses.isEmpty()) {
                            for (String permissions : permissionses) {
                                Set<String> allPermissions = PermissionsConstant.getAllPermissions(permissions);
                                for (String allPermission : allPermissions) {
                                    authorities.add(new SimpleGrantedAuthority(allPermission));
                                }
                            }
                        }
                    }
                }

                // 封装指定的user对象返回
                boolean enabled = user.getStatus() == null || user.getStatus() != -1;
                SecurityUser userDetails = new SecurityUser(user.getId(), user.getName(), username, user.getPassword(),
                        enabled, true, true, true, authorities);
                
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                if (request != null) {
                    userDetails.setIpAddr(IPUtil.getIPAddr(request));
                }
                
                return userDetails;
            }

        }
        return null;
    }

}
