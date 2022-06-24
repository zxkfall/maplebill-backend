package com.flywinter.maplebillbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.flywinter.maplebillbackend.entity.UserInfo;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/24 11:18
 * Description:
 * JWT是明文，只是进行了base64加密才显得不可读
 * 所以JWT里面不要存放敏感信息，比如密码、账号等
 * 密钥的作用主要是为了防篡改，虽然内容是明文，但只要修改了，那么
 * 使用密钥验证的时候，就会失败
 * 所以，先获取用户名，然后用密钥验证token，如果验证成功，就获取用户信息
 */
public class JWTUtil {

    private JWTUtil() {
    }

    public static String getToken(UserInfo userInfo) {
        final var instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("email", userInfo.getEmail())
                .withClaim("nickname", userInfo.getNickname())
                .withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(userInfo.getPassword()));
    }

    public static DecodedJWT verify(String token, String secret) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build().verify(token);
    }

    public static String getEmail(String token) {
        final var verify = JWT.decode(token);
        return verify.getClaim("email").asString();
    }
}
