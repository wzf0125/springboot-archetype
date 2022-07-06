package com.quanta.archetype.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description
 * @author quantacenter
 * @date 2022/7/7
 */
@Configuration
public class OSSConfig {

    @Autowired
    private OSSProperties ossProperties;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret());
    }
}
