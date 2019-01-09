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

public class AllTypesBench {
    private static final AllTypesBenchFormatter.Formatter formatter =
            new AllTypesBenchFormatter_Formatter();

    private static String javaStringFormat(
            String formatString, boolean b, char c, double d, float f, int i, long lng,
            Object obj, String str) {
        return String.format(formatString, b, c, d, f, i, lng, obj, str);
    }

    private static final boolean[] BOOLEAN = new boolean[] { false };
    private static final char[] CHAR = new char[] { 'f' };
    private static final double[] DOUBLE = new double[] { 1.3424 };
    private static final float[] FLOAT = new float[] { 1424.1424f };
    private static final int[] INT = new int[] { 34234234 };
    private static final long[] LONG = new long[] { 324249243L };
    private static final Object[] OBJ = new Object[] { new Object() };
    private static final String[] STR = new String[] { "foobar" };

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        blackhole.consume(javaStringFormat(
                AllTypesBenchFormatter.FORMAT,
                BOOLEAN[0], CHAR[0], DOUBLE[0], FLOAT[0], INT[0], LONG[0], OBJ[0], STR[0]));
    }

    private static String javaStringConcat(boolean b, char c, double d, float f, int i, long lng,
                                           Object obj, String str) {
        return "Benchmark - " + b + ' ' + c + ' ' + d + ' ' + f + ' ' + i + ' ' + lng + ' ' + obj + ' ' + str;
    }

    @Benchmark
    public void javaStringConcat(Blackhole blackhole) {
        blackhole.consume(javaStringConcat(
                BOOLEAN[0], CHAR[0], DOUBLE[0], FLOAT[0], INT[0], LONG[0], OBJ[0], STR[0]));
    }

    private static String stringBuilder(boolean b, char c, double d, float f, int i, long lng,
                                        Object obj, String str) {
        return new StringBuilder()
                .append(b)
                .append(' ')
                .append(c)
                .append(' ')
                .append(d)
                .append(' ')
                .append(f)
                .append(' ')
                .append(i)
                .append(' ')
                .append(lng)
                .append(' ')
                .append(obj)
                .append(' ')
                .append(str)
                .toString();
    }

    @Benchmark
    public void stringBuilder(Blackhole blackhole) {
        blackhole.consume(stringBuilder(
                BOOLEAN[0], CHAR[0], DOUBLE[0], FLOAT[0], INT[0], LONG[0], OBJ[0], STR[0]));
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        blackhole.consume(formatter.format(
                BOOLEAN[0], CHAR[0], DOUBLE[0], FLOAT[0], INT[0], LONG[0], OBJ[0], STR[0]));
    }
}
