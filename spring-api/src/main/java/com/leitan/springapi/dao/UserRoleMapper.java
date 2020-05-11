package com.leitan.springapi.dao;

import com.leitan.springapi.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @Author lei.tan
 * @Date 2019/12/29 20:57
 */
@Repository
public interface UserRoleMapper {

    /**
     * 添加一条记录
     * @param userRole
     * @return
     */
    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{roleId})")
    int addRecord(UserRole userRole);

    /**
     * 根据用户 id 删除记录
     * @param userIds
     * @return
     */
    @Delete("<script> delete from user_role where user_id in " +
            "  <foreach collection='userIds' item='item' index='index' open='(' separator=',' close=')' >" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int deleteByUserId(List<Long> userIds);

    /**
     * 根据角色 id 删除记录
     * @param roleIds
     * @return
     */
    @Delete("<script> delete from user_role where role_id in " +
            "  <foreach collection='roleIds' item='item' index='index' open='(' separator=',' close=')' >" +
            "    #{item}" +
            "  </foreach>" +
            "</script>")
    int deleteByRoleId(List<Long> roleIds);


}
