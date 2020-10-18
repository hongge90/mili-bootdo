package com.miliboy.admin.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName MyShiroException.java
 * @Description TODO 自定义异常
 * @createTime 2020年10月18日 11:41:00
 */
@ControllerAdvice
public class MyShiroException {

    public Map<String,Object> defaultErrorHandler(){
        Map<String,Object> map = new HashMap<>();
        map.put("403", "权限不足");
        return map;

    }
}
