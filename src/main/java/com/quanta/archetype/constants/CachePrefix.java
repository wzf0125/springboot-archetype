package com.quanta.archetype.constants;

/**
 * @description 缓存key前缀
 * @author Leslie Leung
 * @date 2021/11/10
 */
public class CachePrefix {
    private CachePrefix() {
        throw new IllegalStateException();
    }

    public static final String TOKEN_PREFIX = "user_%s"; // token
    public static final String VERIFICATION_CODE_PREFIX = "verification_code_%s"; // 验证码
    public static final String VERIFICATION_EMAIL_PREFIX = "verification_email_%s"; // 验证码邮箱

}
