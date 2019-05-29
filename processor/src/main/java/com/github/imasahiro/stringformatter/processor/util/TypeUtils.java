/*
 * Copyright (C) 2018 Masahiro Ide
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

package com.github.imasahiro.stringformatter.processor.util;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Utility methods for generating types.
 */
public final class TypeUtils {
    private TypeUtils() {
    }

    public static boolean isInterface(TypeElement type) {
        return type.getKind() == ElementKind.INTERFACE;
    }

    /**
     * Generates a type name from {@code type}.
     */
    public static String generateClassName(TypeElement type) {
        String name = type.getSimpleName().toString();
        while (type.getEnclosingElement() instanceof TypeElement) {
            type = (TypeElement) type.getEnclosingElement();
            name = type.getSimpleName() + "_" + name;
        }
        return name;
    }
}

