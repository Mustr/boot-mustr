package com.mustr.common.constant;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class PermissionsConstant {
    public static final String PSM_ALL = "p/all";//最高权限
    public static final String PSM_TEST = "p/test";//测试权限
    public static final String PSM_MANAGER_USER = "p/all/manageUser";//管理用户
    public static final String PSM_MANAGER_DEPT = "p/all/manageDept";//管理部门
    
    //存放权限的容器
    private static Map<String, String> permissionsMap = new LinkedHashMap<>();
    static {
        permissionsMap.put(PSM_ALL, "超级管理员");
        permissionsMap.put(PSM_TEST, "测试权限");
        permissionsMap.put(PSM_MANAGER_USER, "管理用户");
        permissionsMap.put(PSM_MANAGER_DEPT, "管理部门");
    }
    
    /**
     * 获取权限map
     * @return
     */
    public static Map<String, String> getPermissionsMap() {
        return permissionsMap;
    }
    
    /**
     * 获取指定权限的所有子权限
     * @param currentPM 权限
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Set<String> getAllPermissions(String currentPM) {
        if (StringUtils.isBlank(currentPM)) {
            return Collections.EMPTY_SET;
        }
        Set<String> allPM = new HashSet<>();
        allPM.add(currentPM);
        Set<String> keySet = getPermissionsMap().keySet();
        String temp = currentPM + "/";
        if (currentPM.equals(PSM_TEST)) {//测试用户，转为具有超级管理员的权限
            temp = PSM_ALL + "/";
            allPM.add(PSM_ALL);
        }
        for (String pm : keySet) {
            if (pm.startsWith(temp)) {
                allPM.add(pm);
            }
        }
        return allPM;
    }
}
