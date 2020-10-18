package com.miliboy.admin.common.shiro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName SpringUtil.java
 * @Description TODO Spring上下文工具类
 * @createTime 2020年10月18日 12:22:00
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    /**
     * @title Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     *      如果是，setApp;icationContext()方法，会将容器中ApplicationContext作为参数传入进去
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:24
     * @throws
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
    /**
     * @title 通过Name返回指定的Bean
     * @description 
     * @author miliboy 
     * @updateTime 2020/10/18 13:20
     * @throws 
     */
    public static <T> T getBean(Class<T> beanClass){
        return context.getBean(beanClass);
    }
}
