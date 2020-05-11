package com.leitan.springapi.config;

import com.alibaba.fastjson.JSON;
import com.leitan.springapi.entity.AjaxResponseBody;
import com.leitan.springapi.filter.JwtTokenFilter;
import com.leitan.springapi.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 未携带 token 或 携带的 token 无效, 或者程序处理异常
 * @author tanlei
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = AjaxResponseBody.forbidden();
        String token = request.getHeader(JwtTokenFilter.HEADER_STRING);
        if (!JwtTokenUtil.validateToken(token)) {
            // 无效 token
            responseBody.setStatus(HttpStatus.FORBIDDEN.value());
            responseBody.setMessage("无效Token, 请认证后操作 !");
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            // 服务异常
            responseBody.setStatus(response.getStatus());
            responseBody.setMessage("服务处理异常: " + e.getMessage());
        }
        // 设置内容格式与编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 发送消息
        response.getWriter().write(JSON.toJSONString(responseBody));
    }
}
