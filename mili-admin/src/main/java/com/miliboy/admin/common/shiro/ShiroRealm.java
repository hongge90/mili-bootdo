package com.miliboy.admin.common.shiro;

import com.miliboy.admin.common.shiro.utils.ShiroUtils;
import com.miliboy.admin.core.service.SysMenuService;
import com.miliboy.admin.core.service.SysRoleService;
import com.miliboy.admin.core.service.SysUserService;
import com.miliboy.admin.core.entity.SysMenuEntity;
import com.miliboy.admin.core.entity.SysRoleEntity;
import com.miliboy.admin.core.entity.SysUserEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author miliboy
 * @version 1.0
 * @ClassName ShiroRealm.java
 * @Description TODO
 * @createTime 2020年10月18日 12:03:00
 */
public class ShiroRealm  extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * @title 授权权限
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:04
     * @throws
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
        SysUserEntity sysUserEntity = (SysUserEntity)principalCollection.getPrimaryPrincipal();
        Long userId =sysUserEntity.getUserId();
        //这里可以进行授权和处理
        Set<String> rolesSet = new HashSet<>();
        Set<String> permsSet = new HashSet<>();
        //查询角色和权限（这里根据具体业务查询）
        List<SysRoleEntity> sysRoleEntityList = sysRoleService.selectSysRoleByUserId(userId);
        for (SysRoleEntity sysRoleEntity:sysRoleEntityList) {
            rolesSet.add(sysRoleEntity.getRoleName());
            List<SysMenuEntity> sysMenuEntityList = sysMenuService.selectSysMenuByRoleId(sysRoleEntity.getRoleId());
            for (SysMenuEntity sysMenuEntity :sysMenuEntityList) {
                permsSet.add(sysMenuEntity.getPerms());
            }
        }
        //将查到的权限和角色分别传入authorizationInfo中
        authenticationInfo.setStringPermissions(permsSet);
        authenticationInfo.setRoles(rolesSet);
        return authenticationInfo;
    }

    /**
     * @title 身份认证
     * @description
     * @author miliboy
     * @updateTime 2020/10/18 12:12
     * @throws
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的输入的账号
        String username = (String) authenticationToken.getPrincipal();
        //通过username从数据库中查找User对象，如果找到进行验证
        //实际项目中，可以根据具体情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行改方法
        SysUserEntity user = sysUserService.selectUserByName(username);
        //判断账号是否存在
        if (user == null) {
            throw new AuthenticationException();
        }
        //判断账号是否被冻结
        if (user.getState()==null||user.getState().equals("PROHIBIT")){
            throw new LockedAccountException();
        }
        //进行验证
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), //设置盐值
                getName());
        //验证成功开始踢人（清除缓存和Session）
        ShiroUtils.deleteCache(username,true);
        return authenticationInfo;
    }
}
