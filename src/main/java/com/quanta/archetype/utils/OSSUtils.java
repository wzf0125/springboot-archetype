package com.quanta.archetype.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.ObjectMetadata;
import com.quanta.archetype.config.OSSProperties;
import com.quanta.archetype.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @description 阿里云OSS工具类
 * @author quantacenter
 * @date 2022/7/7
 */
@Slf4j
@Component
public class OSSUtils {

    @Resource
    private OSS ossClient;

    private final OSSProperties ossProperties;

    public OSSUtils(OSSProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    // 上传目标文件夹的名称（按需修改）
    private static final String DIRECTORY_NAME = "images/";

    // 允许上传图片的格式（按需增删，也可不校验图片格式）
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};

    // 允许上传图片的最大大小（按需修改）
    private static final int MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10M

    /**
     * 图片上传
     * @param image 要上传的图片
     * @return 图片url
     */
    public String uploadPhoto(MultipartFile image) {
        String imageUrl;
        try {
            imageUrl = image.getOriginalFilename();
            // 校验文件名
            if (imageUrl == null) {
                throw new ApiException("文件名不能为空");
            }
            // 校验图片格式
            boolean isLegal = false;
            for (String type : IMAGE_TYPE) {
                if (StringUtils.endsWithIgnoreCase(image.getOriginalFilename(), type)) {
                    isLegal = true;
                    break;
                }
            }
            if (!isLegal) { // 图片格式不合法
                throw new ApiException("不支持的图片格式");
            }
            // 校验图片大小
            if (image.getSize() > MAX_IMAGE_SIZE) {
                throw new ApiException("上传的图片大于10MB，请压缩图片重试");
            }
            // 拼接路径
            imageUrl = DIRECTORY_NAME + UUID.randomUUID() + imageUrl.substring(imageUrl.lastIndexOf("."));
            // 获取文件输入流
            InputStream inputStream = image.getInputStream();
            // 上传至阿里云OSS
            ossClient.putObject(ossProperties.getBucketName(), imageUrl, inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ApiException("文件存储出错，请重试");
        } catch (ServiceException e) {
            log.error("文件上传到OSS失败[ErrMsg:{}]", e.getMessage());
            throw new ApiException("OSS上传错误，请重试");
        }
        // 返回图片url
        return ossProperties.getUrlPrefix() + imageUrl;
    }
}
