package com.miliboy.admin.common.constant;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName RedisConstant.java
 * @Description TODO redis常量类
 * @createTime 2020年10月18日 13:01:00
 */
public interface RedisConstant {
    /**
     * TOKEN前缀
     */
    String REDIS_PREFIX_LOGIN = "login_token_%s";
    /**
     * 过期时间2小时
     */
    Integer REDIS_EXPIRE_TWO = 7200;
    /**
     * 过期时间15分
     */
    Integer REDIS_EXPIRE_EMAIL = 900;
    /**
     * 过期时间5分钟
     */
    Integer REDIS_EXPIRE_KAPTCHA = 300;
    /**
     * 暂无过期时间
     */
    Integer REDIS_EXPIRE_NULL = -1;
}
