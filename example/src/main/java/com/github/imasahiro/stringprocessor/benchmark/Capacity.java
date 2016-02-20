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

public class Capacity {
    private static final String FORMAT = "%32d%32d%32d%32d%32d%32d";

    @AutoStringFormatter
    interface CapacityFormatter {
        @Format(value = FORMAT, capacity = 32 * 6)
        String format(int a, int b, int c, int d, int e, int g);
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
            s = javaStringFormat(FORMAT, i);
        }

        long end = System.currentTimeMillis();
        System.out.println("Format = " + (end - start) + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            s = javaStringConcat(i);
        }
        end = System.currentTimeMillis();
        System.out.println("Concatenation = " + (end - start) + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            s = stringBuilder(i);
        }

        end = System.currentTimeMillis();
        System.out.println("String Builder = " + (end - start) + " millisecond");

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            s = Capacity_Formatter.format(i, i, i, i, i, i);
        }
        end = System.currentTimeMillis();
        System.out.println("StringFormatter = " + (end - start) + " millisecond");
    }

    private static String stringBuilder(int i) {
        StringBuilder sb = new StringBuilder(32 * 6);
        sb.append(Integer.toString(i));
        sb.append(Integer.toString(i));
        sb.append(Integer.toString(i));
        sb.append(Integer.toString(i));
        sb.append(Integer.toString(i));
        return sb.toString();
    }

    private static String javaStringConcat(int i) {
        String s = "";
        s += i;
        s += i;
        s += i;
        s += i;
        s += i;
        s += i;
        return s;
    }

    private static String javaStringFormat(String formatString, int i) {
        return String.format(formatString, i, i, i, i, i, i);
    }
}
