package com.patrick.utils;

public class Utils {

    public static String stripLastChar(String value) {
        if (value == null) {
            return null;
        }
        return value.substring(0, value.length() - 1);
    }

}
