package com.flywinter.maplebillbackend;

import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Autowired
    private JacksonTester<ResponseResult<BillDTO>> billJsonbTester;

    @Test
    void registerApi() {
        //given
        final var userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail("zxk@gmail.com");
        userInfoDTO.setPassword("123456");
        userInfoDTO.setNickname("zxk");
        //when
        final var result = testRestTemplate.postForEntity("/signup", userInfoDTO, String.class);
        //then
        assertEquals(201, result.getStatusCodeValue());
    }

    @Test
    void should_add_a_bill_record() throws IOException {
        //given
        final var billDTO = new BillDTO();
        billDTO.setEmail("1475795322@qq.com");
        billDTO.setAmount(new BigDecimal(100));
        billDTO.setCategory(1);
        billDTO.setDateTime(LocalDateTime.now());
        billDTO.setDescription("饭饭饭");
        billDTO.setType(0);
        //when
        final var httpHeaders = new HttpHeaders();
        httpHeaders.add("Custom-Maple-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuaWNrbmFtZSI6Inp4a2ZhbGwiLCJleHAiOjE2NTY4NTA5ODcsImVtYWlsIjoiMTQ3NTc5NTMyMkBxcS5jb20ifQ.D-LGV6Qit5kFXkhf4yuRRq7vzGkxBlbOZk5dnm9XGiE");
        final var billDTOHttpEntity = new HttpEntity<>(billDTO, httpHeaders);
        final var result = testRestTemplate.postForEntity("/bill", billDTOHttpEntity, String.class);
        //then
        final var responseEntity = billJsonbTester.parseObject(result.getBody());
        assertEquals(201, result.getStatusCodeValue());
        assertEquals(billDTO, responseEntity.getData());
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
