package com.lordz.lbt.utils;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    /**
     * Slugify string.
     *
     * @param input input string must not be blank
     * @return slug string
     */
    @NonNull
    public static String slugify(@NonNull String input) {
        Assert.hasText(input, "Input string must not be blank");

        String withoutWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(withoutWhitespace, Normalizer.Form.NFKD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
