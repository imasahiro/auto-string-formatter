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

import com.github.imasahiro.stringformatter.processor.FormatConversionType.BooleanFormatConversionType;
import com.github.imasahiro.stringformatter.processor.FormatConversionType.CharacterFormatConversionType;
import com.github.imasahiro.stringformatter.processor.FormatConversionType.FloatFormatConversionType;
import com.github.imasahiro.stringformatter.processor.FormatConversionType.IntegerFormatConversionType;
import com.github.imasahiro.stringformatter.processor.FormatConversionType.StringFormatConversionType;
import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import com.squareup.javapoet.CodeBlock.Builder;
import com.squareup.javapoet.TypeName;

class FormatSpecifier implements FormatString {
    private final int index;
    private final int width;
    private final int precision;
    private final Set<FormatFlag> flags;
    private final FormatConversionType type;

    static final String STRING_BUILDER_NAME = "sb";

    private FormatSpecifier(int index, int width, int precision, Set<FormatFlag> flags,
                            FormatConversionType type) {
        this.index = index;
        this.width = width;
        this.precision = precision;
        this.flags = flags;
        this.type = type;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void emit(Builder codeBlockBuilder, TypeName argumentType) {
        codeBlockBuilder.add(type.emit("arg" + index, width, precision, flags, argumentType));
    }

    public FormatConversionType getConversionType() {
        return this.type;
    }

    static FormatString of(String s, Matcher m, int index) {
        index = parseIndex(index, s, m.start(1), m.end(1));
        Set<FormatFlag> flags = parseFlags(s, m.start(2), m.end(2));
        int width = parseWidth(s, m.start(3), m.end(3));
        int precision = parsePrecision(s, m.start(4), m.end(4));

        int time = m.start(5);
        if (time >= 0) {
            flags.add(FormatFlag.TIME);
            if (s.charAt(time) == 'T') {
                flags.add(FormatFlag.UPPER_CASE);
            }
        }
        char conversion = parseConversion(s, m.start(6), flags);
        if (Character.isUpperCase(conversion)) {
            flags.add(FormatFlag.UPPER_CASE);
        }

        FormatConversionType type = null;
        switch (conversion) {
        case 'n':
            checkArgument(width >= 0, "width is not applicable for line separator conversion.");
            checkArgument(precision >= 0, "precision is not applicable for line separator conversion.");
            return FixedString.of(System.getProperty("line.separator", "\n"));
        case '%':
            checkArgument(width >= 0, "width is not applicable for percent conversion.");
            checkArgument(precision >= 0, "precision is not applicable for percent conversion.");
            return FixedString.of("%");
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
        case 'x':
        case 'X':
            checkArgument(precision >= 0, "precision is not applicable for integer conversion.");
            type = new IntegerFormatConversionType();
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

    private static void checkArgument(boolean condition, String message) {
        if (condition) {
            throw new IllegalArgumentException(message);
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

    @Override
    public String toString() {
        return "FormatSpecifier(" +
               "index:" + index + "," +
               "width:" + width + "," +
               "precision:" + precision + "," +
               "flags:" + Joiner.on(',').join(flags) +
               ")";
    }

}
