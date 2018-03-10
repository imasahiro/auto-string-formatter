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

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

/**
 * Definition formatters to benchmark with/without capacity.
 */
public final class CapacityBenchFormatter {
    public static final String FORMAT = "%32d%32d%32d%32d%32d%32d";

    private CapacityBenchFormatter() {}

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
}
