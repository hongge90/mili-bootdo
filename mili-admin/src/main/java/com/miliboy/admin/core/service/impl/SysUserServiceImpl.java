package com.miliboy.admin.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.miliboy.admin.core.service.SysUserService;
import com.miliboy.admin.core.dao.SysUserDao;
import com.miliboy.admin.core.entity.SysUserEntity;
import org.springframework.stereotype.Service;
/**
 * @title 系统用户业务实现
 * @description 
 * @author miliboy 
 * @updateTime 2020/10/18 13:28 
 * @throws 
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    /**
     * @title 根据用户名查询实体
     * @description 
     * @author miliboy 
     * @updateTime 2020/10/18 13:35
     * @throws 
     */
    @Override
    public SysUserEntity selectUserByName(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }
}