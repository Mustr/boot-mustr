package com.mustr.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * oss存储配置
 * @author chenxj
 * @Date 2021-6-7
 *
 */
@Data
@ConfigurationProperties("mustr.minio")
public class OssProperties {

    /**
     * minio服务器地址（ip:端口）
     */
    private String endpoint;
    /**
     * minio ACCESS_KEY
     */
    private String accessKey;
    /**
     * minio SECRET_KEY
     */
    private String secretKey;
    /**
     * 桶的名称,默认是"boot-mustr"
     */
    private String bucket = "boot-mustr";
    
}
