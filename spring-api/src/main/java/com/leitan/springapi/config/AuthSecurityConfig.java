package com.leitan.springapi.config;

import com.leitan.springapi.filter.JwtTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.DigestUtils;

/**
 * @author lei.tan
 * @description SecurityConfig
 * @date 2019/12/29 10:30
 */
@Slf4j
@Configuration
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    /**
     * 未登陆时时返回 JSON 格式的数据给前端（否则为 html）
     */
    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 登录成功返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    AjaxAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 登录失败返回的 JSON 格式数据给前端（否则为 html）
     */
    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）
     */
    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;

    /**
     * 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）
     */
    @Autowired
    AjaxLogoutSuccessHandler  logoutSuccessHandler;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //校验用户
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            //对密码进行加密
            @Override
            public String encode(CharSequence charSequence) {
                log.info("当前密码: {}", charSequence.toString());
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }

            //对密码进行判断匹配
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                boolean res = s.equals(encode);
                return res;
            }
        });

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                // 因为使用JWT，所以不需要 HttpSession 故关闭token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 未授权时返回 JSON 格式的数据给前端（否则为 html）
                .authorizeRequests()
                // OPTIONS请求全部放行
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录接口放行
                .antMatchers("/auth/login").permitAll()
                // 其他接口全部接受验证
                .anyRequest().authenticated()
                .and()
                .cors()//新加入
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        //使用自定义的 Token过滤器 验证请求的Token是否合法
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
