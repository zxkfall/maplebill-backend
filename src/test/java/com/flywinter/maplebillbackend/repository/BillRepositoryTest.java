package com.flywinter.maplebillbackend.repository;

import com.flywinter.maplebillbackend.entity.Bill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA
 * User:Xingkun Zhang
 * Date:2022/6/28 19:44
 * Description:
 */
@DataJpaTest  //
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // ForJpa
@Rollback //回滚事务
@EnableJpaAuditing//make sure the created time and updated time are set
class BillRepositoryTest {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save_bill() {
        final var bill = new Bill();
        bill.setEmail("1475795322@qq.com");
        bill.setAmount(new BigDecimal(100));
        bill.setCategory(1);
        bill.setDateTime(LocalDateTime.now());
        bill.setDescription("food");
        bill.setType(0);
        testEntityManager.persist(bill);
        final var result = billRepository.findAll();
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getCreateTime());
    }
}
