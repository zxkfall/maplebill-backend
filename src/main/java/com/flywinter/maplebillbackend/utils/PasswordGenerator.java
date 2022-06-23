package com.flywinter.maplebillbackend.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA
 * User:Zhang Xingkun
 * Date:2022/6/23 16:50
 * Description:
 */
public class PasswordGenerator {

    public static final SecureRandom RANDOM = new SecureRandom();

    protected static final List<Character> NUMBERS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    protected static final List<Character> LOWER_LETTERS = List.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    protected static final List<Character> UPPER_LETTERS = List.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    protected static final List<Character> SPECIAL_CHARACTERS = List.of('!', '"', '#', '$', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~');

    private PasswordGenerator() {
    }

    public static String generatePassword(int length) {
        List<Character> numbers = getSpecifiedTypeLetters(NUMBERS, getRandomLength(length - 3));
        List<Character> lowerLetters = getSpecifiedTypeLetters(LOWER_LETTERS, getRandomLength(length - numbers.size() - 2));
        List<Character> upperLetters = getSpecifiedTypeLetters(UPPER_LETTERS, getRandomLength(length - numbers.size() - lowerLetters.size() - 1));
        final var specialLetterLength = length - numbers.size() - lowerLetters.size() - upperLetters.size();
        List<Character> specialLetters = getSpecifiedTypeLetters(SPECIAL_CHARACTERS, specialLetterLength);

        final List<Character> passwordList = mergeLists(numbers, lowerLetters, upperLetters, specialLetters);
        Collections.shuffle(passwordList);
        return passwordList.stream().map(Object::toString).collect(Collectors.joining());
    }

    @SafeVarargs
    private static List<Character> mergeLists(List<Character>... lists) {
        return Stream.of(lists)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private static int getRandomLength(int max) {
        return RANDOM.nextInt(1, max);
    }

    private static List<Character> getSpecifiedTypeLetters(List<Character> characterList, int size) {
        List<Character> tmpList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tmpList.add(characterList.get(RANDOM.nextInt(characterList.size())));
        }
        return tmpList;
    }


}
