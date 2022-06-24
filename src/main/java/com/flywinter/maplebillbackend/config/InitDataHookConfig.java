package com.flywinter.maplebillbackend.config;

import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/23 22:58
 * Description: Init data when all spring boot application start finished
 */
@Slf4j
@Configuration
public class InitDataHookConfig {

    private final DataSource dataSource;

    public InitDataHookConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            //JPA创建表初始化后才启用Flyway
            //注意配置里面先要将base-line-migrate设置为false
            //Flyway只会执行V2及以后的数据库脚本
            Flyway.configure()
                    .baselineOnMigrate(true)
                    .dataSource(dataSource)
                    .load()
                    .migrate();
            log.info("数据库初始化完成");
            log.info("email:1475795322@qq.com");
            log.info("password:P42F1_6r$2$711");
        };
    }
}
