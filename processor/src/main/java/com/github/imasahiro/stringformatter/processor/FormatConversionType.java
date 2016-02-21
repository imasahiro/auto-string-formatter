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

import java.io.StringWriter;
import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.github.imasahiro.stringformatter.runtime.IntegerFormatter;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeName;

public abstract class FormatConversionType {
    abstract Set<TypeMirror> getType(Types typeUtil, Elements elementUtil);

    public String emit(String arg, int width, int precision,
                       Set<FormatFlag> flags, TypeMirror argumentType) {
        return "sb.append(" + arg + ");\n";
    }

    private static String getCode(Mustache template, Map<String, ?> scope) {
        StringWriter sw = new StringWriter();
        template.execute(sw, scope);
        return sw.toString();
    }

    public static class BooleanFormatConversionType extends FormatConversionType {
        private static final Mustache BOOLEAN_TEMPLATE =
                new DefaultMustacheFactory().compile("template/boolean.mustache");
        private static final Mustache BOOLEAN_TEMPLATE_WITH_WIDTH =
                new DefaultMustacheFactory().compile("template/boolean_with_width.mustache");

        @Override
        public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
            return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.BOOLEAN));
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeMirror argumentType) {
            ImmutableMap.Builder<String, Object> scopeBuilder = ImmutableMap.builder();
            scopeBuilder.put("ARG", arg)
                        .put("width", width)
                        .put("TRUE", flags.contains(FormatFlag.UPPER_CASE) ? "TRUE" : "true")
                        .put("FALSE", flags.contains(FormatFlag.UPPER_CASE) ? "FALSE" : "false");
            if (width > Boolean.TRUE.toString().length()) {
                return getCode(BOOLEAN_TEMPLATE_WITH_WIDTH, scopeBuilder.build());
            }
            else {
                return getCode(BOOLEAN_TEMPLATE, scopeBuilder.build());
            }
        }
    }

    public static class CharacterFormatConversionType extends FormatConversionType {
        @Override
        public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
            return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.CHAR));
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeMirror argumentType) {
            return "sb.append(" + arg + ");\n";
        }
    }

    public static class IntegerFormatConversionType extends FormatConversionType {
        private static final String FORMATTER_NAME = IntegerFormatter.class.getCanonicalName();

        private static final Mustache TEMPLATE =
                new DefaultMustacheFactory().compile("template/int.mustache");

        @Override
        public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
            return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.SHORT),
                                   typeUtil.getPrimitiveType(TypeKind.INT),
                                   typeUtil.getPrimitiveType(TypeKind.LONG));
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeMirror argumentType) {
            return getCode(TEMPLATE, ImmutableMap.of("FORMATTER_NAME", FORMATTER_NAME,
                                                     "ARG", arg,
                                                     "flags", convertFlags(flags),
                                                     "width", String.valueOf(width)));
        }

        private static String convertFlags(Set<FormatFlag> flags) {
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
        public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
            return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.FLOAT),
                                   typeUtil.getPrimitiveType(TypeKind.DOUBLE));
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeMirror argumentType) {
            return "sb.append(" + arg + ");\n";
        }
    }

    public static class StringFormatConversionType extends FormatConversionType {
        private static final TypeName FORMATTABLE_TYPE = TypeName.get(Formattable.class);
        private static final TypeName FORMATTER_TYPE = TypeName.get(java.util.Formatter.class);

        private static final Mustache STRING_TEMPLATE =
                new DefaultMustacheFactory().compile("template/string.mustache");
        private static final Mustache FORMATTABLE_TEMPLATE =
                new DefaultMustacheFactory().compile("template/formattable.mustache");

        @Override
        public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
            return ImmutableSet.of(elementUtil.getTypeElement(Formattable.class.getCanonicalName()).asType(),
                                   elementUtil.getTypeElement(Object.class.getCanonicalName()).asType());
        }

        @Override
        public String emit(String arg, int width, int precision, Set<FormatFlag> flags,
                           TypeMirror argumentType) {
            Map<String, ?> scope = ImmutableMap.of("FORMATTER_NAME", FORMATTER_TYPE.toString(),
                                                   "ARG", arg,
                                                   "flags", convertToFormattableFlags(flags),
                                                   "width", String.valueOf(width),
                                                   "%precision%", String.valueOf(precision));

            if (FORMATTABLE_TYPE.equals(TypeName.get(argumentType))) {
                return getCode(FORMATTABLE_TEMPLATE, scope);
            } else {
                return getCode(STRING_TEMPLATE, scope);
            }
        }

        private static String convertToFormattableFlags(Set<FormatFlag> flags) {
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
