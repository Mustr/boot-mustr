package com.mustr.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    //注册三大组件
    @Bean
    public ServletRegistrationBean<MyServlet> myServlet(){
        ServletRegistrationBean<MyServlet> registrationBean = new ServletRegistrationBean<MyServlet>(new MyServlet(),"/myServlet");
        return registrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<MyFilter> myFilter(){
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<MyFilter>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
        return registrationBean;
    }
    
    @Bean
    public ServletListenerRegistrationBean<MyListener> myListener(){
        ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<MyListener>(new MyListener());
        return registrationBean;
    }
}
