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

public class Example {
    public static void main(String... args) {
        System.out.println(new Example_Formatter().formatTo("Alice", "Bob"));
    }

    @AutoStringFormatter
    interface Formatter {
        @Format("Hi %s, my name is %s.")
        String formatTo(String myName, String frientName);
    }
}