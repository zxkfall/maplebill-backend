package com.flywinter.maplebillbackend.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/23 16:51
 * Description:
 */
class PasswordGeneratorTest {

    @Test
    void should_generate_password_with_num_lower_upper_special() {
        String password = PasswordGenerator.generatePassword(8);
        System.out.println(password);
        assertTrue(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`])[a-zA-Z0-9!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`]{8,}$"));
    }
}
