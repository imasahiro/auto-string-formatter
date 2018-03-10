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

package com.github.imasahiro.stringformatter.example;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

/**
 * An example usage of {@link AutoStringFormatter}.
 */
@SuppressWarnings({ "checkstyle:UncommentedMain", "checkstyle:HideUtilityClassConstructor" })
public class FormatHexId {
    /**
     * Entry point.
     */
    public static void main(String... args) {
        long upperId = 0x0123456789abcdefL;
        long lowerId = 0x0123456789abcdefL;
        System.out.println(new FormatHexId_Formatter().formatTo(upperId, lowerId));
    }

    @AutoStringFormatter
    interface Formatter {
        @Format(value = "%016x%016x", capacity = 32)
        String formatTo(long upper, long lower);
    }
}
