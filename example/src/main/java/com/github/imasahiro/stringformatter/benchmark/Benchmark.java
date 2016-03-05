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

package com.github.imasahiro.stringformatter.benchmark;

import java.io.IOException;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.runner.RunnerException;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

public class Benchmark {
    private static final String FORMAT = "Hi %s; Hi to you %s";

    @AutoStringFormatter
    interface Formatter {
        @Format(FORMAT)
        String format(String thisName, String otherName);
    }

    public static void main(String... args) throws IOException, RunnerException {
        Main.main("-wi 3 -i 5 -f 2".split(" "));
    }

    private static final int size = 10;

    @org.openjdk.jmh.annotations.Benchmark
    public void javaStringFormat() {
        String s = null;
        for (int i = 0; i < size; i++) {
            s = javaStringFormat(FORMAT, i);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void javaStringConcat() {
        String s = null;
        for (int i = 0; i < size; i++) {
            s = javaStringConcat(i);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void stringBuilder() {
        String s = null;
        for (int i = 0; i < size; i++) {
            s = stringBuilder(i);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void autoStringFormatter() {
        String s = null;
        Formatter formatter = new Benchmark_Formatter();
        for (int i = 0; i < size; i++) {
            s = formatter.format(String.valueOf(i), String.valueOf(i * 2));
        }
    }

    private static String stringBuilder(int i) {
        StringBuilder bldString = new StringBuilder("Hi ");
        bldString.append(i);
        bldString.append("; Hi to you ");
        bldString.append(i * 2);
        return bldString.toString();
    }

    private static String javaStringConcat(int i) {
        String s = "Hi ";
        s += i;
        s += "; Hi to you ";
        s += i * 2;
        return s;
    }

    private static String javaStringFormat(String formatString, int i) {
        return String.format(formatString, i, i * 2);
    }
}
