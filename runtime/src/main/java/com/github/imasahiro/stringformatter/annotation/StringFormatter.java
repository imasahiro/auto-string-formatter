package com.github.imasahiro.stringformatter.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ANNOTATION_TYPE })
@Retention(RetentionPolicy.SOURCE)
public @interface StringFormatter {
    String value();
}
