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

package com.github.imasahiro.stringformatter.processor.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class IntegerStringifyBench {
    private static final int size = 10;

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(javaStringFormat(IntegerStringifyBenchFormatter.FORMAT,
                                               i, 2, i * 2, i + 2 * (i + 2)));
        }
    }

    @Benchmark
    public void javaStringConcat(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(javaStringConcat(i, 2, i * 2, i + 2 * (i + 2)));
        }
    }

    @Benchmark
    public void stringBuilder(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(stringBuilder(i, 2, i * 2, i + 2 * (i + 2)));
        }
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        IntegerStringifyBenchFormatter.Formatter formatter = new IntegerStringifyBenchFormatter_Formatter();
        for (int i = 0; i < size; i++) {
            blackhole.consume(formatter.format(i, 2, i * 2, i + 2 * (i + 2)));
        }
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
