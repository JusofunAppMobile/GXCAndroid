package com.jusfoun.jusfouninquire.ui.util;

import java.util.regex.Pattern;

/**
 * Created by xiaoma on 2018/5/7/007.
 */


public class RegexUtils {
    public RegexUtils() {
    }

    public static boolean checkEmail(String value) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, value);
    }

    public static boolean checkLetter(String value) {
        String regex = "^[a-zA-Z]+$";
        return Pattern.matches(regex, value);
    }

    public static boolean checkIdCard(String value) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, value);
    }

    public static boolean checkMobile(String value) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex, value);
    }

    public static boolean checkDigit(String value) {
        String regex = "[0-9]*";
        return Pattern.matches(regex, value);
    }

    public static boolean checkDecimals(String value) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, value);
    }

    public static boolean checkBlankSpace(String value) {
        String regex = "\\s+";
        return Pattern.matches(regex, value);
    }

    public static boolean checkChinese(String value) {
        String regex = "^[一-龥]+$";
        return Pattern.matches(regex, value);
    }

    public static boolean checkBirthday(String value) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, value);
    }

    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    public static boolean checkPostcode(String value) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, value);
    }

    public static boolean checkIpAddress(String value) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, value);
    }
}
