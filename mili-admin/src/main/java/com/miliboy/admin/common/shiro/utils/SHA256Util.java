package com.miliboy.admin.common.shiro.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @title Sha-256加密工具
 * @description 
 * @author miliboy 
 * @updateTime 2020/10/18 13:20
 * @throws 
 */
public class SHA256Util {
    /**  私有构造器 **/
    private SHA256Util(){};
    /**  加密算法 **/
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /**  循环次数 **/
    public final static int HASH_ITERATIONS = 15;
    /**  执行加密-采用SHA256和盐值加密 **/
    public static String sha256(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
    }
}
