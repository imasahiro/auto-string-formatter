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
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class CapacityBench {
    private static final CapacityBenchFormatter.Formatter formatter = new CapacityBenchFormatter_Formatter();
    private static final CapacityBenchFormatter.FormatterWithCapacity formatterWithCapacity
            = new CapacityBenchFormatter_FormatterWithCapacity();

    private static final int[] VALUES = new int[] { 1, 10000000, -10000000, 555, 19032313, 14142, 0 };

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        blackhole.consume(String.format(CapacityBenchFormatter.FORMAT,
                                        VALUES[0],
                                        VALUES[1],
                                        VALUES[2],
                                        VALUES[3],
                                        VALUES[4],
                                        VALUES[5]));
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        blackhole.consume(formatter.format(VALUES[0],
                                           VALUES[1],
                                           VALUES[2],
                                           VALUES[3],
                                           VALUES[4],
                                           VALUES[5]));
    }

    @Benchmark
    public void autoStringFormatterCapacity(Blackhole blackhole) {
        blackhole.consume(formatterWithCapacity.format(VALUES[0],
                                                       VALUES[1],
                                                       VALUES[2],
                                                       VALUES[3],
                                                       VALUES[4],
                                                       VALUES[5]));
    }
}
