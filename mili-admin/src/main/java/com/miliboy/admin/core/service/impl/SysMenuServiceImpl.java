package com.miliboy.admin.core.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miliboy.admin.core.service.SysMenuService;
import com.miliboy.admin.core.dao.SysMenuDao;
import com.miliboy.admin.core.entity.SysMenuEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title 权限业务实现
 * @description
 * @author miliboy
 * @updateTime 2020/10/18 13:27
 * @throws
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {


    /**
     * @title 根据角色查询用户权限
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 13:27
     * @throws
     */
    @Override
    public List<SysMenuEntity> selectSysMenuByRoleId(Long roleId) {
        return this.baseMapper.selectSysMenuByRoleId(roleId);
    }
}