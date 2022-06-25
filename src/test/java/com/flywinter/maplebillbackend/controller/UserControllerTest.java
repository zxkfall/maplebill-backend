package com.flywinter.maplebillbackend.controller;

import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.entity.UserInfo;
import com.flywinter.maplebillbackend.entity.UserInfoDTO;
import com.flywinter.maplebillbackend.service.MyUserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/25 14:01
 * Description:
 */
@WebMvcTest(UserController.class)
@AutoConfigureJsonTesters
class UserControllerTest {

    @MockBean
    private MyUserService myUserService;
    @Autowired
    private JacksonTester<UserInfoDTO> userInfoDTOJacksonTester;
    @Autowired
    private JacksonTester<ResponseResult> responseJacksonTester;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    //If 403 or 401 see
    //https://stackoverflow.com/questions/53387415/unit-test-springboot-mockmvc-returns-403-forbidden
    //https://stackoverflow.com/questions/21749781/why-i-received-an-error-403-with-mockmvc-and-junit
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void register() throws Exception {
        //given
        final var userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail("zxk@gmail.com");
        userInfoDTO.setPassword("123456");
        userInfoDTO.setNickname("zxk");
        final var userInfo = new UserInfo(userInfoDTO);
        //注意equals方法
        when(myUserService.createUser(argThat(argument -> argument.equals(userInfo)))).thenReturn(userInfo);
        //when
        final var result = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(userInfoDTOJacksonTester.write(userInfoDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        final var responseString = result.getResponse().getContentAsString();
        final var responseResult = responseJacksonTester.parse(responseString).getObject();
        //then
        assertEquals(201, responseResult.getCode());
        assertEquals("register success", responseResult.getMessage());
    }
}
