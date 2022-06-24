package com.flywinter.maplebillbackend.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/18 12:22
 * Description:
 */
@Slf4j
@Configuration
@Primary //在同样的DataSource中，首先使用被标注的DataSource
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws ClassNotFoundException, SQLException {
        createDatabase();
        return getHikariDataSource();
    }

    private void createDatabase() throws ClassNotFoundException, SQLException {
        String databaseUrl = datasourceUrl.substring(0, datasourceUrl.indexOf("?"));
        String baseUrl = databaseUrl.substring(0, databaseUrl.lastIndexOf("/"));
        String datasourceName = databaseUrl.substring(databaseUrl.lastIndexOf("/") + 1);

        Class.forName(driverClassName);
        final var createDatabase = "CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci".formatted(datasourceName);
        try (
                final var connection = DriverManager.getConnection(baseUrl, username, password);
                final var preparedStatement = connection.prepareStatement(createDatabase)
        ) {
            final var result = preparedStatement.execute();
            if (result) {
                log.info("数据库创建成功");
            } else {
                log.info("数据库已存在");
            }
        }
    }

    private HikariDataSource getHikariDataSource() {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(datasourceUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        return datasource;
    }
}
