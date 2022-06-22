package com.flywinter.maplebillbackend.repository;

import com.flywinter.maplebillbackend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/22 12:55
 * Description:
 */
public interface UserRepository extends JpaRepository<UserInfo,Long> {

    UserInfo getUserInfoByEmail(String email);
}
