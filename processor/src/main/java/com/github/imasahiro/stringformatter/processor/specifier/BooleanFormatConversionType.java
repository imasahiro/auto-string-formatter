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
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class BooleanFormatConversionType extends FormatConversionType {
    private static final Mustache BOOLEAN_TEMPLATE =
            new DefaultMustacheFactory().compile("template/boolean.mustache");
    private static final Mustache BOOLEAN_TEMPLATE_WITH_WIDTH =
            new DefaultMustacheFactory().compile("template/boolean_with_width.mustache");

    @Override
    public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
        return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.BOOLEAN));
    }

    @Override
    public String emit(String arg, int width, int precision, Set<FormatFlag> flags, TypeMirror argumentType) {
        ImmutableMap<String, Object> scopeBuilder = ImmutableMap.of(
                "ARG", arg,
                "width", width,
                "TRUE", flags.contains(FormatFlag.UPPER_CASE) ? "TRUE" : "true",
                "FALSE", flags.contains(FormatFlag.UPPER_CASE) ? "FALSE" : "false");
        if (width > Boolean.TRUE.toString().length()) {
            return getCode(BOOLEAN_TEMPLATE_WITH_WIDTH, scopeBuilder);
        } else {
            return getCode(BOOLEAN_TEMPLATE, scopeBuilder);
        }
    }
}
