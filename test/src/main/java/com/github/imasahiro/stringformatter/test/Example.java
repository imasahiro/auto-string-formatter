package com.github.imasahiro.stringformatter.test;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

public class Example {

    @AutoStringFormatter
    interface Formatter {

        @Format("Hi %s, my name is %s.")
        String formatTo(String friendName, String myName);
    }

    public static void main(String... args) {
        System.out.println(new Example_Formatter().formatTo("Alice", "Bob"));
    }
}
