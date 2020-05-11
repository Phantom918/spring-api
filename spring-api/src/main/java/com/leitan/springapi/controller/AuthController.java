package com.leitan.springapi.controller;

import com.github.pagehelper.Page;
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


}
