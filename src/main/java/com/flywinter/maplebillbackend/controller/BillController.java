package com.flywinter.maplebillbackend.controller;

import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@AllArgsConstructor
public class BillController {

    private BillService billService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseResult<BillDTO> addBill(@Valid @RequestBody BillDTO billDTO, Authentication authentication) {
        return billService.addBillByDTO(billDTO, authentication);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<BillDTO> getBill(@PathVariable(value = "id") Long id,
                                           Authentication authentication) {
        return billService.getBillById(id, authentication);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBill(@PathVariable(value = "id") Long id,
                           Authentication authentication) {
        billService.removeBillById(id, authentication);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseResult<BillDTO> editBill(@PathVariable("id") Long id,
                                            @Valid @RequestBody BillDTO billDTO,
                                            Authentication authentication) {
        return billService.editBillById(id, billDTO, authentication);
    }
}
