package com.flywinter.maplebillbackend.controller;

import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import com.flywinter.maplebillbackend.service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 13:20
 * Description:
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private MyUserService myUserService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserInfoDTO userInfo) {
        myUserService.createUser(new UserInfo(userInfo));
        return userInfo.toString();
    }
}
