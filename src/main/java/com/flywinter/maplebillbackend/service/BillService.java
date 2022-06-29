package com.flywinter.maplebillbackend.service;

import com.flywinter.maplebillbackend.entity.BillDTO;
import com.flywinter.maplebillbackend.entity.ResponseResult;
import org.springframework.security.core.Authentication;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/27 11:31
 * Description:
 */
public interface BillService {

    ResponseResult<BillDTO> addBillByDTO(BillDTO billDTO, Authentication authentication);

    ResponseResult<BillDTO> getBillById(Long id, Authentication authentication);
}
