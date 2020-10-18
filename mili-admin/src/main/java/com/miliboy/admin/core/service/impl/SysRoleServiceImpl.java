package com.miliboy.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miliboy.admin.core.service.SysRoleService;
import com.miliboy.admin.core.dao.SysRoleDao;
import com.miliboy.admin.core.entity.SysRoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title 角色业务实现
 * @description 
 * @author miliboy 
 * @updateTime 2020/10/18 13:27 
 * @throws 
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    /**
     * @title 通过用户ID查询角色集合
     * @description 
     * @author miliboy 
     * @updateTime 2020/10/18 13:42
     * @throws 
     */
    @Override
    public List<SysRoleEntity> selectSysRoleByUserId(Long userId) {
        return this.baseMapper.selectSysRoleByUserId(userId);
    }
}