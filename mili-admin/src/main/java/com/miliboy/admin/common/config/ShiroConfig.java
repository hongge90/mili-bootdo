package com.miliboy.admin.common.config;

import com.miliboy.admin.common.shiro.ShiroRealm;
import com.miliboy.admin.common.shiro.ShiroSessionIdGenerator;
import com.miliboy.admin.common.shiro.ShiroSessionManager;
import com.miliboy.admin.common.shiro.utils.SHA256Util;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName ShiroConfig.java
 * @Description TODO
 * @createTime 2020年10月18日 11:47:00
 */
@Configuration
public class ShiroConfig {

    private final String CACHE_KEY = "shiro:cache";
    private final String SESSION_KEY = "shiro:session";

    //Redis配置
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * @title 开启shiro-aop注解支持
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 11:50
     * @throws
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * @title shiro 基础配置
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 11:53
     * @throws
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> fiterChainMap = new LinkedHashMap<>();
        //注意过滤器配置的顺序不能颠倒
        //配置过滤：不会被拦截的连接
        fiterChainMap.put("/static/**", "anon");
        fiterChainMap.put("/userLogin/**", "anon");
        fiterChainMap.put("/**", "authc");
        // 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/userLogin/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fiterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @title 安全管理器
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:01
     * @throws
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义Ssession管理
        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现
        securityManager.setCacheManager(cacheManager());
        // 自定义Realm验证
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }
    /**
     * @title 身份验证器
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:46
     * @throws
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * @title 凭证匹配器 将密码校验交给Shiro的SimpleAuthenticationInfo进行处理在这里做匹配配置
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:48
     * @throws
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //散列算法，这里使用SHA256算法
        credentialsMatcher.setHashAlgorithmName(SHA256Util.HASH_ALGORITHM_NAME);
        //散列的次数，比如散列两次，相当于md5(md5(""))
        credentialsMatcher.setHashIterations(SHA256Util.HASH_ITERATIONS);
        return credentialsMatcher;
    }

    /**
     * @title 配置redis管理器
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:52
     * @throws
     */
    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }
    /**
     * @title 配置Cahche管理器
     * @description  用于往redis存储权限和角色标示 使用的是shiro-redis开源插件
     * @author miliboy
     * @updateTime 2020/10/18 12:53
     * @throws
     */
    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setKeyPrefix(CACHE_KEY);
        //配置缓存的话要求放在session里面的实体类必须要有个id标示
        redisCacheManager.setPrincipalIdFieldName("userId");
        return redisCacheManager;
    }

    /**
     * @title sessionID生成器
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 13:02
     * @throws
     */
    @Bean
    public ShiroSessionIdGenerator sessionIdGenerator(){
        return new ShiroSessionIdGenerator();
    }

    /**
     * @title 配置RedisSessionDAO
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 13:05
     * @throws
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setKeyPrefix(SESSION_KEY);
        redisSessionDAO.setExpire(timeout);
        return redisSessionDAO;
    }
    @Bean
    public SessionManager sessionManager(){
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setSessionDAO(redisSessionDAO());
        return shiroSessionManager;
    }



}
