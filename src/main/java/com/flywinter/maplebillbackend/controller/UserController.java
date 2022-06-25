package com.flywinter.maplebillbackend.controller;

import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.ResultState;
import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import com.flywinter.maplebillbackend.exception.RegisterFailedException;
import com.flywinter.maplebillbackend.service.MyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    private final MyUserService myUserService;

    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseResult<String> register(@Valid @RequestBody UserInfoDTO userInfoDTO) {
        final var userInfo = new UserInfo(userInfoDTO);
        final var user = myUserService.createUser(userInfo);
        if (user == null) {
            throw new RegisterFailedException("注册失败，用户已存在");
        }
        return ResponseResult.success(ResultState.REGISTER_SUCCESS);
    }
}
