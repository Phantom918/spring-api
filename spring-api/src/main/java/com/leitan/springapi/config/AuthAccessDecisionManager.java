package com.leitan.springapi.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author lei.tan
 * @description TODO
 * @date 2019/12/29 13:54
 */
@Component
public class AuthAccessDecisionManager implements AccessDecisionManager {

    /**
     * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
     *
     * @param authentication   包含了当前用户信息以及拥有的权限，这里的权限来源就是前面登录时 UserDetailsService 中设置的 authorities
     * @param object           就是 FilterInvocation 对象，可以得到 request 等 web 资源
     * @param configAttributes configAttributes 是本次访问需要的权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || 0 >= configAttributes.size()) {
            return;
        } else {
            String needRole;
            for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext(); ) {
                needRole = iter.next().getAttribute();
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    if (needRole.trim().equals(ga.getAuthority().trim())) {
                        return;
                    }
                }
            }
            ((FilterInvocation) object).getResponse().setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            throw new AccessDeniedException("当前资源无访问权限!");
        }

    }

    /**
     * 表示此 AccessDecisionManager 是否能够处理传递的 ConfigAttribute 呈现的授权请求
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * 表示当前 AccessDecisionManager 实现是否能够为指定的安全对象（方法调用或Web请求）提供访问控制决策
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
