package com.flywinter.maplebillbackend.repository;

import com.flywinter.maplebillbackend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/26 20:31
 * Description:
 */
public interface BillRepository extends JpaRepository<Bill, Long> {
}
