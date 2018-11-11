package com.mustr.common.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.mustr.common.entity.UserBean;

@Transactional
public interface UserService {

    /**
     * 获取用户
     * @param params -- 参数
     * @param pageNum -- 当前页
     * @param pageSize -- 条数
     * @return
     */
    public Page<UserBean> getUsers(Map<String, Object> params, int pageNum, int pageSize);
    
    /**
     * 根据用户id获取用户
     * @param id
     * @return
     */
    public UserBean getUserById(long id);
    
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    public UserBean getUserByUsername(String username);
    
    /**
     * 更新用户信息
     * @param bean
     * @return
     */
    public boolean updateUser(UserBean bean);
    
    /**
     * 删除用户
     * @param id
     * @return
     */
    public boolean deleteUser(long id);
    
    /**
     * 创建用户
     * @param bean
     * @return
     */
    public Long createUser(UserBean bean);
}
