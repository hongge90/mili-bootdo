package com.miliboy.admin.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miliboy.admin.core.entity.SysMenuEntity;

import java.util.List;

/**
 * @title 权限DAO
 * @description
 * @author miliboy
 * @updateTime 2020/10/18 13:27
 * @throws
 */
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

    /**
     * 根据角色查询用户权限
     * @Author Sans
     * @CreateTime 2019/6/19 10:14
     * @Param  roleId 角色ID
     * @Return List<SysMenuEntity> 权限集合
     */
    List<SysMenuEntity> selectSysMenuByRoleId(Long roleId);
	
}
