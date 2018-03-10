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
package com.github.imasahiro.stringformatter.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation to specify the string format. A simple example:
 * <pre>{@code
 * &#64;AutoStringFormatter
 * interface IdFormatter {
 *     &#64;Format("id%08d")
 *     String formatTo(int id);
 * }
 * }</pre>
 */
@Target(METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Format {
    /**
     *  Format string. See <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#syntax">
     *  format syntax</a> for format string syntax.
     */
    String value();

    /**
     * The initial capacity of a buffer. Default is the default capacity of {@link StringBuilder} buffer
     * ({@code 16}).
     */
    int capacity() default 16;
}
