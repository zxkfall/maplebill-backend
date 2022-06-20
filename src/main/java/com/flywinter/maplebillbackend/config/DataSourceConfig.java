package com.flywinter.maplebillbackend.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/18 12:22
 * Description:
 */
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
    public DataSource dataSource() {
        createDatabase();
        return getHikariDataSource();
    }

    private void createDatabase() {
        String databaseUrl = datasourceUrl.substring(0, datasourceUrl.indexOf("?"));
        String baseUrl = databaseUrl.substring(0, databaseUrl.lastIndexOf("/"));
        String datasourceName = databaseUrl.substring(databaseUrl.lastIndexOf("/") + 1);

        loadDriver();

        try (
                Connection connection = DriverManager.getConnection(baseUrl, username, password);
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci".formatted(datasourceName));
        } catch (Exception e) {
            e.printStackTrace();
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

    private void loadDriver() {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
