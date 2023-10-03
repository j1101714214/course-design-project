package edu.whu.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @author Heima
 * @version 1.0
 * @description JwtUtil: Jwt工具类
 * @date 2023/9/16 18:10
 */

public class JwtUtil {
    public enum VerifyResult {
        EXPIRE,
        VALID,
        INVALID;
    }
    // TOKEN的有效期一天（S）
    private static final int TOKEN_TIME_OUT = 60 * 60 * 1000;   // 分钟数
    // 加密KEY
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";

    // 生产ID
    public static String getToken(UserDetails userDetails){
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime))     //签发时间
                .setSubject(userDetails.getUsername())  //说明
                .setIssuer("xyNasTools")                //签发者信息
                .setAudience("app")                     //接收用户
                .compressWith(CompressionCodecs.GZIP)   //数据压缩方式
                .signWith(SignatureAlgorithm.HS512, generalKey())               //加密方式
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT))   //过期时间戳
                .compact();
    }

    /**
     * 获取token中的claims信息
     *
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
            return Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     *
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        } catch (ExpiredJwtException e){
            return null;
        }
    }

    /**
     * 获取hearder body信息
     *
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static VerifyResult verifyToken(Claims claims) {
        if(claims==null){
            return VerifyResult.EXPIRE;
        }
        try {
            // 需要自动刷新TOKEN
            if((claims.getExpiration().getTime()-System.currentTimeMillis()) > TOKEN_TIME_OUT){
                return VerifyResult.EXPIRE;
            }else {
                return VerifyResult.VALID;
            }
        } catch (ExpiredJwtException ex) {
            return VerifyResult.EXPIRE;
        } catch (Exception e){
            return VerifyResult.INVALID;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static void main(String[] args) {
        long now = System.currentTimeMillis();
        System.out.println(new Date(now));
        System.out.println(new Date(now + TOKEN_TIME_OUT));
    }
}
