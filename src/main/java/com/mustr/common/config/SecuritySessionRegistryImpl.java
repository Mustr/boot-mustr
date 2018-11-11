package com.mustr.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;


public class SecuritySessionRegistryImpl  implements SessionRegistry,
ApplicationListener<SessionDestroyedEvent>{

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        System.out.println("onApplicationEvent");
    }

    @Override
    public List<Object> getAllPrincipals() {
        System.out.println("getAllPrincipals");
        return null;
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        System.out.println("getAllSessions");
        System.out.println(principal);
        return new ArrayList<>();
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        System.out.println("getSessionInformation");
        return null;
    }

    @Override
    public void refreshLastRequest(String sessionId) {
        System.out.println("refreshLastRequest");
        
    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        System.out.println("registerNewSession");
    }

    @Override
    public void removeSessionInformation(String sessionId) {
        System.out.println("removeSessionInformation");
        
    }

}
