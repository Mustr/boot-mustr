package com.mustr.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mustr.common.entity.SecurityUser;

public class SecurityUtils {

    /**
     * 获取当前登录用户
     * @return
     */
    public static SecurityUser getUser() {
    	SecurityContext context = SecurityContextHolder.getContext();
    	if (context != null) {
    		Authentication authentication = context.getAuthentication();
    		if (authentication != null) {
    			return (SecurityUser) authentication.getPrincipal();
    		}
    	}
        return null;
    }
    
    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getUsername() {
        SecurityUser userDetails = getUser();
        if (userDetails != null) {
            return userDetails.getUsername();
        }
        return null;
    }

    /**
     * 获取当前登录用户key
     * @return
     */
    public static Long getUserId() {
        SecurityUser userDetails = getUser();
        if (userDetails != null) {
            return userDetails.getId();
        }
        return null;
    }
}
