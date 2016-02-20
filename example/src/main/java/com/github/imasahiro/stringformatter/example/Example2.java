package com.github.imasahiro.stringformatter.example;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;

public class Example2 {
    @AutoStringFormatter
    interface Formatter {
        @Format("V%d.%02d")
        String format(int major, int minor);
    }

    public static void main(String... args) {
        long version = 10;
        long mainVersion = (version - 1) / 100 + 1;
        long minorVersion = (version - 1) % 100;
        System.out.println(Example2_Formatter.format(mainVersion, minorVersion));
    }
}