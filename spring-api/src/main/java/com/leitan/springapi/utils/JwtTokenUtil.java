package com.leitan.springapi.utils;

import com.leitan.springapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description Jwt 工具类
 * @Author lei.tan
 * @Date 2019/12/29 14:09
 */
public class JwtTokenUtil implements Serializable {

    private static final String CLAIM_KEY_USERNAME = "sub";

    /**
     * token 过期时间 1天(毫秒)
     */
    private static final long EXPIRATION_TIME = 1 * 24 * 60 * 60 * 1000;

    /**
     * token 剩余1小时有效时间， http状态(205 {@link HttpStatus#RESET_CONTENT})提醒可刷新 token
     */
    private static final long REFRESH_TIME = 1 * 60 * 60 * 1000;

    /**
     * JWT 签名秘钥
     */
    private static final String SECRET_KEY = "phantom_secret";

    /**
     * 签发JWT
     */
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 验证 JWT
     */
    public static Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String username = getUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 校验 token 是否有效
     */
    public static Boolean validateToken(String token) {
        boolean result = true;
        try {
            String username = getUsernameFromToken(token);
            Boolean tokenExpired = isTokenExpired(token);
            if (StringUtils.isEmpty(username) || tokenExpired) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }

        return result;
    }

    /**
     * token 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 是否需要刷新 token
     * @param token
     * @return
     */
    public static Boolean needRefreshToken(String token) {
        return getExpirationDateFromToken(token).toInstant().toEpochMilli() - Instant.now().toEpochMilli() < REFRESH_TIME;
    }

    /**
     * 根据 token 获取 username
     */
    public static String getUsernameFromToken(String token) {
        String username = getClaimsFromToken(token).getSubject();
        return username;
    }

    /**
     * 获取token的过期时间
     */
    public static Date getExpirationDateFromToken(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration;
    }

    /**
     * 解析 JWT
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }


}
