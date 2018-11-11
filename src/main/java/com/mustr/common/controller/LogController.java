package com.mustr.common.controller;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.mustr.common.annotation.Log;
import com.mustr.common.entity.LogBean;
import com.mustr.common.entity.UserBean;
import com.mustr.common.service.LogService;
import com.mustr.common.service.UserService;
import com.mustr.common.utils.Pagination;
import com.mustr.common.utils.Res;
import com.mustr.common.utils.SecurityUtils;

@Controller
@RequestMapping("/common/log")
public class LogController extends PageController {
    private static String prefix = "/common/log";
    
    @Autowired
    LogService logService;
    
    @Autowired
    UserService userService;
    
    @Log("日志管理")
    @GetMapping("/logs")
    public String operationLog() {
        
        return prefix + "/logs";
    }
    
    @GetMapping("/logList")
    @ResponseBody
    public Pagination<LogBean> logList(String username) {
        Long userId = null;
        if (isNotBlank(username)) {
            UserBean user = userService.getUserByUsername(username);
            userId = user == null ? null : user.getId();
        } else {
            userId = SecurityUtils.getUserId();
        }
        
        if (userId == null) {
            return new Pagination<LogBean>();
        }
        Page<LogBean> logBeans = logService.getLogsByUserId(getPageNum(), getPageSize(), userId);
        return Pagination.builderPage(logBeans);
    }
    
    @Log("批量删除日志")
    @DeleteMapping("batchLog")
    @ResponseBody
    public Res batchDelLogs(String ids) {
        boolean flage = false;
        if (isNotBlank(ids)) {
            String[] split = ids.split(",");
            Set<Long> delIds = new HashSet<>();
            for (String id : split) {
                delIds.add(Long.valueOf(id));
            }

            flage = logService.batchDeleteLogs(delIds);
        }
        return flage ? Res.succ() : Res.error();
    }
}
