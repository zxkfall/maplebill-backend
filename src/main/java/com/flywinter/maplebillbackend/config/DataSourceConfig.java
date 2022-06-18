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
    //jdbc:mysql://127.0.0.1:3306/insight?useUnicode=true&characterEncoding=utf8&failOverReadOnly=false&allowMultiQueries=true
    private String datasourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean     //声明其为Bean实例
    public DataSource dataSource(){
        HikariDataSource datasource = new HikariDataSource();

        datasource.setJdbcUrl(datasourceUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        try {
            Class.forName(driverClassName);

            String url01 = datasourceUrl.substring(0,datasourceUrl.indexOf("?"));

            String url02 = url01.substring(0,url01.lastIndexOf("/"));

            String datasourceName = url01.substring(url01.lastIndexOf("/")+1);
            // 连接已经存在的数据库，如：mysql
            Connection connection = DriverManager.getConnection(url02, username, password);
            Statement statement = connection.createStatement();

            // 创建数据库
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + datasourceName + "` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci");

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datasource;
    }
}
