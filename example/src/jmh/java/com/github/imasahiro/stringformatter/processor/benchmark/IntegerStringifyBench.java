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

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class IntegerStringifyBench {
    private static final IntegerStringifyBenchFormatter.Formatter formatter =
            new IntegerStringifyBenchFormatter_Formatter();
    private static final Supplier<Integer> integerSupplier = () -> ThreadLocalRandom.current().nextInt();

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        blackhole.consume(javaStringFormat(IntegerStringifyBenchFormatter.FORMAT,
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get()));
    }

    @Benchmark
    public void javaStringConcat(Blackhole blackhole) {
        blackhole.consume(javaStringConcat(integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get()));
    }

    @Benchmark
    public void stringBuilder(Blackhole blackhole) {
        blackhole.consume(stringBuilder(integerSupplier.get(),
                                        integerSupplier.get(),
                                        integerSupplier.get(),
                                        integerSupplier.get()));
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        blackhole.consume(formatter.format(integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get()));
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
