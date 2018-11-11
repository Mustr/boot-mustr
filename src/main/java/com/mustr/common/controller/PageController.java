package com.mustr.common.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

@Controller
public class PageController extends ControllerBase{

    protected int getPageNum() {
        String pageNum = getParameterValue("pageNum");
        if (StringUtils.isNotBlank(pageNum)) {
            return Integer.valueOf(pageNum);
        }
        return 1;
    }

    protected int getPageSize() {
        String pageSize = getParameterValue("pageSize");
        if (StringUtils.isNotBlank(pageSize)) {
            return Integer.valueOf(pageSize);
        }
        return 5;
    }
    
    
}
