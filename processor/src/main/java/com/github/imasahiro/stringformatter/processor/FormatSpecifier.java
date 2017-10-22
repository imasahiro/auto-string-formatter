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

import java.util.Set;

import javax.lang.model.type.TypeMirror;

import com.github.imasahiro.stringformatter.processor.specifier.FormatConversionType;
import com.google.common.base.Joiner;
import com.squareup.javapoet.CodeBlock;

class FormatSpecifier implements FormatString {
    private final int index;
    private final int width;
    private final int precision;
    private final Set<FormatFlag> flags;
    private final FormatConversionType type;

    FormatSpecifier(int index, int width, int precision, Set<FormatFlag> flags,
                    FormatConversionType type) {
        this.index = index;
        this.width = width;
        this.precision = precision;
        this.flags = flags;
        this.type = type;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void emit(CodeBlock.Builder codeBlockBuilder, TypeMirror argumentType) {
        codeBlockBuilder.add(type.emit("arg" + index, width, precision, flags, argumentType));
    }

    public FormatConversionType getConversionType() {
        return type;
    }

    @Override
    public String toString() {
        return "FormatSpecifier(" +
               "index:" + index + "," +
               "width:" + width + "," +
               "precision:" + precision + "," +
               "flags:" + Joiner.on(',').join(flags) +
               ")";
    }

}
