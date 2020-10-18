package com.miliboy.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miliboy.admin.core.entity.SysRoleEntity;

import java.util.List;

/**
 * @title 角色业务接口
 * @description
 * @author miliboy
 * @updateTime 2020/10/18 13:22
 * @throws
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 通过用户ID查询角色集合
     * @Author miliboy
     * @CreateTime 2020/10/18 13:22
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);
}

