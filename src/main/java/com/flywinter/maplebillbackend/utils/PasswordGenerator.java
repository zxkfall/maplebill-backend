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
    protected static final List<Character> LOWER_AND_UPPER_LETTERS = mergeLists(LOWER_LETTERS, UPPER_LETTERS);
    protected static final List<Character> SPECIAL_CHARACTERS = List.of('!', '"', '#', '$', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~');

    private PasswordGenerator() {
    }

    public static String generatePassword(int length) {
        return generatePassword(length, true,true);
    }

    public static String generatePassword(int length, boolean useSpecialCharacters) {
        List<Character> numbers = getSpecifiedTypeLetters(NUMBERS, getRandomLength(length - 3));
        List<Character> lowerLetters = getSpecifiedTypeLetters(LOWER_LETTERS, getRandomLength(length - numbers.size() - 2));
        final int lettersSize = numbers.size() - lowerLetters.size();
        final List<Character> upperAndSpecial = hasSpecialLetters(useSpecialCharacters, length, lettersSize, UPPER_LETTERS);
        final var passwordList = mergeLists(numbers, lowerLetters, upperAndSpecial);

        return getRandomPassword(passwordList);
    }

    public static String generatePassword(int length, boolean useSpecialCharacters, boolean isCaseSensitive) {
        if (isCaseSensitive) {
            return generatePassword(length, useSpecialCharacters);
        }

        List<Character> numbers = getSpecifiedTypeLetters(NUMBERS, getRandomLength(length - 2));
        final List<Character> letterAndSpecial = hasSpecialLetters(useSpecialCharacters, length, numbers.size(), LOWER_AND_UPPER_LETTERS);
        final var passwordList = mergeLists(numbers, letterAndSpecial);

        return getRandomPassword(passwordList);
    }

    private static String getRandomPassword(List<Character> numbers) {
        Collections.shuffle(numbers);
        return numbers.stream().map(Object::toString).collect(Collectors.joining());
    }

    private static List<Character> hasSpecialLetters(boolean useSpecialCharacters, int length, int lettersSize, List<Character> englishLetters) {
        List<Character> specialLetters;
        List<Character> upperLetters;
        if (useSpecialCharacters) {
            final int upperLetterLength = getRandomLength(length - lettersSize - 1);
            upperLetters = getSpecifiedTypeLetters(englishLetters, upperLetterLength);
            final int specialLetterLength = length - lettersSize - upperLetters.size();
            specialLetters = getSpecifiedTypeLetters(SPECIAL_CHARACTERS, specialLetterLength);
            return mergeLists(upperLetters, specialLetters);
        }
        return getSpecifiedTypeLetters(englishLetters, length - lettersSize);
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
