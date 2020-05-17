package com.leitan.springapi.controller;

import cn.hutool.core.util.ArrayUtil;
import com.github.pagehelper.util.StringUtil;
import com.leitan.springapi.entity.AjaxResponseBody;
import com.leitan.springapi.entity.User;
import com.leitan.springapi.service.AuthService;
import com.leitan.springapi.service.BasicService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 权限处理
 * @Author lei.tan
 * @Date 2019/12/29 14:06
 */
@RestController()
@RequestMapping("/auth")
public class AuthController {


    private AuthService authService;

    private BasicService basicService;

    public AuthController(AuthService authService, BasicService basicService) {
        this.authService = authService;
        this.basicService = basicService;
    }


    /**
     * 登录
     */
    @PostMapping(value = "/login")
    public AjaxResponseBody login(String username, String password) throws AuthenticationException {
        // 登录成功会返回Token给用户
        return authService.login(username, password);
    }

    @PostMapping(value = "/user/common")
    public String userHi(String name) throws AuthenticationException {
        return "hi " + name + " , you have 'user' role";
    }

    @PostMapping(value = "/user/admin")
    public String adminHi(String name) throws AuthenticationException {
        return "hi " + name + " , you have 'admin' role";
    }

    @GetMapping(value = "/user/other")
    public String other(String message) throws AuthenticationException {
        return "hi! I get your " + message + " !";
    }

    /**
     * 根据用户id或昵称分页查询用户信息
     *
     * @param current  当前第几页
     * @param pageSize 每页多少条
     * @param username 用户id或昵称
     * @param enable   是否有效
     * @return
     */
    @GetMapping("/getUsersInfo")
    public AjaxResponseBody getUsersInfo(Integer current, Integer pageSize, String username, Boolean enable) {
        User user = new User();
        user.setUsername(username);
        user.setEnable(enable);
        List<User> users = basicService.selectUsersByPage(current, pageSize, user);
        AjaxResponseBody responseBody = AjaxResponseBody.success();
        responseBody.setResult(users);
        return responseBody;
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return
     */
    @PostMapping("/addUser")
    public AjaxResponseBody addUser(@RequestBody User user) {
        boolean res = basicService.addUser(user);
        return AjaxResponseBody.success();
    }

    /**
     * 批量删除用户
     *
     * @param ids 用户id集合
     * @return
     */
    @PostMapping("/delUser")
    public AjaxResponseBody delUser(@RequestBody List<Long> ids) {
        if (ArrayUtil.isEmpty(ids)) {
            return AjaxResponseBody.parameterError();
        }
        basicService.deleteUsers(ids);

        return AjaxResponseBody.success();
    }

    /**
     * 用户名/email 查询是否存在用户
     *
     * @param user 用户
     * @return
     */
    @PostMapping("/queryUser")
    public AjaxResponseBody queryUser(@RequestBody User user) {
        if (StringUtil.isEmpty(user.getUsername()) && StringUtil.isEmpty(user.getEmail())) {
            return AjaxResponseBody.parameterError();
        }
        int userCount = basicService.getUserCount(user);
        AjaxResponseBody responseBody = AjaxResponseBody.success();
        if (userCount > 0) {
            responseBody.setResult(true);
        } else {
            responseBody.setResult(false);
        }

        return responseBody;
    }


}
