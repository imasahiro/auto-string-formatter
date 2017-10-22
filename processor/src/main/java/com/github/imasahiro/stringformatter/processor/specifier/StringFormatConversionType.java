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

package com.github.imasahiro.stringformatter.processor.specifier;

import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.github.imasahiro.stringformatter.processor.FormatFlag;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.TypeName;

public class StringFormatConversionType extends FormatConversionType {
    private static final TypeName FORMATTABLE_TYPE = TypeName.get(Formattable.class);
    private static final TypeName FORMATTER_TYPE = TypeName.get(Formatter.class);

    private static final Mustache STRING_TEMPLATE =
            new DefaultMustacheFactory().compile("template/string.mustache");
    private static final Mustache FORMATTABLE_TEMPLATE =
            new DefaultMustacheFactory().compile("template/formattable.mustache");

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

    @Override
    public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
        return ImmutableSet.of(elementUtil.getTypeElement(Formattable.class.getCanonicalName()).asType(),
                               elementUtil.getTypeElement(Object.class.getCanonicalName()).asType());
    }

    @Override
    public String emit(String arg, int width, int precision, Set<FormatFlag> flags, TypeMirror argumentType) {
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
}
