package com.leitan.springapi.dao;

import com.leitan.springapi.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlei
 * @Description @Repository 注解，用于标记是数据访问 Bean 对象。在 MyBatis 的接口，实际非必须，只是为了避免在 Service 中，@Autowired 注入时无需报警。
 * @Description 用户 mapper
 */
@Repository
public interface UserMapper {

    /**
     * 根据 id 删除用户
     *
     * @param id
     * @return
     */
    @Delete("delete from user where id = #{id}")
    int deleteUser(Long id);

    /**
     * 插入记录
     *
     * @param user
     * @return
     */
    @Insert("insert into user (username, password, nickname, sex, enable, image) values (#{username}, #{password}, #{nickname}, #{sex}, #{enable}, #{image})")
    int addUser(User user);

    /**
     * 批量插入数据
     *
     * @param users
     * @return
     */
    @Insert("<script> " +
            "insert into user(username, password, nickname, sex, enable, image) values" +
            "<foreach collection='users' item='item' index='index' separator=','>" +
            "(#{username}, #{password}, #{nickname}, #{sex}, #{enable}, #{image})" +
            "</foreach>" +
            "</script>")
    int addUsers(List<User> users);

    /**
     * 根据 id 查找用户
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User selectById(Long id);

    /**
     * 获取所有用户
     *
     * @return
     */
    @Select("select * from user")
    List<User> selectAll();

    /**
     * 分页查询用户
     *
     * @param user
     * @return
     */
    @Select("<script>" +
            "select * from user " +
            "<where>" +
            "  <if test='id != null and id != &quot;&quot;'> and id = #{id} </if>" +
            "  <if test='username != null and username != &quot;&quot;'> and username like CONCAT('%', #{username}, '%') </if>" +
            "  <if test='nickname != null and nickname != &quot;&quot;'> and nickname like CONCAT('%', #{nickname}, '%') </if>" +
            "  <if test='sex != null and sex != &quot;&quot;'> and sex = #{sex} </if>" +
            "  <if test='enable != null and enable != &quot;&quot;'> and enable = #{enable} </if>" +
            "</where>" +
            " order by id asc" +
            "</script>")
    List<User> selectByUser(User user);

    /**
     * 根据 id 修改用户
     *
     * @param user
     * @return
     */
    @Update("<script>" +
            "update user" +
            "<set>" +
            "  <if test='username != null'> username=#{username}, </if> " +
            "  <if test='password != null'> password=#{password}, </if> " +
            "  <if test='nickname != null'> nickname=#{nickname}, </if> " +
            "  <if test='sex != null'> sex=#{sex}, </if> " +
            "  <if test='enable != null'> enable=#{enable}, </if> " +
            "  <if test='image != null'> image=#{image}, </if> " +
            "</set>" +
            "where id = #{id} " +
            "</script>")
    int updateUser(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User loadUserByUsername(@Param("username") String username);

    /**
     * 根据角色 id 查询用户信息
     *
     * @param roleId
     * @return
     */
    @Select("select u.* from user u left join user_role ur on u.id = ur.user_id where ur.role_id = #{roleId}")
    List<User> getUserByRoleId(Long roleId);

    /**
     * 根据用户 id 禁用用户
     *
     * @param id
     * @return
     */
    @Update("update user set enable = 0 where id = #{id}")
    int disableUser(long id);

    /**
     * 根据用户 id 启用用户
     *
     * @param id
     * @return
     */
    @Update("update user set enable = 1 where id = #{id}")
    int enableUser(long id);
}