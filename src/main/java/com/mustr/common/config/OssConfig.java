package com.mustr.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;


/**
 * 配置minio客户端
 * @author chenxj
 * @Date 2021-6-7
 *
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(OssProperties.class)
public class OssConfig {
    
    /**
     * 配置minio client
     * @param properites
     * @return
     */
    @Bean
    MinioClient minioClient(OssProperties properites) {
        log.info("load minioClient...");
        MinioClient client = MinioClient.builder()
            .endpoint(properites.getEndpoint())
            .credentials(properites.getAccessKey(), properites.getSecretKey())
            .build();
        return client;
    }
    
}
