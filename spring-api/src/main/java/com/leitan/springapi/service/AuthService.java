package com.leitan.springapi.service;

import com.leitan.springapi.entity.AjaxResponseBody;

/**
 * @Description 权限认证相关
 * @Author lei.tan
 * @Date 2019/12/29 16:35
 */
public interface AuthService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    AjaxResponseBody login(String username, String password);

}
