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

public class Example2 {
    public static void main(String... args) {
        long version = 10;
        long mainVersion = (version - 1) / 100 + 1;
        long minorVersion = (version - 1) % 100;
        System.out.println(new Example2_Formatter().format(mainVersion, minorVersion));
    }

    @AutoStringFormatter
    interface Formatter {
        @Format("V%d.%02d")
        String format(long major, long minor);
    }
}