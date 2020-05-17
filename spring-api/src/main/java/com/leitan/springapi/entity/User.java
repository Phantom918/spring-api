package com.leitan.springapi.entity;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 用户信息
 * @Author lei.tan
 * @Date 2019/12/29 19:10
 */
@Data
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String sex;

    private Boolean enable;

    private String image;

    private String email;

    private List<Role> authorities;

    /**
     * 用户账号未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账号未被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户密码未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户当前可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}