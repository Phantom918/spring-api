package com.leitan.springapi.config;

import com.alibaba.fastjson.JSON;
import com.leitan.springapi.entity.AjaxResponseBody;
import com.leitan.springapi.entity.User;
import com.leitan.springapi.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tanlei
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        AjaxResponseBody responseBody = new AjaxResponseBody();

        responseBody.setStatus(200);
        responseBody.setMessage("Login Success!");

        User userDetails = (User) authentication.getPrincipal();

        String jwtToken = JwtTokenUtil.generateToken(userDetails);
        responseBody.setToken(jwtToken);

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}

