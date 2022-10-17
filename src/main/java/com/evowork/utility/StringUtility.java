package com.evowork.utility;

import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.*;

public class StringUtility {
    private static char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự', 'ỹ'};

    private static char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u', 'y'};

    private static final Random RANDOM = new SecureRandom();

    public static String getTags(List<String> lstTag) {
        if (lstTag == null) {
            return "";
        }
        return String.join("|", lstTag);
    }

    /**
     * Check if input is null or empty
     *
     * @param testString
     * @return true if input string is null or empty string, otherwise, false.
     */
    public static Boolean isEmpty(String testString) {
        return StringUtils.isEmpty(testString) || StringUtils.isEmpty(testString.trim());
    }

    /**
     * Check if input is not a empty string
     *
     * @param testString
     * @return true if input is not null and not empty string, otherwise, false.
     */
    public static Boolean isNotEmpty(String testString) {
        return !isEmpty(testString);
    }

    /**
     * vietnamese string to english string
     *
     * @param ch
     * @return
     * @since 1.0
     */
    public static Character removeAccent(char ch) {
        int charValue = (int) ch;
        if (charValue == 768 || charValue == 769 || charValue == 770 || charValue == 771 || charValue == 775) { // utf16 table: http://asecuritysite.com/coding/asc2?val=768%2C1024
            return null;
        }
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    /**
     * vietnamese string to english string
     *
     * @param s
     * @return
     * @since 1.0
     */
    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = sb.length() - 1; i >= 0; i--) {
            Character replacedChar = removeAccent(sb.charAt(i));
            if (replacedChar != null) {
                sb.setCharAt(i, replacedChar);
            } else {
                sb.deleteCharAt(i);
            }
        }
        return sb.toString();
    }

    /**
     * Generate a random String suitable for use as a temporary password.
     *
     * @return String suitable for use as a temporary password
     * @since 1.0
     */
    public static String generateRandomString(int length, Optional<Boolean> useSpecialCharacters) {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
        if (useSpecialCharacters.orElse(false)) {
            letters += "!@#$%&*(=";
        }
        String pw = "";
        for (int i = 0; i < length; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }

    public static String generateRandomString() {
        return generateRandomString(12, Optional.of(true));
    }

    /**
     * Get full name
     *
     * @param firstName
     * @param lastName
     * @return
     */
    public static String getFullName(String firstName, String lastName) {
        String fullName = "";
        if (isNotEmpty(firstName)) {
            fullName = firstName;
        }
        if (isNotEmpty(lastName)) {
            fullName = fullName + " " + lastName;
        }
        return fullName;
    }

    /**
     * check for valid email (<a href="http://www.regular-expressions.info/email.html">regex</a>)
     *
     * @param email
     * @return
     * @since 1.0
     */
    public static Boolean isValidEmail(String email) {
        String regex = "\\A(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|  \\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\\z";
        return isNotEmpty(email) && email.trim().matches(regex);
    }

    public static String defaultTo(String text, String defaultText) {
        return isNotEmpty(text) ? text : defaultText;
    }

    public static String defaultToEmpty(String text) {
        return defaultTo(text, "");
    }

    public static boolean isNumeric(String inputData) {
        return inputData.matches("[-+]?\\d+(\\.\\d+)?");
    }

    public static String bytesToHex(byte[] bytes) {
        // https://stackoverflow.com/a/21178195/2463794
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            buffer.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
            buffer.append(Character.forDigit((bytes[i] & 0xF), 16));
        }
        return buffer.toString();
    }

    /**
     * Generate number random
     *
     * @param length
     * @return
     */
    public static String generateRandomNumber(int length) {
        String letters = "0123456789";
        String pw = "";
        for (int i = 0; i < length; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }

    /**
     * Convert String param for SQL LIKE
     *
     * @param searchText
     * @return
     */
    public static String toFullSearchLike(String searchText) {
        return "%" + searchText + "%";
    }

    /**
     * Convert Uppercase To Lowercase
     * @param str
     * @return
     */
    public static String convertUppercaseToLowercase(String str) {
        String strLower = "";
        if (str != null) {
            strLower = str.toLowerCase();
        }
        return strLower;
    }

    /**
     * Convert Lowercase To Uppercase
     * @param str
     * @return
     */
    public static String convertLowercaseToUppercase(String str) {
        String strUpper = "";
        if (str != null) {
            strUpper = str.toUpperCase();
        }
        return strUpper;
    }
}
