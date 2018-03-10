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

package com.github.imasahiro.stringformatter.runtime.floats;

import org.junit.Test;

public class GrisuTest {
    @Test
    public void testFormatDouble() throws Exception {
        System.out.println(String.format("%10.20f", Math.PI));
        System.out.println(Grisu.formatDouble(new StringBuilder(), Math.PI));
        System.out.println(String.format("%10.20f", Math.E));
        System.out.println(Grisu.formatDouble(new StringBuilder(), Math.E));
        System.out.println(String.format("%10.20f", 1.0 / 3));
        System.out.println(Grisu.formatDouble(new StringBuilder(), 1.0 / 3));
        System.out.println(String.format("%10.20f", 1234.56789012345678901234567890e26));
        System.out.println(Grisu.formatDouble(new StringBuilder(), 1234.56789012345678901234567890e26));
    }
}
