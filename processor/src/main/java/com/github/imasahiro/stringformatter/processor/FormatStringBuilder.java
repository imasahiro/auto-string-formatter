/*
 * Copyright (C) 2016 Masahiro Ide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.imasahiro.stringformatter.processor;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import javax.lang.model.element.Element;

import com.github.imasahiro.stringformatter.processor.specifier.BooleanFormatConversionType;
import com.github.imasahiro.stringformatter.processor.specifier.CharacterFormatConversionType;
import com.github.imasahiro.stringformatter.processor.specifier.FloatFormatConversionType;
import com.github.imasahiro.stringformatter.processor.specifier.FormatConversionType;
import com.github.imasahiro.stringformatter.processor.specifier.HexIntegerFormatConversionType;
import com.github.imasahiro.stringformatter.processor.specifier.IntegerFormatConversionType;
import com.github.imasahiro.stringformatter.processor.specifier.StringFormatConversionType;
import com.github.imasahiro.stringformatter.processor.util.ErrorReporter;
import com.google.common.primitives.Ints;

class FormatStringBuilder {
    private final Element element;
    private final ErrorReporter errorReporter;

    private String format;
    private Matcher matcher;
    private int index;

    FormatStringBuilder(Element element, ErrorReporter errorReporter) {
        this.element = element;
        this.errorReporter = errorReporter;
    }

    FormatStringBuilder format(String format) {
        this.format = format;
        return this;
    }

    FormatStringBuilder matcher(Matcher matcherResult) {
        this.matcher = matcherResult;
        return this;
    }

    FormatStringBuilder index(int index) {
        this.index = index;
        return this;
    }

    FormatString newFixedString(String s, int begin, int end) {
        for (int i = begin; i < end; i++) {
            if (s.charAt(i) == '%') {
                String conversion = i != end - 1 ? String.valueOf(s.charAt(i + 1)) : "";
                errorReporter.fatal("Unrecognized conversion : " + conversion, element);
            }
        }
        return new FixedString(s.substring(begin, end));
    }

    FormatString build() {
        index = parseIndex(index, format, matcher.start(1), matcher.end(1));
        Set<FormatFlag> flags = parseFlags(format, matcher.start(2), matcher.end(2));
        int width = parseWidth(format, matcher.start(3), matcher.end(3));
        int precision = parsePrecision(format, matcher.start(4), matcher.end(4));

        int time = matcher.start(5);
        if (time >= 0) {
            flags.add(FormatFlag.TIME);
            if (format.charAt(time) == 'T') {
                flags.add(FormatFlag.UPPER_CASE);
            }
        }
        char conversion = parseConversion(format, matcher.start(6), flags);
        if (Character.isUpperCase(conversion)) {
            flags.add(FormatFlag.UPPER_CASE);
        }

        FormatConversionType type = null;
        switch (conversion) {
            case 'n':
                checkArgument(width >= 0, "width is not applicable for line separator conversion.");
                checkArgument(precision >= 0, "precision is not applicable for line separator conversion.");
                return new FixedString(System.getProperty("line.separator", "\n"));
            case '%':
                checkArgument(width >= 0, "width is not applicable for percent conversion.");
                checkArgument(precision >= 0, "precision is not applicable for percent conversion.");
                return new FixedString("%");
            case 'b':
            case 'B':
                checkArgument(precision >= 0, "precision is not applicable for boolean conversion.");
                type = new BooleanFormatConversionType();
                break;
            case 'h':
            case 'H':
                checkArgument(precision >= 0, "precision is not applicable for integer conversion.");
                type = new IntegerFormatConversionType();
                break;
            case 's':
            case 'S':
                checkArgument(precision >= 0, "precision is not applicable for string conversion.");
                type = new StringFormatConversionType();
                break;
            case 'c':
            case 'C':
                checkArgument(precision >= 0, "precision is not applicable for character conversion.");
                type = new CharacterFormatConversionType();
                break;
            case 'd':
            case 'o':
                checkArgument(precision >= 0, "precision is not applicable for integer conversion.");
                type = new IntegerFormatConversionType();
                break;
            case 'x':
            case 'X':
                checkArgument(precision >= 0, "precision is not applicable for integer conversion.");
                type = new HexIntegerFormatConversionType();
                break;
            case 'e':
            case 'E':
                type = new FloatFormatConversionType();
                break;
            case 'f':
                type = new FloatFormatConversionType();
                break;
            case 'g':
            case 'G':
            case 'a':
            case 'A':
                type = new FloatFormatConversionType();
                break;
        }
        return new FormatSpecifier(index, width, precision, flags, type);
    }

    private void checkArgument(boolean condition, String message) {
        if (condition) {
            errorReporter.fatal(message, element);
        }
    }

    // "(\\d+\\$)" -> index
    private static int parseIndex(int index, String s, int start, int end) {
        if (start < 0) {
            return index;
        }
        return Ints.tryParse(s.substring(start, end - 1));
    }

    // "([-#+ 0,(\\<]*)" -> [MINUS, SHARP, PLUS, ZERO, ...]
    private static Set<FormatFlag> parseFlags(String s, int start, int end) {
        Set<FormatFlag> flags = new HashSet<>();
        if (start < 0) {
            return flags;
        }
        for (int i = start; i < end; i++) {
            switch (s.charAt(i)) {
            case '-':
                flags.add(FormatFlag.MINUS);
                break;
            case '#':
                flags.add(FormatFlag.SHARP);
                break;
            case '+':
                flags.add(FormatFlag.PLUS);
                break;
            case ' ':
                flags.add(FormatFlag.SPACE);
                break;
            case '0':
                flags.add(FormatFlag.ZERO);
                break;
            case ',':
                flags.add(FormatFlag.COMMA);
                break;
            case '(':
                flags.add(FormatFlag.PARENTHESIS);
                break;
            case '<':
                break;
            }
        }
        return flags;
    }

    // (\\d+)
    private static int parseWidth(String s, int start, int end) {
        if (start < 0) {
            return -1;
        }
        return Ints.tryParse(s.substring(start, end));
    }

    // "(\\.\\d+)"
    private static int parsePrecision(String s, int start, int end) {
        if (start < 0) {
            return -1;
        }
        return Ints.tryParse(s.substring(start + 1, end));
    }

    // "([a-zA-Z%])"
    private static char parseConversion(String s, int index, Set<FormatFlag> flags) {
        char c = s.charAt(index);
        if (flags.contains(FormatFlag.TIME)) {
            throw new RuntimeException("Time conversion is not implemented");
        }
        return c;
    }
}
