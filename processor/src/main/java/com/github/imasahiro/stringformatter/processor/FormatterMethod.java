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

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import com.github.imasahiro.stringformatter.processor.util.ErrorReporter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

class FormatterMethod {
    private final String name;
    private final String format;
    private final int bufferCapacity;
    private final List<TypeMirror> argumentTypes;
    private final Element element;
    private final ErrorReporter errorReporter;

    FormatterMethod(String name, String format, int bufferCapacity, List<TypeMirror> argumentTypes,
                    Element element, ErrorReporter errorReporter) {
        this.name = name;
        this.format = format;
        this.bufferCapacity = bufferCapacity;
        this.argumentTypes = argumentTypes;
        this.element = element;
        this.errorReporter = errorReporter;
    }

    private static List<ParameterSpec> buildParamTypes(List<TypeMirror> argumentTypes) {
        ImmutableList.Builder<ParameterSpec> builder = ImmutableList.builder();
        for (int i = 0; i < argumentTypes.size(); i++) {
            builder.add(ParameterSpec.builder(TypeName.get(argumentTypes.get(i)),
                                              "arg" + i, Modifier.FINAL).build());
        }
        return builder.build();
    }

    public static Builder builder() {
        return new Builder();
    }

    private CodeBlock buildBody(List<FormatString> formatStringList, List<TypeMirror> argumentTypes) {
        CodeBlock.Builder builder = CodeBlock.builder()
                                             .add("final StringBuilder sb = new StringBuilder(" +
                                                  bufferCapacity + ");\n");
        int idx = 0;
        for (FormatString formatString : formatStringList) {
            if (formatString instanceof FormatSpecifier) {
                formatString.emit(builder, argumentTypes.get(idx++));
            } else {
                formatString.emit(builder, null);
            }
        }
        return builder.add("return sb.toString();\n")
                      .build();
    }

    private void checkArgumentTypes(ProcessingEnvironment processingEnv, List<FormatString> formatStringList,
                                    List<TypeMirror> expectedTypeList) {
        List<FormatSpecifier> formatSpecifiers = formatStringList.stream()
                                                                 .filter(FormatSpecifier.class::isInstance)
                                                                 .map(FormatSpecifier.class::cast)
                                                                 .collect(toImmutableList());

        if (formatSpecifiers.size() != expectedTypeList.size()) {
            throw new RuntimeException(name + " cannot not acceptable to " + expectedTypeList);
        }

        Set<List<TypeMirror>> candidateArgumentTypes = Sets.cartesianProduct(
                formatSpecifiers.stream()
                                .map(FormatSpecifier::getConversionType)
                                .map(e -> e.getType(processingEnv.getTypeUtils(),
                                                    processingEnv.getElementUtils()))
                                .collect(toImmutableList()));

        if (candidateArgumentTypes.stream().noneMatch(
                candidate -> isAssignableArguments(processingEnv, candidate, expectedTypeList))) {
            errorReporter.fatal(name + " cannot not apply to " + expectedTypeList, element);
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
        List<FormatString> formatStringList = FormatParser.parse(format, element, errorReporter);
        checkArgumentTypes(processingEnv, formatStringList, argumentTypes);
        return MethodSpec.methodBuilder(name)
                         .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                         .addParameters(buildParamTypes(argumentTypes))
                         .addCode(buildBody(formatStringList, argumentTypes))
                         .returns(TypeName.get(String.class))
                         .build();
    }

    @Override
    public String toString() {
        return "FormatterMethod(name:" + name + ", format:" + format +
               ", bufferCapacity:" + bufferCapacity + ')';
    }

    static class Builder {
        private String name;
        private int bufferCapacity;
        private String format;
        private ImmutableList<TypeMirror> argumentTypes;
        private Element element;
        private ErrorReporter errorReporter;

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

        public Builder element(Element element) {
            this.element = element;
            return this;
        }

        public Builder errorReporter(ErrorReporter errorReporter) {
            this.errorReporter = errorReporter;
            return this;
        }

        public FormatterMethod build() {
            return new FormatterMethod(name, format, bufferCapacity, argumentTypes, element, errorReporter);
        }
    }
}
