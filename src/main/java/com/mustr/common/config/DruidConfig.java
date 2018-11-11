package com.mustr.common.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * 配置druid数据源相关
 * @author mustr
 *
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix="spring.datasource")
    @Bean(initMethod = "init", destroyMethod = "close") //声明其为Bean实例
    public DataSource dataSource() {
        return new DruidDataSource();
    }
    
    
    //注册druid管理后台的servlet
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<StatViewServlet>();
        bean.setServlet(new StatViewServlet());
        bean.setUrlMappings(Arrays.asList("/druid/*"));
        return bean;
    }
    
    //配置监控的filter
    @Bean
    public FilterRegistrationBean<WebStatFilter> webStatFilter() {
        FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<WebStatFilter>();
        bean.setFilter(new WebStatFilter());
        bean.setUrlPatterns(Arrays.asList("/*"));
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,*.woff,*.woff2");
        initParameters.put("DruidWebStatFilter", "/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
