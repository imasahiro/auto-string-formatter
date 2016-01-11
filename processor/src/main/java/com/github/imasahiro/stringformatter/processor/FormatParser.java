package com.github.imasahiro.stringformatter.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatParser {
    // %[flags][width][.precision][t]conversion
    private static final String FORMAT_SPECIFIER =
            "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";

    private static Pattern FORMAT_SPECIFIER_PATTERN = Pattern.compile(FORMAT_SPECIFIER);

    /**
     * Parse format specifiers in the format string.
     */
    public static List<FormatString> parse(String fmt) {
        ArrayList<FormatString> formatStrings = new ArrayList<>();
        Matcher m = FORMAT_SPECIFIER_PATTERN.matcher(fmt);
        System.out.println(fmt);
        int index = 0;
        for (int i = 0; i < fmt.length(); ) {
            if (m.find(i)) {
                if (m.start() != i) {
                    formatStrings.add(FixedString.of(fmt, i, m.start()));
                }
                FormatString specifier = FormatSpecifier.of(fmt, m, index);
                if (specifier instanceof FormatSpecifier && index == specifier.getIndex()) {
                    index++;
                }
                formatStrings.add(specifier);
                i = m.end();
            } else {
                formatStrings.add(FixedString.of(fmt, i, fmt.length()));
                break;
            }
        }
        return formatStrings;
    }
}
