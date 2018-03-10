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

import com.google.common.collect.ImmutableSet;

public class FloatFormatConversionType extends FormatConversionType {
    @Override
    public Set<TypeMirror> getType(Types typeUtil, Elements elementUtil) {
        return ImmutableSet.of(typeUtil.getPrimitiveType(TypeKind.FLOAT),
                               typeUtil.getPrimitiveType(TypeKind.DOUBLE));
    }
}
