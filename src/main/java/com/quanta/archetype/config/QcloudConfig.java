package com.quanta.archetype.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description
 * @author Leslie Leung
 * @date 2021/11/3
 */
@Configuration
public class QcloudConfig {

    @Autowired
    private QcloudProperties qcloudProperties;

    @Bean
    public COSClient cosClient() {
        COSCredentials cred = new BasicCOSCredentials(qcloudProperties.getSecretId(), qcloudProperties.getSecretKey());
        Region region = new Region(qcloudProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }
}
