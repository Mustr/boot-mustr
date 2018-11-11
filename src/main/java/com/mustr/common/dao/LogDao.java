package com.mustr.common.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.mustr.common.entity.LogBean;

@Mapper
public interface LogDao {
    
    public int saveLog(LogBean log);
    
    public LogBean getLogById(long id);
    
    public Page<LogBean> getLogsByUserId(long userId);
    
    public int batchDelete(@Param("ids") Set<Long> ids);

}
