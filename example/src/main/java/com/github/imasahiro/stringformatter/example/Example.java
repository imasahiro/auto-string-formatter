package com.github.imasahiro.stringformatter.example;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

public class Example {
    @AutoStringFormatter
    interface Formatter {
        @Format("Hi %s, my name is %s.")
        String formatTo(String myName, String frientName);
    }

    public static void main(String... args) {
        System.out.println(new Example_Formatter().formatTo("Alice", "Bob"));
    }
}