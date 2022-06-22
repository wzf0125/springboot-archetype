package com.quanta.archetype.utils;

import com.quanta.archetype.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @description 发送邮件工具类
 * @author Leslie Leung
 * @date 2021/11/9
 */
@Component
public class MailUtils {
    final
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    // TODO 改成配置
    private final String[] adminEmails = {
            "974500760@qq.com",
            "1260052701@qq.com"
    };

    public MailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 创建random对象部分来自猫兄
    private static final Random random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new ApiException(e.getMessage());
        }
    }

    /**
     * 发送邮件基础方法
     * @param recipients 收件人数组
     * @param title 邮件标题
     * @param content 邮件正文
     * @param isHtml 是否html格式
     */
    private void sendMail(String[] recipients, String title, String content, boolean isHtml) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage, true, "utf-8");
            messageHelper.setFrom(mailFrom, "广外劳动学时");
            messageHelper.setTo(recipients);
            messageHelper.setSubject(title);
            messageHelper.setText(content, isHtml);
            javaMailSender.send(mimeMailMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ApiException("邮件发送出错！");
        }
    }


    /**
     * 发送邮箱验证码
     * @param recipient 收件人邮箱
     * @param verificationCode 验证码
     * @param type 验证码类型 0代表绑定邮箱，1代表忘记密码
     */
    public void sendVerificationMail(String recipient, String verificationCode, int type) {
        String template;
        String title;
        if (type == 0) {
            template = "你正在绑定【广外劳动学时】邮箱，你的验证码为 %s ，5分钟内有效。";
            title = "【广外劳动学时】邮箱绑定验证码";
        } else {
            template = "你正在修改【广外劳动学时】登录密码，你的验证码为 %s ，5分钟内有效。";
            title = "【广外劳动学时】修改密码验证码";
        }
        String mailBody = String.format(template, verificationCode);
        sendMail(new String[]{recipient}, title, mailBody, false);
    }

    /**
     * 管理员向学生发送邮件方法
     * @param recipients 学生邮箱信息数组
     * @param title 标题
     * @param content 内容
     * @param isHtml 是否以html格式发送
     */
    public void sendMessage(String[] recipients, String title, String content, boolean isHtml) {
        sendMail(recipients, title, content, isHtml);
    }


    /**
     * 生成邮箱验证码，码长6位，字母与数字混合
     * @return 6位验证码
     */
    public String getVerificationCode() {
        // 增大数字权重，去除部分相似字母
        final String CHARACTERS = "0123456789012345678901234567890123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt((int) (Math.random()*(CHARACTERS.length()))));
        }
        return sb.toString();
    }
}
