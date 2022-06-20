package com.flywinter.maplebillbackend;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class MaplebillBackendApplicationTests {

    @Autowired
    HikariDataSource dataSource;

    @Test
    void contextLoads() {
        System.out.println(dataSource.getJdbcUrl());
    }

}
