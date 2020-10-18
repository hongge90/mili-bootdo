package com.miliboy.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miliboy.admin.core.entity.SysUserEntity;

/**
 * @title 系统用户业务接口
 * @description 
 * @author miliboy 
 * @updateTime 2020/10/18 13:34
 * @throws 
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 根据用户名查询实体
     * @Author Sans
     * @CreateTime 2019/6/14 16:30
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    SysUserEntity selectUserByName(String username);

}

