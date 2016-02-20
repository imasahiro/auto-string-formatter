package com.github.imasahiro.stringformatter.example;

import javax.inject.Qualifier;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

public class Example {
    @AutoStringFormatter
    interface Formatter {
        @Format("Hi %s, my name is %s.")
        String formatTo(String myName, String frientName);
    }

    public static void main(String... args) {
        System.out.println(Example_Formatter.formatTo("Alice", "Bob"));
    }
}