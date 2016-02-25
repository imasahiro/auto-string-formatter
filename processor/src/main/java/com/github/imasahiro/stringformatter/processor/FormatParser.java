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
package com.github.imasahiro.stringformatter.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

public class FormatParser {
    // %[index][flags][width][.precision][t]conversion
    private static final String FORMAT_SPECIFIER =
            "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";

    private static final Pattern FORMAT_SPECIFIER_PATTERN = Pattern.compile(FORMAT_SPECIFIER);

    /**
     * Parse format specifiers in the format string.
     */
    public static List<FormatString> parse(String fmt, Element element, ErrorReporter errorReporter) {
        ArrayList<FormatString> formatStrings = new ArrayList<>();
        Matcher m = FORMAT_SPECIFIER_PATTERN.matcher(fmt);
        int index = 0;
        for (int i = 0; i < fmt.length(); ) {
            if (m.find(i)) {
                if (m.start() != i) {
                    formatStrings.add(FixedString.of(fmt, i, m.start()));
                }
                FormatString specifier = new FormatStringBuilder(element, errorReporter).format(fmt)
                                                                                        .matcher(m)
                                                                                        .index(index)
                                                                                        .build();
                if (specifier instanceof FormatSpecifier && index == specifier.getIndex()) {
                    index++;
                }
                formatStrings.add(specifier);
                i = m.end();
            } else {
                formatStrings.add(FixedString.of(fmt, i, fmt.length()));
                break;
            }
        }
        return formatStrings;
    }
}
