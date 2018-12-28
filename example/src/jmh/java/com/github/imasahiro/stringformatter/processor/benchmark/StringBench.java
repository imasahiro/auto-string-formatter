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

public class StringBench {

    private static final StringBenchFormatter.Formatter formatter = new StringBenchFormatter_Formatter();

    private static final int[] VALUES = new int[] { 1, 100000000 };

    private static String javaStringFormat(String formatString, int i, int j) {
        return String.format(formatString, i, j);
    }

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        blackhole.consume(javaStringFormat(StringBenchFormatter.FORMAT,
                                           VALUES[0],
                                           VALUES[1]));
    }

    private static String javaStringConcat(int i, int j) {
        return "Hi " + i + "; Hi to you " + j;
    }

    @Benchmark
    public void javaStringConcat(Blackhole blackhole) {
        blackhole.consume(javaStringConcat(VALUES[0],
                                           VALUES[1]));
    }

    private static String stringBuilder(int i, int j) {
        StringBuilder bldString = new StringBuilder("Hi ");
        bldString.append(i);
        bldString.append("; Hi to you ");
        bldString.append(j);
        return bldString.toString();
    }

    @Benchmark
    public void stringBuilder(Blackhole blackhole) {
        blackhole.consume(stringBuilder(VALUES[0],
                                        VALUES[1]));
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        blackhole.consume(formatter.format(VALUES[0],
                                           VALUES[1]));
    }
}
