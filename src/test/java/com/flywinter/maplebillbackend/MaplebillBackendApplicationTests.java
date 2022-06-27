package com.flywinter.maplebillbackend;

import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private JacksonTester<ResponseResult<String>> stringJsonbTester;

    private String myToken;

//    @BeforeEach
//    void should_login() throws IOException {
//        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
//        params.add("email", "1475795322@qq.com");
//        params.add("password", "P42F1_6r$2$711");
//        final var httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        final var billDTOHttpEntity = new HttpEntity<>(params, httpHeaders);
//        final var result = testRestTemplate.postForEntity("/login", billDTOHttpEntity, String.class);
//        final var responseJson = result.getBody();
//        final var responseResult = stringJsonbTester.parseObject(responseJson);
//        myToken = responseResult.getData();
//    }

    @BeforeEach
    void should_login() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("email", "1475795322@qq.com");
        map.put("password", "P42F1_6r$2$711");
        final var result = testRestTemplate.postForEntity("/login", map, String.class);
        final var responseJson = result.getBody();
        final var responseResult = stringJsonbTester.parseObject(responseJson);
        myToken = responseResult.getData();
    }


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
        httpHeaders.add("Custom-Maple-Token", myToken);
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
