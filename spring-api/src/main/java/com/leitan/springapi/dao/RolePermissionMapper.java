package com.leitan.springapi.dao;

import com.leitan.springapi.entity.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author lei.tan
 * @Date 2019/12/29 20:50
 */
@Repository
public interface RolePermissionMapper {

    /**
     * 添加一条记录
     * @param rolePermission
     * @return
     */
    @Insert("insert into role_permission(role_id, permission_id) values(#{roleId}, #{permissionId})")
    int addRecord(RolePermission rolePermission);

    /**
     * 根据多个权限 id 批量删除记录
     * @param permissionIds
     * @return
     */
    @Delete("<script> delete from role_permission where permission_id in " +
            "  <foreach collection='permissionIds' item='item' index='index' open='(' separator=',' close=')' >" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int deleteByPermissionId(List<Long> permissionIds);

    /**
     * 根据多个角色 id 批量删除记录
     * @param roleIds
     * @return
     */
    @Delete("<script> delete from role_permission where role_id in " +
            "  <foreach collection='roleIds' item='item' index='index' open='(' separator=',' close=')' >" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int deleteByRoleId(List<Long> roleIds);

}
