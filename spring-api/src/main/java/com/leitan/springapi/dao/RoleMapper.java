package com.leitan.springapi.dao;

import com.github.pagehelper.Page;
import com.leitan.springapi.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlei
 * @Description 角色 mapper
 */
@Repository
public interface RoleMapper {

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    @Delete("<script> delete from role where id in " +
            "  <foreach collection='ids' item='item' index='index' open='(' separator=',' close=')' >" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int deleteByIds(List<Long> ids);

    /**
     * 新增一条记录
     *
     * @param role
     * @return
     */
    @Insert("insert into role (id, name, description) values (#{id}, #{name}, #{description})")
    int addRole(Role role);

    /**
     * 根据 id 查找角色
     *
     * @param id
     * @return
     */
    @Select("select * from role where id = #{id}")
    Role selectById(Long id);

    /**
     * 查询所有角色
     *
     * @return
     */
    @Select("select * from role")
    List<Role> selectAll();

    /**
     * 根据 id 修改角色信息
     *
     * @param role
     * @return
     */
    @Update("update role set name = #{name}, description = #{description} where id = #{id}")
    int updateRole(Role role);

    /**
     * 根据用户 id 查询对应角色
     *
     * @param userId
     * @return
     */
    @Select("SELECT A.id, A.name FROM role A " +
            "LEFT JOIN user_role B ON A.id=B.role_id " +
            "WHERE B.user_id = ${userId}")
    List<Role> getRolesByUserId(@Param("userId") Long userId);

    /**
     * 分页查询角色
     * @param role
     * @return
     */
    @Select("<script>" +
            "select * from role " +
            "<where>" +
            "  <if test='id != null and id != &quot;&quot;'> and id = #{id} </if>" +
            "  <if test='name != null and name != &quot;&quot;'> and name like CONCAT('%', #{name}, '%') </if>" +
            "</where>" +
            " order by id asc" +
            "</script>")
    Page<Role> selectByRole(Role role);
}