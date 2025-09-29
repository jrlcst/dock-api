package com.dock.common.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public final class DateSanitizer {
    private static final Pattern DATE_SANITIZER = Pattern.compile("^\\s*([0-9]{4}-[0-9]{2}-[0-9]{2})\\s*.*$");

    public static String normalizeDateParam(String value) {
        if (value == null) return null;
        Matcher m = DATE_SANITIZER.matcher(value);
        if (m.matches()) {
            return m.group(1);
        }
        return value.trim();
    }
}
