package com.github.imasahiro.stringformatter.processor;

import java.lang.reflect.Type;
import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public abstract class FormatConversionType {
    abstract Set<Type> getType();

    public String emit(String arg, int width, int precision, Set<FormatFlag> flags, Type argumentType) {
        return FormatSpecifier.STRING_BUILDER_NAME + ".append(" + arg + ");\n";
    }

    public static class BooleanFormatConversionType extends FormatConversionType {
        @Override
        public Set<Type> getType() {
            return ImmutableSet.of(boolean.class);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags, Type argumentType) {
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
        public Set<Type> getType() {
            return ImmutableSet.of(char.class);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags, Type argumentType) {
            return FormatSpecifier.STRING_BUILDER_NAME + ".append(" + arg + ");\n";
        }
    }

    public static class IntegerFormatConversionType extends FormatConversionType {
        @Override
        public Set<Type> getType() {
            return ImmutableSet.of(short.class, int.class, long.class);
        }

        private final String FORMATTER_NAME = "com.github.imasahiro.stringformatter.runtime.IntegerFormatter";

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags, Type argumentType) {
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
                        com.github.imasahiro.stringformatter.runtime.IntegerFormatter.PADDED_WITH_ZEROS);
            }
            return "0";
        }

    }

    public static class FloatFormatConversionType extends FormatConversionType {
        @Override
        public Set<Type> getType() {
            return ImmutableSet.of(float.class, double.class);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags, Type argumentType) {
            return FormatSpecifier.STRING_BUILDER_NAME + ".append(" + arg + ");\n";
        }
    }

    public static class StringFormatConversionType extends FormatConversionType {
        @Override
        public Set<Type> getType() {
            return ImmutableSet.of(Formattable.class, Object.class);
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags, Type argumentType) {
            if (argumentType == Formattable.class) {
                return "%ARG%.formatTo(new java.util.Formatter(%BUFFER%), %flags%, %width%, %precision%);\n"
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
