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

public class HexIntegerFormatter {
    public static final int PADDED_WITH_ZEROS = 1;

    public static StringBuilder formatTo(StringBuilder sb, short v, int flags, int width) {
        long unsigned = Math.abs(v);
        unsigned += v < 0 ? 1L << 16 : 0;
        return format0(sb, unsigned, flags, width);
    }

    public static StringBuilder formatTo(StringBuilder sb, int v, int flags, int width) {
        long unsigned = Math.abs(v);
        unsigned += v < 0 ? 1L << 32 : 0;
        return format0(sb, unsigned, flags, width);
    }

    public static StringBuilder formatTo(StringBuilder sb, long v, int flags, int width) {
        return format0(sb, v, flags, width);
    }

    private static StringBuilder format0(StringBuilder sb, long val, int flags, int width) {
        int len = (IntegerUtils.log2(val) + 3) / 4;
        if ((flags & PADDED_WITH_ZEROS) != PADDED_WITH_ZEROS) {
            for (int i = len; i < width; i++) {
                sb.append(' ');
            }
        }
        if ((flags & PADDED_WITH_ZEROS) == PADDED_WITH_ZEROS) {
            for (int i = len; i < width; i++) {
                sb.append('0');
            }
        }
        len *= 4;
        do {
            len -= 4;
            sb.append(Character.forDigit((int) (val >> len) & 0xf, 16));
        } while (len != 0);
        return sb;
    }
}
