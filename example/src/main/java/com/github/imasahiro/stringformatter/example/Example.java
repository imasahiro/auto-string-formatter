package com.github.imasahiro.stringformatter.example;

import javax.inject.Qualifier;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;

public class Example {
    @StringFormatter(value = "Hi %s, my name is %s.")
    @Qualifier
    @interface Formatter {}

    public static void main(String... args) {
        System.out.println(StringFormatter_Formatter.format("Alice", "Bob"));
    }
}