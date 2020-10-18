package com.miliboy.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.miliboy.admin.core.entity.SysMenuEntity;

import java.util.List;

/**
 * @title 权限业务接口
 * @description 
 * @author miliboy 
 * @updateTime 2020/10/18 13:22 
 * @throws 
 */
public interface SysMenuService extends IService<SysMenuEntity> {
    
    
    /**
     * @title 根据角色查询用户权限
     * @description 
     * @author miliboy 
     * @updateTime 2020/10/18 13:22 
     * @throws 
     */
    List<SysMenuEntity> selectSysMenuByRoleId(Long roleId);

}

