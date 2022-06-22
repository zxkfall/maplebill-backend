package com.flywinter.maplebillbackend.service;

import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.repository.UserRepository;
import com.flywinter.maplebillbackend.service.impl.MyUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 14:24
 * Description:
 */
@ExtendWith(MockitoExtension.class) //引入Mockito
class MyUserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks// 必须是类，不能是接口
    private MyUserServiceImpl myUserServiceImpl;

    @Test
    void should_get_password_by_email() {
        //given
        final var userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setEmail("zxkfall@foxmail.com");
        userInfo.setPassword(new BCryptPasswordEncoder().encode("123456"));
        userInfo.setNickname("zxk");
        userInfo.setRoles("ROLE_NORMAL");
        when(userRepository.getUserInfoByEmail("zxkfall@foxmail.com")).thenReturn(userInfo);
        //when
        final var result = myUserServiceImpl.getPasswordByEmail(userInfo.getEmail());
        //then
        assertEquals(userInfo, result);
    }

    @Test
    void should_create_user() {
        //given
        final var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final var userInfo = new UserInfo();
        userInfo.setEmail("zxkfall@foxmail.com");
        userInfo.setPassword(bCryptPasswordEncoder.encode("123456"));
        userInfo.setNickname("zxk");
        userInfo.setRoles("ROLE_NORMAL");
        when(userRepository.save(any())).thenReturn(userInfo);
        //when
        final var newUserInfo = new UserInfo();
        newUserInfo.setNickname("zxk");
        newUserInfo.setEmail("zxkfall@foxmail.com");
        newUserInfo.setPassword("123456");
        final var result = myUserServiceImpl.createUser(newUserInfo);
        //then
        final var matches = bCryptPasswordEncoder.matches("123456", result.getPassword());
        assertTrue(matches);
    }
}
