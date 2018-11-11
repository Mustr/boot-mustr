package com.mustr.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mustr.common.annotation.Log;
import com.mustr.common.utils.Pagination;
import com.mustr.common.utils.Res;

@Controller
@RequestMapping("/common/online")
public class UserSessionController extends PageController {
    private static String prefix = "common/online";

    @Autowired
    SessionRegistry sessionRegistry;
    
    @Log("查看在线用户")
    @GetMapping("/online")
    public String onlineUsers() {
        
        return prefix + "/onlineUser";
    }
    
    @GetMapping("/onlineUsers")
    @ResponseBody
    public Pagination<SessionInformation> getSessionUsers() {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<SessionInformation> sessions = new ArrayList<>();
        if (isNotBlank(allPrincipals)) {
            for (Object principal : allPrincipals) {
                List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, false);
                if (isNotBlank(allSessions)) {
                    sessions.addAll(allSessions);
                }
            }
        }

        Pagination<SessionInformation> page = new Pagination<SessionInformation>(sessions, getPageNum(), getPageSize());
        return page;
    }
    
    @Log("踢出用户")
    @ResponseBody
    @DeleteMapping("/tick")
    public Res tickUser(String sessionId) {
        if (isNotBlank(sessionId)) {
            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
            if (sessionInformation != null) {
                sessionInformation.expireNow();
            }
        }
        return Res.succ();
    }
}
