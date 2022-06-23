package com.flywinter.maplebillbackend.config;

import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/23 22:58
 * Description: Init data when all spring boot application start finished
 */
@Slf4j
@Configuration
public class InitDataHookConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public ApplicationRunner applicationRunner(){
//      password: P42F1_6r$2$711
//      encode: $2a$10$we1KwoVzwkchAMfvRJ2NdurNk3.KzcnDcrEfrD17uT3itfnEaNVdG
        return args -> {
            final var userInfo = new UserInfo();
            userInfo.setEmail("1475795322@qq.com");
            userInfo.setPassword("$2a$10$we1KwoVzwkchAMfvRJ2NdurNk3.KzcnDcrEfrD17uT3itfnEaNVdG");
            userInfo.setNickname("zxkfall");
            userInfo.setRoles("ROLE_ADMIN");
            userRepository.save(userInfo);
            log.info("初始化用户信息完成");
            log.info("email: 1475795322@qq.com");
            log.info("password: P42F1_6r$2$711");
        };
    }
}
