package com.github.imasahiro.stringprocessor.benchmark;

import javax.inject.Qualifier;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;

public class Benchmark {
    private static final String FORMAT = "Hi %s; Hi to you %s";

    @StringFormatter(value = FORMAT)
    @Qualifier
    @interface Formatter {}

    public static void main(String... args) {
        for (int i = 0; i < 100; i++) {
            f(10);
        }
        for (int i = 0; i < 100; i++) {
            f(100000);
        }
    }

    public static void f(int n) {
        long start = System.currentTimeMillis();
        String s = null;
        for (int i = 0; i < n; i++) {
            s = javaStringFormat(FORMAT, i);
        }

        long end = System.currentTimeMillis();
        System.out.println("Format = " + end - start + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            s = javaStringConcat(i);
        }
        end = System.currentTimeMillis();
        System.out.println("Concatenation = " + end - start + " millisecond");

        start = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            s = stringBuilder(i);
        }

        end = System.currentTimeMillis();
        System.out.println("String Builder = " + end - start + " millisecond");

        start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            s = StringFormatter_Formatter.format(i, i * 2);
        }
        end = System.currentTimeMillis();
        System.out.println("StringFormatter = " + end - start + " millisecond");
    }

    private static String stringBuilder(int i) {
        StringBuilder bldString = new StringBuilder("Hi ");
        bldString.append(i);
        bldString.append("; Hi to you ");
        bldString.append(i * 2);
        return bldString.toString();
    }

    private static String javaStringConcat(int i) {
        String s = "Hi ";
        s += i;
        s += "; Hi to you ";
        s += i * 2;
        return s;
    }

    private static String javaStringFormat(String formatString, int i) {
        return String.format(formatString, i, i * 2);
    }
}
