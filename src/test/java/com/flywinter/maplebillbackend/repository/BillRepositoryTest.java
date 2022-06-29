package com.flywinter.maplebillbackend.repository;

import com.flywinter.maplebillbackend.entity.Bill;
import com.flywinter.maplebillbackend.entity.BillDTO;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

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

    private Bill myDefineBill;

    @BeforeEach
    void setUp() {
        myDefineBill = new Bill();
        myDefineBill.setEmail("1475795322@qq.com");
        myDefineBill.setAmount(new BigDecimal(100));
        myDefineBill.setCategory(1);
        myDefineBill.setDateTime(LocalDateTime.now());
        myDefineBill.setDescription("food");
        myDefineBill.setType(0);
    }

    @Test
    void save_bill() {
        myDefineBill.setEmail("1475795322@qq.com");
        myDefineBill.setAmount(new BigDecimal(100));
        myDefineBill.setCategory(1);
        myDefineBill.setDateTime(LocalDateTime.now());
        myDefineBill.setDescription("food");
        myDefineBill.setType(0);
        testEntityManager.persist(myDefineBill);
        final var result = billRepository.findAll();
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getCreateTime());
    }

    @Test
    void should_get_bill_by_id() {
        myDefineBill.setEmail("1475795322@qq.com");
        myDefineBill.setAmount(new BigDecimal(100));
        myDefineBill.setCategory(1);
        myDefineBill.setDateTime(LocalDateTime.now());
        myDefineBill.setDescription("food");
        myDefineBill.setType(0);
        testEntityManager.persist(myDefineBill);
        final var result = billRepository.findById(1L);
        BillDTO billDTO = new BillDTO();
        if (result.isPresent()) {
            billDTO = result.get().getBillDTO();
        }
        assertEquals(myDefineBill.getBillDTO(), billDTO);
    }
}
