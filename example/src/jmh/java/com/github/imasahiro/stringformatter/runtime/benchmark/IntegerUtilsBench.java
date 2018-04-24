/*
 * Copyright (C) 2018 Masahiro Ide
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

package com.github.imasahiro.stringformatter.runtime.benchmark;

import static com.github.imasahiro.stringformatter.runtime.integers.IntegerUtils.log10;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class IntegerUtilsBench {
    private static final long[] values = {
            1L,
            9L,
            10L,
            11L,
            99L,
            100L,
            500L,
            999L,
            1000L,
            5000L,
            9999L,
            10000L,
            50000L,
            99999L,
            1000000000000000000L,
            5555555555555555555L
    };

    @Benchmark
    public void loop(Blackhole bh) {
        for (long v : values) {
            bh.consume(stringSize(v));
        }
    }

    @Benchmark
    public void array(Blackhole bh) {
        for (long v : values) {
            bh.consume(log10(v));
        }
    }

    private static int stringSize(long x) {
        long p = 10;

        for (int i = 1; i < 19; i++) {
            if (x < p) {
                return i;
            }
            p = 10 * p;
        }

        return 19;
    }
}
