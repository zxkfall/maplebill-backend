package com.flywinter.maplebillbackend.controller;

import com.flywinter.maplebillbackend.entity.Bill;
import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/26 19:33
 * Description:
 */
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseResult<BillDTO> addBill(@Valid @RequestBody BillDTO billDTO, Authentication authentication) {
        final var email = authentication.getName();
        billDTO.setEmail(email);
        final var bill = new Bill(billDTO);
        final var result = billRepository.save(bill);
        return ResponseResult.success(result.getBillDTO());
    }
}
