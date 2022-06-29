package com.flywinter.maplebillbackend.service.impl;

import com.flywinter.maplebillbackend.entity.Bill;
import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.repository.BillRepository;
import com.flywinter.maplebillbackend.service.BillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/28 14:11
 * Description:
 */
@ExtendWith(MockitoExtension.class)
class BillServiceImplTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BillServiceImpl billServiceImpl;

    @Test
    void addBillByDTO() {
        final var billDTO = new BillDTO();
        billDTO.setEmail("1475795322@qq.com");
        billDTO.setAmount(new BigDecimal(100));
        billDTO.setCategory(1);
        billDTO.setDateTime(LocalDateTime.now());
        billDTO.setDescription("food");
        billDTO.setType(0);
        final var bill = new Bill(billDTO);
        when(billRepository.save(bill)).thenReturn(bill);
        when(authentication.getName()).thenReturn("1475795322@qq.com");
        final var result = billServiceImpl.addBillByDTO(billDTO, authentication);
        assertEquals(billDTO, result.getData());
    }

}
