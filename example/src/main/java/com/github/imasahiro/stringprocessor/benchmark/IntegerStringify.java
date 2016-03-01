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

package com.github.imasahiro.stringprocessor.benchmark;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

public class IntegerStringify {
    private static final String FORMAT = "%d + %d * %d = %d";

    @AutoStringFormatter
    interface Formatter {
        @Format(value = FORMAT)
        String format(int a, int b, int c, int d);
    }

    public static void main(String... args) {
        for (int i = 0; i < 100; i++) {
            f(10);
        }
        for (int i = 0; i < 100; i++) {
            f(100000);
        }
    }

    public static void f(int n) {
        long start = System.currentTimeMillis();
        String s = null;
        for (int i = 0; i < n; i++) {
            //s = javaStringFormat(FORMAT, i, 2, i * 2, i + 2 * (i + 2));
        }

        long end = System.currentTimeMillis();
        System.out.println("Format = " + (end - start) + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            s = javaStringConcat(i, 2, i * 2, i + 2 * (i + 2));
        }
        end = System.currentTimeMillis();
        System.out.println("Concatenation = " + (end - start) + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            s = stringBuilder(i, 2, i * 2, i + 2 * (i + 2));
        }

        end = System.currentTimeMillis();
        System.out.println("String Builder = " + (end - start) + " millisecond");

        start = System.currentTimeMillis();
        IntegerStringify.Formatter formatter = new IntegerStringify_Formatter();
        for (int i = 0; i < n; i++) {
            s = formatter.format(i, 2, i * 2, i + 2 * (i + 2));
        }
        end = System.currentTimeMillis();
        System.out.println("StringFormatter = " + (end - start) + " millisecond");
    }

    private static String stringBuilder(int a, int b, int c, int d) {
        return new StringBuilder()
                .append(a)
                .append(" + ")
                .append(b)
                .append(" * ")
                .append(c)
                .append(" = ")
                .append(d)
                .toString();
    }

    private static String javaStringConcat(int a, int b, int c, int d) {
        String s = "";
        s += a;
        s += " + ";
        s += b;
        s += " * ";
        s += c;
        s += " = ";
        s += d;
        return s;
    }

    private static String javaStringFormat(String formatString, int a, int b, int c, int d) {
        return String.format(formatString, a, b, c, d);
    }
}
