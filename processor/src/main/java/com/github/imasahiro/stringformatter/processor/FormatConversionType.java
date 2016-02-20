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

import java.util.*;

import com.github.imasahiro.stringformatter.runtime.IntegerFormatter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeName;

public abstract class FormatConversionType {
    abstract Set<TypeName> getType();

    public String emit(String arg, int width, int precision,
                       Set<FormatFlag> flags, TypeName argumentType) {
        return FormatSpecifier.STRING_BUILDER_NAME + ".append(" + arg + ");\n";
    }

    public static class BooleanFormatConversionType extends FormatConversionType {
        @Override
        public Set<TypeName> getType() {
            return ImmutableSet.of(TypeName.BOOLEAN);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeName argumentType) {
            StringBuilder sb = new StringBuilder();
            if (width > "TRUE".length()) {
                String widthTemplate = "int %ARG%_len = %ARG% ? 4/*true*/ : 5/*false*/;\n" +
                                       "padding(%width% - %ARG%_len);\n";
                sb.append(widthTemplate.replace("%width%", String.valueOf(width)));
            }
            String template = FormatSpecifier.STRING_BUILDER_NAME + ".append(%ARG% ? \"true\" : \"false\");\n";
            if (flags.contains(FormatFlag.UPPER_CASE)) {
                template = template.replace("true", "TRUE")
                                   .replace("false", "FALSE");
            }
            sb.append(template.replace("%ARG%", arg));
            return sb.toString();
        }
    }

    public static class CharacterFormatConversionType extends FormatConversionType {
        @Override
        public Set<TypeName> getType() {
            return ImmutableSet.of(TypeName.CHAR);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeName argumentType) {
            return FormatSpecifier.STRING_BUILDER_NAME + ".append(" + arg + ");\n";
        }
    }

    public static class IntegerFormatConversionType extends FormatConversionType {
        @Override
        public Set<TypeName> getType() {
            return ImmutableSet.of(TypeName.SHORT, TypeName.INT, TypeName.LONG);
        }

        private final String FORMATTER_NAME = IntegerFormatter.class.getCanonicalName();

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeName argumentType) {
            return FORMATTER_NAME + ".formatTo(%BUFFER%, %ARG%, %flags%, %width%);\n"
                    .replace("%BUFFER%", FormatSpecifier.STRING_BUILDER_NAME)
                    .replace("%ARG%", arg)
                    .replace("%flags%", convertFlags(flags))
                    .replace("%width%", String.valueOf(width));
        }

        private String convertFlags(Set<FormatFlag> flags) {
            // TODO Support left-justified.
            if (flags.contains(FormatFlag.ZERO)) {
                return String.valueOf(
                        IntegerFormatter.PADDED_WITH_ZEROS);
            }
            return "0";
        }

    }

    public static class FloatFormatConversionType extends FormatConversionType {
        @Override
        public Set<TypeName> getType() {
            return ImmutableSet.of(TypeName.FLOAT, TypeName.DOUBLE);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeName argumentType) {
            return FormatSpecifier.STRING_BUILDER_NAME + ".append(" + arg + ");\n";
        }
    }

    public static class StringFormatConversionType extends FormatConversionType {
        private static final TypeName FORMATTABLE_TYPE = TypeName.get(Formattable.class);
        private static final TypeName FORMATTER_TYPE = TypeName.get(java.util.Formatter.class);

        @Override
        public Set<TypeName> getType() {
            return ImmutableSet.of(FORMATTABLE_TYPE, TypeName.OBJECT);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeName argumentType) {
            if (FORMATTABLE_TYPE.equals(argumentType)) {
                return "%ARG%.formatTo(new %FORMATTER_TYPE%(%BUFFER%), %flags%, %width%, %precision%);\n"
                        .replace("%FORMATTER_TYPE%", FORMATTER_TYPE.toString())
                        .replace("%BUFFER%", FormatSpecifier.STRING_BUILDER_NAME)
                        .replace("%ARG%", arg)
                        .replace("%flags%", convertToFormattableFlags(flags))
                        .replace("%width%", String.valueOf(width))
                        .replace("%precision%", String.valueOf(precision));
            } else {
                return "%BUFFER%.append(String.valueOf(%ARG%));\n"
                        .replace("%BUFFER%", FormatSpecifier.STRING_BUILDER_NAME)
                        .replace("%ARG%", arg);
            }
        }

        private String convertToFormattableFlags(Set<FormatFlag> flags) {
            ImmutableList.Builder<Integer> flagBuilder = ImmutableList.builder();
            if (flags.contains(FormatFlag.UPPER_CASE)) {
                flagBuilder.add(FormattableFlags.UPPERCASE);
            }
            if (flags.contains(FormatFlag.SHARP)) {
                flagBuilder.add(FormattableFlags.ALTERNATE);
            }
            if (flags.contains(FormatFlag.MINUS)) {
                flagBuilder.add(FormattableFlags.LEFT_JUSTIFY);
            }
            ImmutableList<Integer> formatterFlags = flagBuilder.build();
            if (formatterFlags.isEmpty()) {
                formatterFlags = ImmutableList.of(0);
            }
            return Joiner.on("|").join(formatterFlags);
        }
    }
}
