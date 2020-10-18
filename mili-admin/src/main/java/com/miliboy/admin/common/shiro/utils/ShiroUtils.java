package com.miliboy.admin.common.shiro.utils;

import com.miliboy.admin.core.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;

import java.util.Collection;
import java.util.Objects;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName ShiroUtils.java
 * @Description TODO Shiro工具类
 * @createTime 2020年10月18日 12:27:00
 */
public class ShiroUtils {

    /** 私有构造器 **/
    private ShiroUtils(){ }

    private static RedisSessionDAO redisSessionDAO = SpringUtil.getBean(RedisSessionDAO.class);
    /**
     * @title 获取当前用户的Session
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:28
     * @throws
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
    /**
     * @title 用户登出
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:30
     * @throws
     */
    public static void logout(){
        SecurityUtils.getSubject().logout();

    }
    /**
     * @title 获取当前用户信息
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:31
     * @throws
     */
    public static SysUserEntity getUserInfo(){
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    public static void deleteCache(String username,boolean isRemoveSession){
        //从缓存中获取Session
        Session session = null;
        Collection<Session> sessions = redisSessionDAO.getActiveSessions();
        SysUserEntity sysUserEntity;
        Object attribute = null;
        for(Session sessionInfo : sessions){
            //遍历Session。找到该用户名称对应的session
            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if(attribute == null){
                continue;
            }
            sysUserEntity = (SysUserEntity) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if(sysUserEntity == null){
                continue;
            }
            if(Objects.equals(sysUserEntity.getUsername(),username)){
                session = sessionInfo;
                break;
            }
        }
        if(session == null || attribute == null){
            return;
        }
        //删除session
        if(isRemoveSession){
            redisSessionDAO.delete(session);
        }
        //删除cache，在访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();
        ((LogoutAware)authc).onLogout((SimplePrincipalCollection)attribute);
    }

}
