package com.miliboy.admin.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName ShiroSessionManager.java
 * @Description TODO 自定义获取token
 * @createTime 2020年10月18日 13:07:00
 */
public class ShiroSessionManager extends DefaultWebSessionManager {
    //定义常量
    private static final String AUTHORIZATION = "Authorization";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
    //重写构造器
    public ShiroSessionManager(){
        super();
        this.setDeleteInvalidSessions(true);
    }

    /**
     * @title 重写方法实现从请求头获取Token便于接口统一，每次请求进来，Shiro会从请求头中找Authorization 这个key对应的Value（Token
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 13:11
     * @throws
     */
    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求投中存在token，则从请求头中获取token
        if(!StringUtils.isEmpty(token)){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID,token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID,Boolean.TRUE);
            return token;
        }else{
            //这里禁用掉Cookie获取方式
            //按默认规则从Cookie取token
            //return super.getSessionId(request, response);
            return null;
        }

    }
}
