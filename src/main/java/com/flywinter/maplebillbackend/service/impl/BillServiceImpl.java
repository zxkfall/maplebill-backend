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

    @Override
    public ResponseResult<BillDTO> getBillById(Long id, Authentication authentication) {
        final var bill = billRepository.findById(id);
        if (bill.isEmpty()) {
            throw new IllegalArgumentException("Bill not found");
        }
        final var billDTO = bill.get().getBillDTO();
        final var isSameUser = billDTO.getEmail().equals(authentication.getName());
        if (!isSameUser) {
            throw new IllegalArgumentException("You can't get other's bill");
        }
        return ResponseResult.success(billDTO);
    }

    @Override
    public void removeBillById(Long id, Authentication authentication) {
        final var queryResult = billRepository.findById(id);
        if (queryResult.isEmpty()) {
            throw new IllegalArgumentException("Bill not found");
        }
        billRepository.deleteById(id);
    }
}
