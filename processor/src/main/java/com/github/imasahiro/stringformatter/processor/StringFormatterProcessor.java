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

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;
import com.google.auto.common.MoreElements;
import com.squareup.javapoet.JavaFile;

@SupportedAnnotationTypes("com.github.imasahiro.stringformatter.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StringFormatterProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(StringFormatter.class).forEach(element -> {
            Formatter formatter = buildFormatterType(element);
            JavaFile javaFile = JavaFile.builder(formatter.getPackageName(),
                                                 formatter.getType())
                                        .build();
            try (Writer writer = processingEnv.getFiler()
                                              .createSourceFile(formatter.getSourceFileName())
                                              .openWriter()) {
                javaFile.writeTo(writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

    private Formatter buildFormatterType(Element element) {
        StringFormatter fmt = element.getAnnotation(StringFormatter.class);
        String formatterName = "StringFormatter_" + element.getSimpleName();
        return Formatter.builder()
                        .pkg(MoreElements.getPackage(element))
                        .name(formatterName)
                        .formatter(fmt.value())
                        .bufferCapacity(fmt.capacity())
                        .build();
    }

}
