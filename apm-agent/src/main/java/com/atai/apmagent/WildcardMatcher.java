package com.atai.apmagent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WildcardMatcher {
    private final Pattern pattern=Pattern.compile("com\\.atai\\.*.");

    public boolean maches(String className) {
        String[] split = className.split("|");
        for (int i = 0; i < split.length; i++) {
            Matcher matcher = pattern.matcher(split[i]);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
