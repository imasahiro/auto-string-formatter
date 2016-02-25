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
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.inject.Named;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;
import com.google.auto.common.MoreElements;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import jp.skypencil.guava.stream.GuavaCollectors;

@SupportedAnnotationTypes("com.github.imasahiro.stringformatter.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StringFormatterProcessor extends AbstractProcessor {

    private static final TypeName JAVA_LANG_STRING = TypeName.get(String.class);
    private ErrorReporter errorReporter;

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        errorReporter = new ErrorReporter(processingEnv.getMessager());
        Set<? extends Element> annotatedElements =
                roundEnv.getElementsAnnotatedWith(AutoStringFormatter.class);

        try {
            ElementFilter.typesIn(annotatedElements).forEach(
                    typeElement -> {
                        List<Formatter> formatterList = buildFormatter(typeElement);
                        PackageElement packageElement = MoreElements.getPackage(typeElement);
                        String className = generateClassName(typeElement);
                        String packageName = packageElement.getQualifiedName().toString();
                        String sourceName = packageElement + "." + className;

                        JavaFile javaFile = JavaFile.builder(packageName, buildClass(typeElement, className,
                                                                                     formatterList))
                                                    .build();
                        try (Writer writer = processingEnv.getFiler()
                                                          .createSourceFile(sourceName)
                                                          .openWriter()) {
                            javaFile.writeTo(writer);
                        } catch (IOException ignored) {
                            errorReporter.fatal("Cannot write java file to " + className, typeElement);
                        }
                    });
            return true;
        } catch (AbortProcessingException ignored) {
        }
        return false;
    }

    private TypeSpec buildClass(TypeElement superInterface, String className, List<Formatter> formatterList) {
        TypeSpec.Builder builder =
                TypeSpec.classBuilder(className)
                        .addSuperinterface(TypeName.get(superInterface.asType()))
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addAnnotation(AnnotationSpec.builder(Generated.class)
                                                     .addMember("value", "{$S}", getClass().getCanonicalName())
                                                     .build())
                        .addAnnotation(AnnotationSpec.builder(Named.class).build());
        formatterList.forEach(formatter -> builder.addMethod(formatter.getMethod(processingEnv)));
        return builder.build();
    }

    private static String generateClassName(TypeElement type) {
        String name = type.getSimpleName().toString();
        while (type.getEnclosingElement() instanceof TypeElement) {
            type = (TypeElement) type.getEnclosingElement();
            name = type.getSimpleName() + "_" + name;
        }
        return name;
    }

    private TypeMirror getTypeMirror(Class<?> c) {
        return processingEnv.getElementUtils().getTypeElement(c.getName()).asType();
    }

    private static boolean isInterface(TypeElement type) {
        return type.getKind() == ElementKind.INTERFACE;
    }

    private static List<ExecutableElement> filterFormatAnnotatedMethods(Set<ExecutableElement> methods) {
        ImmutableList.Builder<ExecutableElement> targetMethods = ImmutableList.builder();
        methods.stream()
               .filter(method -> JAVA_LANG_STRING.equals(TypeName.get(method.getReturnType())) &&
                                 method.getAnnotation(Format.class) != null)
               .forEach(targetMethods::add);
        return targetMethods.build();
    }

    private List<Formatter> buildFormatter(TypeElement element) {
        AutoStringFormatter type = element.getAnnotation(AutoStringFormatter.class);
        if (!isInterface(element)) {
            errorReporter.warn("@" + AutoStringFormatter.class.getName() +
                               "only applies to interfaces. " + type, element);
            return ImmutableList.of();
        }
        return filterFormatAnnotatedMethods(
                MoreElements.getLocalAndInheritedMethods(element, processingEnv.getElementUtils()))
                .stream()
                .map(this::buildFormatter)
                .collect(GuavaCollectors.toImmutableList());
    }

    private Formatter buildFormatter(ExecutableElement method) {
        Format fmt = method.getAnnotation(Format.class);
        return Formatter.builder()
                        .name(method.getSimpleName().toString())
                        .formatter(fmt.value())
                        .bufferCapacity(fmt.capacity())
                        .argumentTypeNames(method.getParameters().stream()
                                                 .map(Element::asType)
                                                 .collect(GuavaCollectors.toImmutableList()))
                        .element(method)
                        .errorReporter(errorReporter)
                        .build();
    }
}
