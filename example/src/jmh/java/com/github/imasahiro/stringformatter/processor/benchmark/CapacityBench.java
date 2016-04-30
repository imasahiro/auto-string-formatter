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

public class CapacityBench {
    private static final int size = 10;

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(String.format(CapacityBenchFormatter.FORMAT, i, i, i, i, i, i));
        }
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        CapacityBenchFormatter.Formatter formatter = new CapacityBenchFormatter_Formatter();
        for (int i = 0; i < size; i++) {
            blackhole.consume(formatter.format(i, i, i, i, i, i));
        }
    }

    @Benchmark
    public void autoStringFormatterCapacity(Blackhole blackhole) {
        CapacityBenchFormatter.FormatterWithCapacity formatter =
                new CapacityBenchFormatter_FormatterWithCapacity();
        for (int i = 0; i < size; i++) {
            blackhole.consume(formatter.format(i, i, i, i, i, i));
        }
    }
}
