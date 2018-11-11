package com.mustr.common.controller;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.mustr.common.utils.HttpContextUtils;

@Controller
public class ControllerBase {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected String getParameterValue(String name) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return request.getParameter(name);
    }
    
    protected boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }
    
    protected boolean isNotBlank(Collection<?> value) {
        return !CollectionUtils.isEmpty(value);
    }
    
    protected boolean isNotBlank(Map<?, ?> value) {
        return value != null && !value.isEmpty();
    }
}
