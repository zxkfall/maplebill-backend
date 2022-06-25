package com.flywinter.maplebillbackend.service.impl;

import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.service.MyUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/25 13:03
 * Description:
 */
@ExtendWith(MockitoExtension.class) //引入Mockito
class MyUserDetailServiceImplTest {

    @Mock
    private MyUserService myUserService;

    @InjectMocks
    private MyUserDetailServiceImpl myUserDetailServiceImpl;

    @Test
    void loadUserByUsername() {
        //given
        final var userInfo = new UserInfo();
        userInfo.setEmail("1475795322@qq.com");
        userInfo.setPassword("123456");
        userInfo.setRoles("ROLE_NORMAL");
        when(myUserService.getUserInfoByEmail(userInfo.getEmail())).thenReturn(userInfo);
        //when
        final var userDetails = myUserDetailServiceImpl.loadUserByUsername(userInfo.getEmail());
        //then
        assertEquals(userInfo.getEmail(), userDetails.getUsername());
        assertEquals(userInfo.getPassword(), userDetails.getPassword());
        assertEquals(userInfo.getRoles(), userDetails.getAuthorities().toArray()[0].toString());
    }
}
