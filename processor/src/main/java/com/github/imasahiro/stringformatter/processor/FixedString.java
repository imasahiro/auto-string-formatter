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

import javax.lang.model.type.TypeMirror;

import com.squareup.javapoet.CodeBlock.Builder;

class FixedString implements FormatString {
    private final String text;

    FixedString(String text) {
        this.text = text;
    }

    @Override
    public int getIndex() {
        return -1;
    }

    @Override
    public void emit(Builder codeBlockBuilder, TypeMirror ignored) {
        codeBlockBuilder.add("sb.append(\"" + text + "\");\n");
    }

    @Override
    public String toString() {
        return "FixedString(text:" + text + ")";
    }
}
