package com.leitan.springapi.service.impl;

import com.alibaba.fastjson.JSON;
import com.leitan.springapi.entity.AjaxResponseBody;
import com.leitan.springapi.service.AuthService;
import com.leitan.springapi.utils.JwtTokenUtil;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 权限处理
 * 1、@Resource后面没有任何内容，默认通过name属性去匹配bean，找不到再按type去匹配
 * 2、指定了name或者type则根据指定的类型去匹配bean
 * 3、指定了name和type则根据指定的name和type去匹配bean，任何一个不匹配都将报错
 *
 * 然后，区分一下@Autowired和@Resource两个注解的区别：
 * 1、@Autowired默认按照byType方式进行bean匹配，@Resource默认按照byName方式进行bean匹配
 * 2、@Autowired是Spring的注解，@Resource是J2EE的注解，这个看一下导入注解的时候这两个注解的包名就一清二楚了
 * Spring属于第三方的，J2EE是Java自己的东西，因此，建议使用@Resource注解，以减少代码和Spring之间的耦合。
 *
 * @Author lei.tan
 * @Date 2019/12/29 16:39
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public AjaxResponseBody login(String username, String password) {
        // 权鉴
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 设置上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取资源
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 生成 token
        String token = JwtTokenUtil.generateToken(userDetails);

        // 组装消息
        AjaxResponseBody responseBody = AjaxResponseBody.success();
        responseBody.setToken(token);

        return responseBody;
    }

}
