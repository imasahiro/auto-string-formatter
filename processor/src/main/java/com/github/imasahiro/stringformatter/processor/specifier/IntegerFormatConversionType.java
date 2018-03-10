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

import java.util.Set;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.github.imasahiro.stringformatter.processor.FormatFlag;
import com.github.imasahiro.stringformatter.runtime.integers.IntegerFormatter;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class IntegerFormatConversionType extends FormatConversionType {
    private static final String FORMATTER_NAME = IntegerFormatter.class.getCanonicalName();

    private static final Mustache TEMPLATE =
            new DefaultMustacheFactory().compile("template/int.mustache");
    private static final Mustache TEMPLATE_WIDTH =
            new DefaultMustacheFactory().compile("template/int_with_width.mustache");

    private static String convertFlags(Set<FormatFlag> flags) {
        // TODO Support left-justified.
        if (flags.contains(FormatFlag.ZERO)) {
            return String.valueOf(IntegerFormatter.PADDED_WITH_ZEROS);
        }
        return "0";
    }

    @Override
    public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
        return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.SHORT),
                               typeUtil.getPrimitiveType(TypeKind.INT),
                               typeUtil.getPrimitiveType(TypeKind.LONG));
    }

    @Override
    public String emit(String arg, int width, int precision, Set<FormatFlag> flags, TypeMirror argumentType) {
        if (width >= 0) {
            return getCode(TEMPLATE_WIDTH, ImmutableMap.of("FORMATTER_NAME", FORMATTER_NAME,
                                                           "ARG", arg,
                                                           "flags", convertFlags(flags),
                                                           "width", String.valueOf(width)));
        } else {
            return getCode(TEMPLATE, ImmutableMap.of("ARG", arg));
        }
    }
}
