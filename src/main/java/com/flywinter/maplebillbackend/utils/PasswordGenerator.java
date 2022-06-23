package com.flywinter.maplebillbackend.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/23 16:50
 * Description:
 */
public class PasswordGenerator {

    private PasswordGenerator() {}

    public static String generatePassword(int length) {
        List<Character> passwordList = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        final var numbers = random.nextInt(1, length - 3);//4
        final var lowerLetters = random.nextInt(1, length - numbers - 2);//8-2-4=2
        final var upperLetters = random.nextInt(1, length - numbers - lowerLetters - 1);//8-4-2-1=1
        final var specialLetters = length - numbers - lowerLetters - upperLetters;//8-4-2-1=1
        final var numbersList = getNumbers();
        for (int i = 0; i < numbers; i++) {
            passwordList.add(numbersList.get(random.nextInt(numbersList.size())));
        }
        final var lowerLettersList = getLowerLetters();
        for (int i = 0; i < lowerLetters; i++) {
            passwordList.add(lowerLettersList.get(random.nextInt(lowerLettersList.size())));
        }
        final var upperLettersList = getUpperLetters();
        for (int i = 0; i < upperLetters; i++) {
            passwordList.add(upperLettersList.get(random.nextInt(upperLettersList.size())));
        }
        final var specialLettersList = getSpecialLetters();
        for (int i = 0; i < specialLetters; i++) {
            passwordList.add(specialLettersList.get(random.nextInt(specialLettersList.size())));
        }
        Collections.shuffle(passwordList);
        return passwordList.stream().map(String::valueOf).reduce("", (a, b) -> a + b);
    }

    private static List<Character> getSpecialLetters() {
        List<Character> specialList = new ArrayList<>();
        for (int i = 33; i < 127; i++) {
            if (!getNumbers().contains((char) i) && !getLowerLetters().contains((char) i) && !getLettersByRange(65, 91).contains((char) i)) {
                specialList.add((char) i);
            }
        }
        return specialList;
    }


    private static List<Character> getUpperLetters() {
        return getLettersByRange(65, 91);
    }

    private static List<Character> getLowerLetters() {
        return getLettersByRange(97, 123);
    }

    private static List<Character> getNumbers() {
        return getLettersByRange(48, 58);
    }

    private static List<Character> getLettersByRange(int start, int end) {
        List<Character> numList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            numList.add((char) i);
        }
        return numList;
    }
}
