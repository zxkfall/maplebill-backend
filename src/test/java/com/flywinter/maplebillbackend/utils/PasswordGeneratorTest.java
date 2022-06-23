package com.flywinter.maplebillbackend.utils;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        assertTrue(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`\"])[a-zA-Z0-9\\\\!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`\"]{8,}$"));
    }

    @Test
    void should_throw_error_when_password_length_smaller_than_3() {
        assertThrows(IllegalArgumentException.class, () -> PasswordGenerator.generatePassword(3));
    }

    @Test
    void should_generate_password_with_num_lower_upper() {
        String password = PasswordGenerator.generatePassword(8, false);
        assertTrue(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"));
    }

    @Test
    void should_generate_password_with_num_lower_or_upper_special() {
        String password = PasswordGenerator.generatePassword(8, true, false);
        assertTrue(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\\\!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`\"])[A-Za-z\\d\\\\!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`\"]{8,}$"));
    }

    @Test
    void should_generate_password_with_num_lower_or_upper() {
        String password = PasswordGenerator.generatePassword(8, false, false);
        assertTrue(password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"));
    }

    @Test
    void should_generate_password_with_num_lower_or_upper_16() {
        String password = PasswordGenerator.generatePassword(16, false, false);
        assertTrue(password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{16}$"));
    }


    @Test
    void should_generate_password_with_num_lower_upper_special_12_and_encode() {
        String password = PasswordGenerator.generatePassword(12);
        assertTrue(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\\\!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`\"])[a-zA-Z0-9\\\\!@#$%^&*()_+-=\\[\\]{};':|,.<>/?~`\"]{12,}$"));
        final var bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final var encode = bCryptPasswordEncoder.encode(password);
        System.out.println("password: " + password);
        System.out.println("encode: " + encode);
//        password: P42F1_6r$2$711
//        encode: $2a$10$we1KwoVzwkchAMfvRJ2NdurNk3.KzcnDcrEfrD17uT3itfnEaNVdG
    }
}
