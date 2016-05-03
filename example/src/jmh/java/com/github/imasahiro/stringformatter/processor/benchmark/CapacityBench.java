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

public class CapacityBench {
    private static final CapacityBenchFormatter.Formatter formatter = new CapacityBenchFormatter_Formatter();
    private static final CapacityBenchFormatter.FormatterWithCapacity formatterWithCapacity
            = new CapacityBenchFormatter_FormatterWithCapacity();

    private static final Supplier<Integer> integerSupplier = () -> ThreadLocalRandom.current().nextInt();

    @Benchmark
    public void javaStringFormat(Blackhole blackhole) {
        blackhole.consume(String.format(CapacityBenchFormatter.FORMAT,
                                        integerSupplier.get(),
                                        integerSupplier.get(),
                                        integerSupplier.get(),
                                        integerSupplier.get(),
                                        integerSupplier.get(),
                                        integerSupplier.get()));
    }

    @Benchmark
    public void autoStringFormatter(Blackhole blackhole) {
        blackhole.consume(formatter.format(integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get(),
                                           integerSupplier.get()));
    }

    @Benchmark
    public void autoStringFormatterCapacity(Blackhole blackhole) {
        blackhole.consume(formatterWithCapacity.format(integerSupplier.get(),
                                                       integerSupplier.get(),
                                                       integerSupplier.get(),
                                                       integerSupplier.get(),
                                                       integerSupplier.get(),
                                                       integerSupplier.get()));
    }
}
