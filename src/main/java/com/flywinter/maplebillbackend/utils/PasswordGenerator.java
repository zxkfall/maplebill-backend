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

    /**
     * Generate a random password with the given length.
     * Format: ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!@#$%^&*()_+-=\[\]{};':|,.<>/?~`"])[a-zA-Z0-9\\!@#$%^&*()_+-=\[\]{};':|,.<>/?~`"]{4,}$
     *  at least 4 characters, at least one number, at least one lowercase letter, at least one uppercase letter, at least one special character.
     *  such as 2K,l  @2pL
     * @param length the length of the password
     * @return the generated password
     */
    public static String generatePassword(int length) {
        return generatePassword(length, true,true);
    }

    /**
     * Generate a random password with the given length.
     * Format:
     *  useSpecial is true ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\!@#$%^&*()_+-=\[\]{};':|,.<>/?~`"])[a-zA-Z0-9\\!@#$%^&*()_+-=\[\]{};':|,.<>/?~`"]{4,}$
     *   such as 1^Lm
     *  useSpecial is false ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{4,}$
     *   such as 1kMn
     * @param length the length of the password
     * @param useSpecialCharacters whether to use special characters
     * @return the generated password
     */
    public static String generatePassword(int length, boolean useSpecialCharacters) {
        List<Character> numbers = getSpecifiedTypeLetters(NUMBERS, getRandomLength(length - 3));
        List<Character> lowerLetters = getSpecifiedTypeLetters(LOWER_LETTERS, getRandomLength(length - numbers.size() - 2));
        final int lettersSize = numbers.size() - lowerLetters.size();
        final List<Character> upperAndSpecial = hasSpecialLetters(useSpecialCharacters, length, lettersSize, UPPER_LETTERS);
        final var passwordList = mergeLists(numbers, lowerLetters, upperAndSpecial);

        return getRandomPassword(passwordList);
    }

    /**
     * Generate a random password with the given length.
     * Format:
     *  useSpecial and isCaseSensitive are true that is generatePassword(int length)
     *  usCaseSensitive is true that is generatePassword(int length, boolean useSpecialCharacters)
     *  useCaseSensitive and useSpecialCharacters are false.
     *      It will be at least 3 characters, at least one number, at least one letter(lowercase letter or uppercase letter).
     *      ^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{3,}$
     *      such as 1Km   K2p  KH2
     *  useCaseSensitive is false and useSpecialCharacters are true.
     *      It will be at least 3 characters, at least one number, at least one letter(lowercase letter or uppercase letter), at least one special character.
     *      ^(?=.*[A-Za-z])(?=.*\d)(?=.*[\\!@#$%^&*()_+-=\[\]{};':|,.<>/?~`"])[A-Za-z\d\\!@#$%^&*()_+-=\[\]{};':|,.<>/?~`"]{8,}$
     *      such as 1L,  3o@
     * @param length the length of the password
     * @param useSpecialCharacters whether to use special characters
     * @param isCaseSensitive whether is case sensitive
     * @return the generated password
     */
    public static String generatePassword(int length, boolean useSpecialCharacters, boolean isCaseSensitive) {
        if (isCaseSensitive) {
            return generatePassword(length, useSpecialCharacters);
        }

        List<Character> numbers = getSpecifiedTypeLetters(NUMBERS, getRandomLength(length - 2));
        final List<Character> letterAndSpecial = hasSpecialLetters(useSpecialCharacters, length, numbers.size(), LOWER_AND_UPPER_LETTERS);
        final var passwordList = mergeLists(numbers, letterAndSpecial);

        return getRandomPassword(passwordList);
    }

    /**
     * Generate a random password with the given list.
     * @param numbers the list of origin password characters
     * @return the random password
     */
    private static String getRandomPassword(List<Character> numbers) {
        Collections.shuffle(numbers);
        return numbers.stream().map(Object::toString).collect(Collectors.joining());
    }

    /**
     * Generate a character list with the special characters or not.
     * @param useSpecialCharacters whether to use special characters
     * @param length the length of the password
     * @param lettersSize the size of the other letters(numbers or English letters)
     * @param englishLetters the list of English letters(lowercase or uppercase or lowercase and uppercase)
     * @return password list with special characters or not
     */
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

    /**
     * Merge lists.
     * @param lists the lists to merge
     * @return the merged list
     */
    @SafeVarargs
    private static List<Character> mergeLists(List<Character>... lists) {
        return Stream.of(lists)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Get a random length.
     * @param max the max length
     * @return the random length
     */
    private static int getRandomLength(int max) {
        return RANDOM.nextInt(1, max);
    }

    /**
     * Get specified length random list from the given list.
     * @param characterList the source list
     * @param size the size of the random list
     * @return the random list
     */
    private static List<Character> getSpecifiedTypeLetters(List<Character> characterList, int size) {
        List<Character> tmpList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tmpList.add(characterList.get(RANDOM.nextInt(characterList.size())));
        }
        return tmpList;
    }

}
