package com.miliboy.admin.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miliboy.admin.core.entity.SysRoleEntity;

import java.util.List;

/**
 * @title 角色DAO
 * @description
 * @author miliboy
 * @updateTime 2020/10/18 13:25
 * @throws
 */
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

    /**
     * @title 通过用户ID查询角色集合
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 13:27
     * @throws
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);
	
}
