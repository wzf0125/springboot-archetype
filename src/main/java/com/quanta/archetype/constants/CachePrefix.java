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
    public static final String TEMP_LABOR_PREFIX = "temp_labor_%s"; // 临时保存会议信息
    public static final String LABOR_INFO_PREFIX = "labor_%s"; // 会议信息
    public static final String LABOR_SIGNED_COUNTER_PREFIX = "labor_signed_%d"; // 会议已报名人数计数器
    public static final String LABOR_SIGNED_LOCK = "labor_%d_user_%d"; // 会议报名锁

}
