package com.mustr.common.service;

import java.util.Set;

import com.github.pagehelper.Page;
import com.mustr.common.entity.LogBean;

public interface LogService {

    /**
     * 创建日志
     * @param log
     * @return
     */
    public Long createLog(LogBean log);
    
    /**
     * 根据id获取日志
     * @param id
     * @return
     */
    public LogBean getLogById(long id);
    
    /**
     * 分页获取指定用户的操作日志
     * @param userId
     * @return
     */
    public Page<LogBean> getLogsByUserId(int pageNum, int pageSize, long userId);
    
    /**
     * 批量删除日志
     * @param ids
     * @return
     */
    public boolean batchDeleteLogs(Set<Long> ids);
}
