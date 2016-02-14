package com.github.imasahiro.stringformatter.example;

import javax.inject.Qualifier;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;

public class Example2 {
    @StringFormatter(value = "V%d.%02d")
    @Qualifier
    @interface Formatter2 {}

    public static void main(String... args) {
        long version = 10;
        long mainVersion = (version - 1) / 100 + 1;
        long minorVersion = (version - 1) % 100;
        System.out.println(StringFormatter_Formatter2.format(mainVersion, minorVersion));
    }
}