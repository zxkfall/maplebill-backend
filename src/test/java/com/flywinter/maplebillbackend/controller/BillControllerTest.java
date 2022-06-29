package com.flywinter.maplebillbackend.controller;

import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.service.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/27 17:05
 * Description:
 */
@WebMvcTest(BillController.class)
@AutoConfigureJsonTesters
class BillControllerTest {

    @MockBean
    private BillService billService;
    @Autowired
    private JacksonTester<BillDTO> billDTOJacksonTester;
    @Autowired
    private JacksonTester<ResponseResult<BillDTO>> responseJacksonTester;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    private String myToken;

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
    void addBill() throws Exception {
        final var billDTO = new BillDTO();
        billDTO.setEmail("1475795322@qq.com");
        billDTO.setAmount(new BigDecimal(100));
        billDTO.setCategory(1);
        billDTO.setDateTime(LocalDateTime.now());
        billDTO.setDescription("food");
        billDTO.setType(0);
        when(billService.addBillByDTO(eq(billDTO), any())).thenReturn(ResponseResult.success(billDTO));
        final var result = mockMvc.perform(MockMvcRequestBuilders.post("/bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(billDTOJacksonTester.write(billDTO).getJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        final var contentAsString = result.getResponse().getContentAsString();
        final var billDTOResponseResult = responseJacksonTester.parse(contentAsString).getObject();
        assertEquals(billDTO, billDTOResponseResult.getData());
    }

    @Test
    void should_get_a_bill() throws Exception {
        final var billDTO = new BillDTO();
        billDTO.setEmail("1475795322@qq.com");
        billDTO.setAmount(new BigDecimal(100));
        billDTO.setCategory(1);
        billDTO.setDateTime(LocalDateTime.now());
        billDTO.setDescription("food");
        billDTO.setType(0);
        when(billService.getBillById(eq(1L), any())).thenReturn(ResponseResult.success(billDTO));
        final var result = mockMvc.perform(MockMvcRequestBuilders.get("/bill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(billDTOJacksonTester.write(billDTO).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        final var contentAsString = result.getResponse().getContentAsString();
        final var billDTOResponseResult = responseJacksonTester.parse(contentAsString).getObject();
        assertEquals(billDTO, billDTOResponseResult.getData());
    }

    @Test
    void should_delete_a_bill() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(billDTOJacksonTester.write(new BillDTO()).getJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
    }

    @Test
    void should_change_bill_info() throws Exception {
        final var billDTO = new BillDTO();
        billDTO.setEmail("1475795322@qq.com");
        billDTO.setAmount(new BigDecimal(100));
        billDTO.setCategory(1);
        billDTO.setDateTime(LocalDateTime.now());
        billDTO.setDescription("food");
        billDTO.setType(0);
        when(billService.editBillById(eq(1L), eq(billDTO), any())).thenReturn(ResponseResult.success(billDTO));
        final var result = mockMvc.perform(MockMvcRequestBuilders.put("/bill/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(billDTOJacksonTester.write(billDTO).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        final var contentAsString = result.getResponse().getContentAsString();
        final var billDTOResponseResult = responseJacksonTester.parse(contentAsString).getObject();
        assertEquals(billDTO, billDTOResponseResult.getData());
    }
}
