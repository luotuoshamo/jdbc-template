package com.wjh.util;

public class StringUtil {
    private StringUtil() {
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
