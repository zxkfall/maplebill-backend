package com.flywinter.maplebillbackend.service;

import com.flywinter.maplebillbackend.entity.UserInfo;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 12:54
 * Description:
 */
public interface MyUserService {

    UserInfo getUserInfoByEmail(String email);

    UserInfo createUser(UserInfo userInfo);

}
