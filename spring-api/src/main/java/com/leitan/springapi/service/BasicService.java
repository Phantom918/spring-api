package com.leitan.springapi.service;

import com.github.pagehelper.Page;
import com.leitan.springapi.entity.Permission;
import com.leitan.springapi.entity.Role;
import com.leitan.springapi.entity.User;

import java.util.List;

/**
 * @Description 基础权限
 * @Author lei.tan
 * @Date 2019/12/29 19:24
 */
public interface BasicService {

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    boolean addRole(Role role);

    /**
     * 添加权限
     *
     * @param permission
     * @return
     */
    boolean addPermission(Permission permission);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    boolean deleteUser(Long id);

    /**
     * 批量删除角色
     * @param ids
     * @return
     * @throws Exception
     */
    boolean deleteRoles(List<Long> ids) throws Exception;

    /**
     * 批量删除权限
     *
     * @param ids
     * @return
     */
    boolean deletePermissions(List<Long> ids);

    /**
     * 根据用户id禁用用户
     *
     * @param id
     * @return
     */
    boolean disableUser(long id);

    /**
     * 根据用户id启用用户
     *
     * @param id
     * @return
     */
    boolean enableUser(long id);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 修改角色信息
     *
     * @param role
     * @return
     */
    boolean updateRole(Role role);

    /**
     * 修改权限信息
     *
     * @param permission
     * @return
     */
    boolean updatePermission(Permission permission);

    /**
     * 条件分页查询用户
     *
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    List<User> selectUsersByPage(Integer pageNum, Integer pageSize, User user);

    /**
     * 条件分页查询角色
     *
     * @param pageNum
     * @param pageSize
     * @param role
     * @return
     */
    Page<Role> selectRolesByPage(Integer pageNum, Integer pageSize, Role role);

    /**
     * 条件分页查询权限
     *
     * @param pageNum
     * @param pageSize
     * @param permission
     * @return
     */
    Page<Permission> selectPermissionsByPage(Integer pageNum, Integer pageSize, Permission permission);


}
