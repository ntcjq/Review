package com.sea.review.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class JwtUtil {
    private static final Long EXPIRE = 30 * 365 * 24 * 60 * 60 * 1000L; // 过期时间(30年)

    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;

    /**
     * 生成token
     *
     * @param claims    要传送消息map
     * @param secretKey 密钥
     * @return
     */
    public static String generate(Map<String, Object> claims, String secretKey) {
        return generate(claims, EXPIRE, secretKey);
    }


    /**
     * 生成token
     *
     * @param claims    要传送消息map
     * @param expire    过期时间，单位秒
     * @param secretKey 密钥
     * @return
     */
    public static String generate(Map<String, Object> claims, Long expire, String secretKey) {
        return generate(null, claims, expire, secretKey);
    }

    /**
     * 生成token
     *
     * @param header    传入头部信息map
     * @param claims    要传送消息map
     * @param expire    过期时间
     * @param secretKey 密钥
     * @return
     */
    public static String generate(Map<String, Object> header, Map<String, Object> claims, Long expire, String secretKey) {
        Date nowDate = new Date();
        if (header == null) {
            header = new HashMap<>();
        }
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + expire);
        return Jwts.builder().setHeader(header)
                .setClaims(claims)  //自定义claims
                .setIssuedAt(nowDate)//当前时间
                .setExpiration(expireDate) //过期时间
                .signWith(JWT_ALG, secretKey)//签名算法和key
                .compact();
    }


    /**
     * 校验签名是否正确
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static boolean verify(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.warn("jwt verify fail, token=" + token, e);
            return false;
        }
    }

    /**
     * 获取payload 部分内容（即要传的信息）
     * 使用方法：如获取userId：getClaim(token).get("userId");
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static Claims getClaim(String token, String secretKey) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.warn("jwt getClaim fail, token=" + token, e);
        }
        return claims;
    }

    /**
     * 获取payload 数据（即要传的信息）
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static Map<String, Object> getDataMap(String token, String secretKey) {
        Claims claims = null;
        Map<String, Object> dataMap = new HashMap<>();
        try {
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getBody();
            for (String key : claims.keySet()) {
                dataMap.put(key, claims.get(key));
            }
        } catch (Exception e) {
            log.warn("jwt getClaim fail, token=" + token, e);
        }
        return dataMap;
    }

    /**
     * 获取头部信息map
     * 使用方法 : getHeader(token).get("alg");
     *
     * @param token
     * @param secretKey
     * @return
     */
    public static JwsHeader getHeader(String token, String secretKey) {
        JwsHeader header = null;
        try {
            header = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                    .getHeader();
        } catch (Exception e) {
            log.warn("jwt getHeader fail, token=" + token, e);
        }
        return header;
    }


    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "IH0000188311");
        String key = "tcsk_inn_home@2021@tcsk_inn_home";
        String jwt = JwtUtil.generate(claims, key);
        System.out.println(jwt);

    }
}
