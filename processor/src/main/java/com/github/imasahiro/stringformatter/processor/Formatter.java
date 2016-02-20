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

import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

class Formatter {
    private final String name;
    private final String format;
    private final int bufferCapacity;
    private final List<TypeMirror> argumentTypes;

    Formatter(String name, String format, int bufferCapacity, List<TypeMirror> argumentTypes) {
        this.name = name;
        this.format = format;
        this.bufferCapacity = bufferCapacity;
        this.argumentTypes = argumentTypes;
    }

    private static List<ParameterSpec> buildParamTypes(List<TypeMirror> argumentTypes) {
        ImmutableList.Builder<ParameterSpec> builder = ImmutableList.builder();
        for (int i = 0; i < argumentTypes.size(); i++) {
            builder.add(ParameterSpec.builder(TypeName.get(argumentTypes.get(i)),
                                              "arg" + i, Modifier.FINAL).build());
        }
        return builder.build();
    }

    private CodeBlock buildBody(List<FormatString> formatStringList, List<TypeMirror> argumentTypes) {
        CodeBlock.Builder builder = CodeBlock.builder()
                                             .add("final StringBuilder sb = new StringBuilder(" +
                                                  bufferCapacity + ");\n");
        int idx = 0;
        for (int i = 0; i < formatStringList.size(); i++) {
            FormatString formatString = formatStringList.get(i);
            if (formatString instanceof FormatSpecifier) {
                formatStringList.get(i).emit(builder, argumentTypes.get(idx++));
            } else {
                formatStringList.get(i).emit(builder, null);
            }
        }
        return builder.add("return sb.toString();\n")
                      .build();
    }

    private void checkArgumentTypes(ProcessingEnvironment processingEnv, List<FormatString> formatStringList,
                                    List<TypeMirror> expectedTypeList) {
        List<FormatSpecifier> formatSpecifiers = FluentIterable.from(formatStringList)
                                                               .filter(FormatSpecifier.class)
                                                               .toList();

        if (formatSpecifiers.size() != expectedTypeList.size()) {
            throw new RuntimeException(name + " cannot not acceptable to " + expectedTypeList);
        }

        Set<List<TypeMirror>> candidateArgumentTypes = Sets.cartesianProduct(
                FluentIterable.from(formatSpecifiers)
                              .transform(e -> e.getConversionType().getType(processingEnv.getTypeUtils(),
                                                                            processingEnv.getElementUtils()))
                              .toList());

        if (!candidateArgumentTypes.stream().anyMatch(
                candidate -> isAssignableArguments(processingEnv, candidate, expectedTypeList))) {
            throw new RuntimeException(name + " cannot not acceptable to " + expectedTypeList);
        }
    }

    private boolean isAssignableArguments(ProcessingEnvironment processingEnv, List<TypeMirror> candidate,
                                          List<TypeMirror> expectedTypeList) {
        Types typeUtils = processingEnv.getTypeUtils();
        for (int i = 0; i < candidate.size(); i++) {
            if (!typeUtils.isAssignable(expectedTypeList.get(i),
                                        candidate.get(i))) {
                return false;
            }
        }
        return true;
    }

    public MethodSpec getMethod(ProcessingEnvironment processingEnv) {
        List<FormatString> formatStringList = FormatParser.parse(format);
        checkArgumentTypes(processingEnv, formatStringList, argumentTypes);
        return MethodSpec.methodBuilder(name)
                         .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                         .addParameters(buildParamTypes(argumentTypes))
                         .addCode(buildBody(formatStringList, argumentTypes))
                         .returns(TypeName.get(String.class))
                         .build();
    }

    @Override
    public String toString() {
        return "Formatter(name:" + name + ", format:" + format + ", bufferCapacity:" + bufferCapacity + ")";
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private String name;
        private int bufferCapacity;
        private String format;
        private ImmutableList<TypeMirror> argumentTypes;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder formatter(String format) {
            this.format = format;
            return this;
        }

        public Builder bufferCapacity(int bufferCapacity) {
            this.bufferCapacity = bufferCapacity;
            return this;
        }

        public Builder argumentTypeNames(ImmutableList<TypeMirror> argumentTypeNames) {
            this.argumentTypes = argumentTypeNames;
            return this;
        }

        public Formatter build() {
            return new Formatter(name, format, bufferCapacity, argumentTypes);
        }

    }
}
