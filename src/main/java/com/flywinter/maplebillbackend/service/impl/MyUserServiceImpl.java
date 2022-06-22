package com.flywinter.maplebillbackend.service.impl;

import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.repository.UserRepository;
import com.flywinter.maplebillbackend.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 12:56
 * Description:
 */
@Service
public class MyUserServiceImpl implements MyUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo getPasswordByEmail(String email) {
        return userRepository.getUserInfoByEmail(email);
    }

    @Override
    public UserInfo createUser(UserInfo userInfo) {
        final var password = userInfo.getPassword();
        final var encode = new BCryptPasswordEncoder().encode(password);
        userInfo.setPassword(encode);
        userInfo.setRoles("ROLE_NORMAL");
        return userRepository.save(userInfo);
    }
}
