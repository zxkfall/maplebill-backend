package com.flywinter.maplebillbackend.service.impl;

import com.flywinter.maplebillbackend.entity.Bill;
import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import com.flywinter.maplebillbackend.repository.BillRepository;
import com.flywinter.maplebillbackend.service.BillService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

    public final BillRepository billRepository;

    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public ResponseResult<BillDTO> addBillByDTO(BillDTO billDTO, Authentication authentication) {
        final var email = authentication.getName();
        billDTO.setEmail(email);
        final var bill = new Bill(billDTO);
        final var result = billRepository.save(bill);
        return ResponseResult.success(result.getBillDTO());
    }
}
