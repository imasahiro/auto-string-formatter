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

package com.github.imasahiro.stringformatter.runtime.integers;

final class IntegerUtils {
    private static final long[] powersOf10 = {
            1L,
            10L,
            100L,
            1000L,
            10000L,
            100000L,
            1000000L,
            10000000L,
            100000000L,
            1000000000L,
            10000000000L,
            100000000000L,
            1000000000000L,
            10000000000000L,
            100000000000000L,
            1000000000000000L,
            10000000000000000L,
            100000000000000000L,
            1000000000000000000L,
            Long.MAX_VALUE
    };
    // maxLog10ForLeadingZeros[i] == floor(log10(2^(Long.SIZE - i)))
    private static final byte[] maxLog10ForLeadingZeros = {
            19, 18, 18, 18, 18, 17, 17, 17, 16, 16, 16, 15, 15, 15, 15, 14, 14, 14, 13, 13, 13, 12, 12,
            12, 12, 11, 11, 11, 10, 10, 10, 9, 9, 9, 9, 8, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 4, 4, 4,
            3, 3, 3, 3, 2, 2, 2, 1, 1, 1, 0, 0, 0
    };

    private IntegerUtils() {}

    public static int log2(long v) {
        if (v != 0) {
            return Long.SIZE - Long.numberOfLeadingZeros(v);
        } else {
            return 1;
        }
    }

    public static int log10(long unsigned) {
        if (unsigned != 0) {
            int digits = maxLog10ForLeadingZeros[Long.numberOfLeadingZeros(unsigned)];
            return digits + (unsigned >= powersOf10[digits] ? 1 : 0);
        } else {
            return 1;
        }
    }
}
