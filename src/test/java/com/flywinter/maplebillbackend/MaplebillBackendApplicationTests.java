package com.flywinter.maplebillbackend;

import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机端口
@AutoConfigureJsonTesters//序列化
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//不缓存，防止方法之间互相影响
//https://relentlesscoding.com/posts/automatic-rollback-of-transactions-in-spring-tests/
class MaplebillBackendApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DataSource dataSource;

    @Test
    void registerApi() {
        //given
        final var userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail("zxk@gmail.com");
        userInfoDTO.setPassword("123456");
        userInfoDTO.setNickname("zxk");
        //when
        final var result = testRestTemplate.postForEntity("/signup", userInfoDTO, UserInfoDTO.class);
        //then
        assertEquals(201, result.getStatusCodeValue());
    }

    @AfterEach
    void tearDown() {
        //清空数据库
        Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(dataSource)
                .load()
                .clean();
    }
}
