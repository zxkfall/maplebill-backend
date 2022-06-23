package com.flywinter.maplebillbackend.myutils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/23 16:00
 * Description:
 */
class GenerateUtilTest {

    @Test
    void generate_password() {
        final var password = "123456";
        final var encode = new BCryptPasswordEncoder().encode(password);
        System.out.println(encode);
        List<Character> numList = getNumbers();
        List<Character> lowerLetterList = getLowerLetters();
        List<Character> upperLetterList = getUpperLetters();
        List<Character> specialList = getSpecialLetters();
        List<Character> passwdList = new ArrayList<>();
        passwdList.add(upperLetterList.get(new Random().nextInt(upperLetterList.size())));
        passwdList.add(lowerLetterList.get(new Random().nextInt(lowerLetterList.size())));
        passwdList.add(numList.get(new Random().nextInt(numList.size())));
        passwdList.add(specialList.get(new Random().nextInt(specialList.size())));
        System.out.println(passwdList);
        Assertions.assertFalse(false);
    }

    private List<Character> getSpecialLetters() {
        List<Character> specialList = new ArrayList<>();
        for (int i = 33; i < 127; i++) {
            if (!getNumbers().contains((char) i) && !getLowerLetters().contains((char) i) && !getLettersByRange(65, 91).contains((char) i)) {
                specialList.add((char) i);
            }
        }
        return specialList;
    }

    private List<Character> getUpperLetters() {
        return getLettersByRange(65, 91);
    }

    private List<Character> getLowerLetters() {
        return getLettersByRange(97, 123);
    }

    private List<Character> getNumbers() {
        return getLettersByRange(48, 58);
    }

    private List<Character> getLettersByRange(int start, int end) {
        List<Character> numList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            numList.add((char) i);
        }
        return numList;
    }
}
