package com.leitan.springapi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.leitan.springapi.dao.*;
import com.leitan.springapi.entity.Permission;
import com.leitan.springapi.entity.Role;
import com.leitan.springapi.entity.User;
import com.leitan.springapi.service.BasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 权限基础操作
 * @Author lei.tan
 * @Date 2019/12/29 19:25
 */
@Slf4j
@Service
public class BasicServiceImpl implements BasicService {

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean addUser(User user) {
        if (StringUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        int count = userMapper.addUser(user);
        return count == 1;
    }

    @Override
    public boolean addRole(Role role) {
        int count = roleMapper.addRole(role);
        return count == 1;
    }

    @Override
    public boolean addPermission(Permission permission) {
        int count = permissionMapper.addPermission(permission);
        return count == 1;
    }

    @Override
    public boolean deleteUsers(List<Long> ids) {
        int count = userMapper.deleteUsers(ids);
        return count == ids.size();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteRoles(List<Long> ids) throws Exception {
        int count1 = rolePermissionMapper.deleteByRoleId(ids);
        log.info("[role_permission]删除{}条记录!", count1);
        int count2 = userRoleMapper.deleteByRoleId(ids);
        log.info("[user_role]删除{}条记录!", count2);
        int count = roleMapper.deleteByIds(ids);
        log.info("角色需要删除{}, 成功删除{}", ids.size(), count);
        return count == ids.size();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deletePermissions(List<Long> ids) {
        int count1 = rolePermissionMapper.deleteByRoleId(ids);
        log.info("[role_permission]删除{}条记录!", count1);
        int count = permissionMapper.deleteByIds(ids);
        log.info("角色需要删除{}, 成功删除{}", ids.size(), count);
        return count == ids.size();
    }

    @Override
    public boolean disableUser(long id) {
        int count = userMapper.disableUser(id);
        return count == 1;
    }

    @Override
    public boolean enableUser(long id) {
        int count = userMapper.enableUser(id);
        return count == 1;
    }

    @Override
    public boolean updateUser(User user) {
        if (StringUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        int count = userMapper.updateUser(user);
        return count == 1;
    }

    @Override
    public boolean updateRole(Role role) {
        int count = roleMapper.updateRole(role);
        return count == 1;
    }

    @Override
    public boolean updatePermission(Permission permission) {
        int count = permissionMapper.updatePermission(permission);
        return count == 1;
    }

    @Override
    public List<User> selectUsersByPage(Integer pageNum, Integer pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectByUser(user);
        for (int i = 0; i < users.size(); i++) {
            List<Role> roles = roleMapper.getRolesByUserId(users.get(i).getId());
            users.get(i).setAuthorities(roles);
        }
        return users;
    }

    @Override
    public List<Role> selectRolesByPage(Integer pageNum, Integer pageSize, Role role) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.selectByRole(role);
        for (int i = 0; i < roles.size(); i++) {
            List<User> users = userMapper.getUserByRoleId(roles.get(i).getId());
            roles.get(i).setUsers(users);
        }
        return roles;
    }

    @Override
    public List<Permission> selectPermissionsByPage(Integer pageNum, Integer pageSize, Permission permission) {
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> permissions = permissionMapper.selectByPermission(permission);
        return permissions;
    }

    @Override
    public int getUserCount(User user) {
        return userMapper.getUserCount(user);
    }
}
