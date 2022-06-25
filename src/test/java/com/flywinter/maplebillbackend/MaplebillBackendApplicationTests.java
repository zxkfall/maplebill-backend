package com.flywinter.maplebillbackend;

import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机端口
@AutoConfigureJsonTesters//序列化
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//不缓存，防止方法之间互相影响
class MaplebillBackendApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void registerApi() {
        //given
        final var userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail("zxk@gmail.com");
        userInfoDTO.setPassword("123456");
        userInfoDTO.setNickname("zxk");
        //when
        final var result = testRestTemplate.postForEntity("/register", userInfoDTO, UserInfoDTO.class);
        //then
        assertEquals(201, result.getStatusCodeValue());
    }

}
