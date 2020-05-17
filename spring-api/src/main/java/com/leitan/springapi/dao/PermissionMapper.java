package com.leitan.springapi.dao;

import com.github.pagehelper.Page;
import com.leitan.springapi.entity.Permission;
import com.leitan.springapi.entity.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description 权限 mapper
 * @author tanlei
 */
@Repository
public interface PermissionMapper {

    /**
     * 根据 id 删除记录
     * @param ids
     * @return
     */
    @Delete("<script> delete from permission where id in " +
            "  <foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' >" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int deleteByIds(List<Long> ids);

    /**
     * 插入记录
     * @param permission
     * @return
     */
    @Insert("insert into permission (id, code, url, name, description, parent_code) values (#{id}, #{code}, #{url}, #{name}, #{description}, #{parentCode})")
    int addPermission(Permission permission);

    /**
     * 根据 id 查找一条记录
     * @param id
     * @return
     */
    @Select("select * from permission where id = #{id}")
    Permission selectById(Long id);

    /**
     * 获取所有权限
     * @return
     */
    @Select("select * from permission")
    List<Permission> selectAll();

    /**
     * 根据 id 修改记录
     * @param permission
     * @return
     */
    @Update("update permission set code = #{code}, url = #{url}, name = #{name}, description = #{description}, parent_code = #{parentCode} where id = #{id}")
    int updatePermission(Permission permission);

    /**
     * 获取所有角色的权限
     * @return
     */
    @Select("SELECT C.url, A.name AS roleName FROM role AS A " +
            "LEFT JOIN role_permission B ON A.id = B.role_id " +
            "LEFT JOIN permission AS C ON B.permission_id = C.id")
    List<RolePermission> getRolePermissions();

    /**
     * 根据角色 id 查询权限信息
     * @param roleId
     * @return
     */
    @Select("select p.* from permission p left join role_permission rp on p.id = rp.permission_id where rp.role_id = #{roleId}")
    List<Permission> getPermissionByRoleId(Long roleId);

    /**
     * 分页查询权限
     * @param permission
     * @return
     */
    @Select("<script>" +
            "select * from permission " +
            "<where>" +
            "  <if test='id != null and id != &quot;&quot;'> and id = #{id} </if>" +
            "  <if test='code != null and code != &quot;&quot;'> and code = #{code} </if>" +
            "  <if test='parentCode != null and parentCode != &quot;&quot;'> parent_code id = #{parentCode} </if>" +
            "  <if test='name != null and name != &quot;&quot;'> and name like CONCAT('%', #{name}, '%') </if>" +
            "</where>" +
            " order by code asc" +
            "</script>")
    List<Permission> selectByPermission(Permission permission);

}