package com.mustr.common.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mustr.common.dao.UserDao;
import com.mustr.common.entity.UserBean;
import com.mustr.common.service.ServiceBase;
import com.mustr.common.service.UserService;
import com.mustr.oa.dao.DeptDao;
import com.mustr.oa.entity.DeptBean;

@Service
public class UserServiceImpl extends ServiceBase implements UserService {

    @Autowired
    UserDao userDao;
    
    @Autowired
    DeptDao deptDao;
    
    @Override
    public Page<UserBean> getUsers(Map<String, Object> params, int pageNum, int pageSize) {
        if (pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize, true);
        }
        return userDao.getUsers(params);
    }

    @Override
    public UserBean getUserById(long id) {
        UserBean user = userDao.getUserById(id);
        if (user != null && user.getDeptId() != null) {
            DeptBean dept = deptDao.getDeptById(user.getDeptId());
            if (dept != null) {
                user.setDeptName(dept.getName());
            }
        }
        return user;
    }

    @Override
    public UserBean getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean updateUser(UserBean bean) {
        if (bean.getId() != null) {
            bean.setLastModifiedTime(new Date());
            return userDao.updateUser(bean) > 0;
        }
        return false;
    }

    @Override
    public boolean deleteUser(long id) {
        return userDao.deleteUser(id) > 0;
    }

    @Override
    public Long createUser(UserBean bean) {
        if (bean != null) {
            Date currDate = new Date();
            bean.setCreateTime(currDate);
            bean.setLastModifiedTime(currDate);
            bean.setPassword(new BCryptPasswordEncoder().encode(bean.getPassword()));
            return userDao.saveUser(bean);
        }
        return null;
    }

}
