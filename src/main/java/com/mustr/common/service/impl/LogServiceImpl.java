package com.mustr.common.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mustr.common.dao.LogDao;
import com.mustr.common.entity.LogBean;
import com.mustr.common.service.LogService;
import com.mustr.common.service.ServiceBase;

@Service
public class LogServiceImpl extends ServiceBase implements LogService {

    @Autowired
    LogDao logDao;
    
    @Async
    @Override
    public Long createLog(LogBean log) {
        logDao.saveLog(log);
        return log.getId();
    }

    @Override
    public LogBean getLogById(long id) {
        return logDao.getLogById(id);
    }

    @Override
    public Page<LogBean> getLogsByUserId(int pageNum, int pageSize, long userId) {
        if (pageSize > 0) {
            PageHelper.startPage(pageNum, pageSize, true);
        }
        return logDao.getLogsByUserId(userId);
    }

    @Override
    public boolean batchDeleteLogs(Set<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            return logDao.batchDelete(ids) > 0;
        }
        return false;
    }

}
