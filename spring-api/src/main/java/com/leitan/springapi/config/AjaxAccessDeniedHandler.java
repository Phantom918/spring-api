package com.leitan.springapi.config;

import com.alibaba.fastjson.JSON;
import com.leitan.springapi.entity.AjaxResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 当前资源(token)无权限访问
 * @author tanlei
 */
@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        AjaxResponseBody responseBody = AjaxResponseBody.forbidden();
        responseBody.setStatus(response.getStatus());
        responseBody.setMessage(e.getMessage());
        // 设置内容格式与编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 发送消息
        response.getWriter().write(JSON.toJSONString(responseBody));
    }
}
