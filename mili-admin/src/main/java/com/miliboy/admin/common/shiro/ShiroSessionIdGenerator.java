package com.miliboy.admin.common.shiro;

import com.miliboy.admin.common.constant.RedisConstant;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName ShiroSessionIdGenerator.java
 * @Description TODO
 * @createTime 2020年10月18日 12:58:00
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
        return String.format(RedisConstant.REDIS_PREFIX_LOGIN,sessionId);
    }
}
