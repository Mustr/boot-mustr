package com.mustr.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * 自定义配置
 * @author chenxj
 * @Date 2021-6-24
 *
 */
@Data
@ConfigurationProperties("mustr")
@Component
public class MustrProperties {

    /**
     * 部署的服务器地址
     */
    private String serverUrl;
}
