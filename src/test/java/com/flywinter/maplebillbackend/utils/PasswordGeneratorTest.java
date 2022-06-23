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

    @Test
    void should_throw_error_when_password_length_smaller_than_3() {
        assertThrows(IllegalArgumentException.class, () -> PasswordGenerator.generatePassword(3));
    }

    @Test
    void should_generate_password_with_num_lower_upper() {
        String password = PasswordGenerator.generatePassword(8, false);
        System.out.println(password);
        assertTrue(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"));
    }

    @Test
    void should_generate_password_with_num_lower_or_upper_special() {
        String password = PasswordGenerator.generatePassword(8, true, false);
        System.out.println(password);
        assertTrue(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`])[A-Za-z\\d!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`]{8,}$"));
    }
    @Test
    void should_generate_password_with_num_lower_or_upper() {
        String password = PasswordGenerator.generatePassword(8, false, false);
        System.out.println(password);
        assertTrue(password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"));
    }
}
