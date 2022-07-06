package com.quanta.archetype.utils;

import cn.hutool.core.io.FileTypeUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.quanta.archetype.config.QcloudProperties;
import com.quanta.archetype.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author Leslie Leung
 * @description 腾讯云工具类
 * @date 2021/11/2
 */
@Slf4j
@Component
public class COSUtils {
    @Resource
    private COSClient cosClient;

    private final QcloudProperties qcloudProperties;

    public static final String PHOTO_PREFIX = "img/";

    public COSUtils(QcloudProperties qcloudProperties) {
        this.qcloudProperties = qcloudProperties;
    }

    /**
     * 上传图片
     *
     * @param file 要上传的图片
     * @return 图片url
     */
    public String uploadPhoto(MultipartFile file) {
        String fileName;
        try {
            fileName = file.getOriginalFilename();
            // 获取文件名后缀
            if (fileName == null) {
                throw new ApiException("文件名不能为空");
            }
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), substring);
            file.transferTo(localFile);
            String type = FileTypeUtil.getType(localFile);
            if (!type.equals("jpg") && !type.equals("png") && !type.equals("gif")) {
                throw new ApiException("上传文件不是图片类型，请上传jpg, png或gif格式图片");
            }
            if (localFile.length() > 10485760) {
                throw new ApiException("上传的图片大于10MB，请减小大小后重试");
            }
            fileName = PHOTO_PREFIX + DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()).getBytes()) + substring;
            PutObjectRequest putObjectRequest = new PutObjectRequest(qcloudProperties.getBucketName(), fileName, localFile);
            cosClient.putObject(putObjectRequest);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ApiException("文件存储出错，请重试");
        } catch (CosClientException e) {
            log.error("文件上传到COS失败[ErrMsg:{}]", e.getMessage());
            throw new ApiException("COS上传错误，请重试");
        }
        return fileName;
    }
}
