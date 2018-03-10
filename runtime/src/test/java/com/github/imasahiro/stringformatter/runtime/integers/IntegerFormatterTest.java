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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntegerFormatterTest {
    private static String run(long i, int width, boolean printZero) {
        StringBuilder sb = new StringBuilder();
        IntegerFormatter.formatTo(sb, i, printZero ? IntegerFormatter.PADDED_WITH_ZEROS : 0, width);
        return sb.toString();
    }

    @Test
    public void testFormatToLong() {
        assertEquals(Long.toString(1234567890123456789L), run(1234567890123456789L, 0, false));
        assertEquals(Long.toString(123456789012345678L), run(123456789012345678L, 0, false));
        assertEquals(Long.toString(12345678901234567L), run(12345678901234567L, 0, false));
        assertEquals(Long.toString(1234567890123456L), run(1234567890123456L, 0, false));
        assertEquals(Long.toString(123456789012345L), run(123456789012345L, 0, false));
        assertEquals(Long.toString(12345678901234L), run(12345678901234L, 0, false));
        assertEquals(Long.toString(1234567890123L), run(1234567890123L, 0, false));
        assertEquals(Long.toString(123456789012L), run(123456789012L, 0, false));
        assertEquals(Long.toString(12345678901L), run(12345678901L, 0, false));
        assertEquals(Long.toString(1234567890L), run(1234567890L, 0, false));
        assertEquals(Long.toString(123456789L), run(123456789L, 0, false));
        assertEquals(Long.toString(12345678L), run(12345678L, 0, false));
        assertEquals(Long.toString(1234567L), run(1234567L, 0, false));
        assertEquals(Long.toString(123456L), run(123456L, 0, false));
        assertEquals(Long.toString(12345L), run(12345L, 0, false));
        assertEquals(Long.toString(1234L), run(1234L, 0, false));
        assertEquals(Long.toString(123L), run(123L, 0, false));
        assertEquals(Long.toString(12L), run(12L, 0, false));
        assertEquals(Long.toString(1L), run(1L, 0, false));
        assertEquals(Long.toString(0L), run(0L, 0, false));
    }

    @Test
    public void testFormatToNegativeLong() {
        assertEquals(Long.toString(-1234567890123456789L), run(-1234567890123456789L, 0, false));
        assertEquals(Long.toString(-123456789012345678L), run(-123456789012345678L, 0, false));
        assertEquals(Long.toString(-12345678901234567L), run(-12345678901234567L, 0, false));
        assertEquals(Long.toString(-1234567890123456L), run(-1234567890123456L, 0, false));
        assertEquals(Long.toString(-123456789012345L), run(-123456789012345L, 0, false));
        assertEquals(Long.toString(-12345678901234L), run(-12345678901234L, 0, false));
        assertEquals(Long.toString(-1234567890123L), run(-1234567890123L, 0, false));
        assertEquals(Long.toString(-123456789012L), run(-123456789012L, 0, false));
        assertEquals(Long.toString(-12345678901L), run(-12345678901L, 0, false));
        assertEquals(Long.toString(-1234567890L), run(-1234567890L, 0, false));
        assertEquals(Long.toString(-123456789L), run(-123456789L, 0, false));
        assertEquals(Long.toString(-12345678L), run(-12345678L, 0, false));
        assertEquals(Long.toString(-1234567L), run(-1234567L, 0, false));
        assertEquals(Long.toString(-123456L), run(-123456L, 0, false));
        assertEquals(Long.toString(-12345L), run(-12345L, 0, false));
        assertEquals(Long.toString(-1234L), run(-1234L, 0, false));
        assertEquals(Long.toString(-123L), run(-123L, 0, false));
        assertEquals(Long.toString(-12L), run(-12L, 0, false));
        assertEquals(Long.toString(-1L), run(-1L, 0, false));
    }

    @Test
    public void testFormatToLong_width() {
        assertEquals(String.format("%30d", 1234567890123456789L), run(1234567890123456789L, 30, false));
        assertEquals(String.format("%30d", 123456789012345678L), run(123456789012345678L, 30, false));
        assertEquals(String.format("%30d", 12345678901234567L), run(12345678901234567L, 30, false));
        assertEquals(String.format("%30d", 1234567890123456L), run(1234567890123456L, 30, false));
        assertEquals(String.format("%30d", 123456789012345L), run(123456789012345L, 30, false));
        assertEquals(String.format("%30d", 12345678901234L), run(12345678901234L, 30, false));
        assertEquals(String.format("%30d", 1234567890123L), run(1234567890123L, 30, false));
        assertEquals(String.format("%30d", 123456789012L), run(123456789012L, 30, false));
        assertEquals(String.format("%30d", 12345678901L), run(12345678901L, 30, false));
        assertEquals(String.format("%30d", 1234567890L), run(1234567890L, 30, false));
        assertEquals(String.format("%30d", 123456789L), run(123456789L, 30, false));
        assertEquals(String.format("%30d", 12345678L), run(12345678L, 30, false));
        assertEquals(String.format("%30d", 1234567L), run(1234567L, 30, false));
        assertEquals(String.format("%30d", 123456L), run(123456L, 30, false));
        assertEquals(String.format("%30d", 12345L), run(12345L, 30, false));
        assertEquals(String.format("%30d", 1234L), run(1234L, 30, false));
        assertEquals(String.format("%30d", 123L), run(123L, 30, false));
        assertEquals(String.format("%30d", 12L), run(12L, 30, false));
        assertEquals(String.format("%30d", 1L), run(1L, 30, false));
        assertEquals(String.format("%30d", 0L), run(0L, 30, false));
    }

    @Test
    public void testFormatToNegativeLong_width() {
        assertEquals(String.format("%30d", -1234567890123456789L), run(-1234567890123456789L, 30, false));
        assertEquals(String.format("%30d", -123456789012345678L), run(-123456789012345678L, 30, false));
        assertEquals(String.format("%30d", -12345678901234567L), run(-12345678901234567L, 30, false));
        assertEquals(String.format("%30d", -1234567890123456L), run(-1234567890123456L, 30, false));
        assertEquals(String.format("%30d", -123456789012345L), run(-123456789012345L, 30, false));
        assertEquals(String.format("%30d", -12345678901234L), run(-12345678901234L, 30, false));
        assertEquals(String.format("%30d", -1234567890123L), run(-1234567890123L, 30, false));
        assertEquals(String.format("%30d", -123456789012L), run(-123456789012L, 30, false));
        assertEquals(String.format("%30d", -12345678901L), run(-12345678901L, 30, false));
        assertEquals(String.format("%30d", -1234567890L), run(-1234567890L, 30, false));
        assertEquals(String.format("%30d", -123456789L), run(-123456789L, 30, false));
        assertEquals(String.format("%30d", -12345678L), run(-12345678L, 30, false));
        assertEquals(String.format("%30d", -1234567L), run(-1234567L, 30, false));
        assertEquals(String.format("%30d", -123456L), run(-123456L, 30, false));
        assertEquals(String.format("%30d", -12345L), run(-12345L, 30, false));
        assertEquals(String.format("%30d", -1234L), run(-1234L, 30, false));
        assertEquals(String.format("%30d", -123L), run(-123L, 30, false));
        assertEquals(String.format("%30d", -12L), run(-12L, 30, false));
        assertEquals(String.format("%30d", -1L), run(-1L, 30, false));
    }

    @Test
    public void testFormatToLong_width_padded_with_zero() {
        assertEquals(String.format("%030d", 1234567890123456789L), run(1234567890123456789L, 30, true));
        assertEquals(String.format("%030d", 123456789012345678L), run(123456789012345678L, 30, true));
        assertEquals(String.format("%030d", 12345678901234567L), run(12345678901234567L, 30, true));
        assertEquals(String.format("%030d", 1234567890123456L), run(1234567890123456L, 30, true));
        assertEquals(String.format("%030d", 123456789012345L), run(123456789012345L, 30, true));
        assertEquals(String.format("%030d", 12345678901234L), run(12345678901234L, 30, true));
        assertEquals(String.format("%030d", 1234567890123L), run(1234567890123L, 30, true));
        assertEquals(String.format("%030d", 123456789012L), run(123456789012L, 30, true));
        assertEquals(String.format("%030d", 12345678901L), run(12345678901L, 30, true));
        assertEquals(String.format("%030d", 1234567890L), run(1234567890L, 30, true));
        assertEquals(String.format("%030d", 123456789L), run(123456789L, 30, true));
        assertEquals(String.format("%030d", 12345678L), run(12345678L, 30, true));
        assertEquals(String.format("%030d", 1234567L), run(1234567L, 30, true));
        assertEquals(String.format("%030d", 123456L), run(123456L, 30, true));
        assertEquals(String.format("%030d", 12345L), run(12345L, 30, true));
        assertEquals(String.format("%030d", 1234L), run(1234L, 30, true));
        assertEquals(String.format("%030d", 123L), run(123L, 30, true));
        assertEquals(String.format("%030d", 12L), run(12L, 30, true));
        assertEquals(String.format("%030d", 1L), run(1L, 30, true));
        assertEquals(String.format("%030d", 0L), run(0L, 30, true));
    }

    @Test
    public void testFormatToNegativeLong_width_padded_with_zero() {
        assertEquals(String.format("%030d", -1234567890123456789L), run(-1234567890123456789L, 30, true));
        assertEquals(String.format("%030d", -123456789012345678L), run(-123456789012345678L, 30, true));
        assertEquals(String.format("%030d", -12345678901234567L), run(-12345678901234567L, 30, true));
        assertEquals(String.format("%030d", -1234567890123456L), run(-1234567890123456L, 30, true));
        assertEquals(String.format("%030d", -123456789012345L), run(-123456789012345L, 30, true));
        assertEquals(String.format("%030d", -12345678901234L), run(-12345678901234L, 30, true));
        assertEquals(String.format("%030d", -1234567890123L), run(-1234567890123L, 30, true));
        assertEquals(String.format("%030d", -123456789012L), run(-123456789012L, 30, true));
        assertEquals(String.format("%030d", -12345678901L), run(-12345678901L, 30, true));
        assertEquals(String.format("%030d", -1234567890L), run(-1234567890L, 30, true));
        assertEquals(String.format("%030d", -123456789L), run(-123456789L, 30, true));
        assertEquals(String.format("%030d", -12345678L), run(-12345678L, 30, true));
        assertEquals(String.format("%030d", -1234567L), run(-1234567L, 30, true));
        assertEquals(String.format("%030d", -123456L), run(-123456L, 30, true));
        assertEquals(String.format("%030d", -12345L), run(-12345L, 30, true));
        assertEquals(String.format("%030d", -1234L), run(-1234L, 30, true));
        assertEquals(String.format("%030d", -123L), run(-123L, 30, true));
        assertEquals(String.format("%030d", -12L), run(-12L, 30, true));
        assertEquals(String.format("%030d", -1L), run(-1L, 30, true));
    }
}
