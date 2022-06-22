package com.quanta.archetype.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description 腾讯云COS配置
 * @author Leslie Leung
 * @date 2021/11/2
 */
@Configuration
@ConfigurationProperties(prefix = "qcloud")
@Data
public class QcloudProperties {
    private String secretId;
    private String secretKey;
    private String region;
    private String URL;
    private String bucketName;
}
