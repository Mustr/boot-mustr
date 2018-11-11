package com.mustr.common.aspect;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustr.common.annotation.Log;
import com.mustr.common.entity.LogBean;
import com.mustr.common.entity.SecurityUser;
import com.mustr.common.service.LogService;
import com.mustr.common.utils.HttpContextUtils;
import com.mustr.common.utils.IPUtil;
import com.mustr.common.utils.SecurityUtils;

@Aspect
@Component
public class LogAspect {

    @Autowired
    LogService logService;

    @Autowired
    private ObjectMapper objectMapper;

    @Pointcut("@annotation(com.mustr.common.annotation.Log)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        saveLog(point, time);

        return result;
    }

    private void saveLog(ProceedingJoinPoint point, long time) throws IOException {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        LogBean logBean = new LogBean();
        // 获取注解
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            logBean.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        logBean.setMethod(className + "." + methodName + "()");

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        // 请求的方法参数值
        Object[] args = point.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            logBean.setParams(params.length() > 4999 ? params.substring(0, 4999) : params.toString());
        }

        logBean.setIp(IPUtil.getIPAddr(request));

        SecurityUser user = SecurityUtils.getUser();
        if (null == user) {
            logBean.setUserId(-1L);
            logBean.setUsername("获取用户信息为空");
        } else {
            logBean.setUserId(user.getId());
            logBean.setUsername(user.getUsername());
        }
        logBean.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        logBean.setCreateTime(date);
        // 保存系统日志
        logService.createLog(logBean);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws IOException {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Map) {
                Set set = ((Map) args[i]).keySet();
                List list = new ArrayList();
                List paramList = new ArrayList<>();
                for (Object key : set) {
                    list.add(((Map) args[i]).get(key));
                    paramList.add(key);
                }
                return handleParams(params, list.toArray(), paramList);

            } else {
                if (args[i] instanceof Serializable) {
                    Class<?> aClass = args[i].getClass();
                    try {
                        aClass.getDeclaredMethod("toString", new Class[] { null });
                        // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走
                        // Object的 toString方法
                        params.append("  ").append(paramNames.get(i)).append(": ")
                                .append(objectMapper.writeValueAsString(args[i]));
                    } catch (NoSuchMethodException e) {
                        params.append("  ").append(paramNames.get(i)).append(": ")
                                .append(objectMapper.writeValueAsString(args[i].toString()));
                    }

                } else if (args[i] instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) args[i];
                    params.append("  ").append(paramNames.get(i)).append(": ").append(file.getName());
                } else {
                    params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
                }
            }
        }
        return params;
    }
}
