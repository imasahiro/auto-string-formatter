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

public class Capacity {
    private static final String FORMAT = "%32d%32d%32d%32d%32d%32d";

    @AutoStringFormatter
    interface Formatter {
        @Format(value = FORMAT)
        String format(int a, int b, int c, int d, int e, int g);
    }

    @AutoStringFormatter
    interface FormatterWithCapacity {
        @Format(value = FORMAT, capacity = 32 * 6)
        String format(int a, int b, int c, int d, int e, int g);
    }

    public static void main(String... args) throws IOException, RunnerException {
        Main.main("-wi 3 -i 5 -f 2".split(" "));
    }

    private static final int size = 10;

    @org.openjdk.jmh.annotations.Benchmark
    public void javaStringFormat() {
        String s = null;
        for (int i = 0; i < size; i++) {
            s = String.format(FORMAT, i, i, i, i, i, i);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void autoStringFormatter() {
        String s = null;
        Formatter formatter = new Capacity_Formatter();
        for (int i = 0; i < size; i++) {
            s = formatter.format(i, i, i, i, i, i);
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void autoStringFormatterCapacity() {
        String s = null;
        FormatterWithCapacity formatter = new Capacity_FormatterWithCapacity();
        for (int i = 0; i < size; i++) {
            s = formatter.format(i, i, i, i, i, i);
        }
    }
}
