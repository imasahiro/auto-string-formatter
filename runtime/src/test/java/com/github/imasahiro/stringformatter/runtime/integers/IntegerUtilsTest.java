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

public class IntegerUtilsTest {

    @Test
    public void testLog10() throws Exception {
        assertEquals(1, IntegerUtils.log10(1));
        assertEquals(1, IntegerUtils.log10(9));

        assertEquals(2, IntegerUtils.log10(10));
        assertEquals(2, IntegerUtils.log10(11));
        assertEquals(2, IntegerUtils.log10(99));

        assertEquals(3, IntegerUtils.log10(100));
        assertEquals(3, IntegerUtils.log10(500));
        assertEquals(3, IntegerUtils.log10(999));

        assertEquals(4, IntegerUtils.log10(1000));
        assertEquals(4, IntegerUtils.log10(5000));
        assertEquals(4, IntegerUtils.log10(9999));

        assertEquals(5, IntegerUtils.log10(10000));
        assertEquals(5, IntegerUtils.log10(50000));
        assertEquals(5, IntegerUtils.log10(99999));

        assertEquals(19, IntegerUtils.log10(1000000000000000000L));
        assertEquals(19, IntegerUtils.log10(5555555555555555555L));
        assertEquals(19, IntegerUtils.log10(Long.MAX_VALUE));
    }
}
