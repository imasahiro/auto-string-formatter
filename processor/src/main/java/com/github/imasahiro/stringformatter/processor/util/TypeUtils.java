package com.github.imasahiro.stringformatter.processor.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import com.squareup.javapoet.TypeName;

public class TypeUtils {
    private TypeUtils() {}

    public static TypeMirror getTypeMirror(ProcessingEnvironment processingEnv, Class<?> c) {
        return processingEnv.getElementUtils().getTypeElement(c.getName()).asType();
    }

    public static boolean isInterface(TypeElement type) {
        return type.getKind() == ElementKind.INTERFACE;
    }

    public static String generateClassName(TypeElement type) {
        String name = type.getSimpleName().toString();
        while (type.getEnclosingElement() instanceof TypeElement) {
            type = (TypeElement) type.getEnclosingElement();
            name = type.getSimpleName() + "_" + name;
        }
        return name;
    }

    public static String getFullyQualifiedName(Class<?> clazz) {
        return clazz.getCanonicalName();
    }

    public static boolean isVoidType(TypeName type) {
        return TypeName.get(Void.class).equals(type);
    }

    public static boolean isByteArrayType(TypeName type) {
        return TypeName.get(byte[].class).equals(type);
    }
}

