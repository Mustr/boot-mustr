package com.mustr.common.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.mustr.common.entity.UserBean;

@Mapper
public interface UserDao {
  
    public Long saveUser(UserBean user);
    
    public int deleteUser(long id);
    
    public int updateUser(UserBean user);
    
    public UserBean getUserById(long id);
    
    public UserBean getUserByUsername(String username);
    
    public Page<UserBean> getUsers(Map<String, Object> params);
    
}
