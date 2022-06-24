package com.flywinter.maplebillbackend.utils;

import com.flywinter.maplebillbackend.entity.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/24 11:42
 * Description:
 */
class JWTUtilTest {

    @Test
    void getToken() {
        final var userInfo = new UserInfo();
        userInfo.setEmail("test@gmail.com");
        userInfo.setNickname("test");
        userInfo.setPassword("123456");
        final var token = JWTUtil.getToken(userInfo);
        final var result = token.matches("^[A-Za-z0-9-_=]+.[A-Za-z0-9-_=]+.?[A-Za-z0-9-_.+/=]*$");
        assertTrue(result);
    }

    @Test
    void verify() {
        final var userInfo = new UserInfo();
        userInfo.setEmail("test@gmail.com");
        userInfo.setNickname("test");
        userInfo.setPassword("123456");
        final var token = JWTUtil.getToken(userInfo);
        final var result = JWTUtil.verify(token,userInfo.getPassword());
        assertEquals(userInfo.getEmail(),result.getClaim("email").asString());
        assertEquals(userInfo.getNickname(),result.getClaim("nickname").asString());
    }

    @Test
    void getEmail() {
        final var userInfo = new UserInfo();
        userInfo.setEmail("test@gmail.com");
        userInfo.setNickname("test");
        userInfo.setPassword("123456");
        final var token = JWTUtil.getToken(userInfo);
        final var result = JWTUtil.getEmail(token);
        assertEquals(userInfo.getEmail(),result);
    }
}
