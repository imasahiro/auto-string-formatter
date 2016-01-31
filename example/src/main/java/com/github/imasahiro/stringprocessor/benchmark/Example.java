package com.github.imasahiro.stringprocessor.benchmark;

import javax.inject.Qualifier;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;

public class Example {
    @StringFormatter(value = "Hello %s!")
    @Qualifier
    @interface Formatter1 {}

    static public void main(String... args) {
        System.out.println(StringFormatter_Formatter1.format("Bob"));
    }
}
