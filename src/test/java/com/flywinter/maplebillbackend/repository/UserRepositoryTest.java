package com.flywinter.maplebillbackend.repository;

import com.flywinter.maplebillbackend.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 13:51
 * Description:
 */
@DataJpaTest  //
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // ForJpa
@Rollback //回滚事务
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void should_get_user_info_by_email() {
        //given
        final var userInfo = new UserInfo();
        userInfo.setEmail("zxkfall@foxmail.com");
        userInfo.setPassword("123456");
        userInfo.setNickname("zxk");
        userInfo.setRoles("ROLE_NORMAL");
        testEntityManager.persist(userInfo);
        //when
        final var result = userRepository.getUserInfoByEmail(userInfo.getEmail());
        //then
        assertEquals(userInfo.getEmail(),result.getEmail());
        assertEquals(userInfo.getNickname(),result.getNickname());
        assertEquals(userInfo.getRoles(),result.getRoles());
        assertEquals(userInfo.getPassword(),result.getPassword());
    }

}
