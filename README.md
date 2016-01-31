# string-formatter

`string-formatter` is a string formatting library for Java, generating optimized formatter methods at compile time with annotation processing.

`string-formatter` has the following features:
* Fast as hand-written code with `StringBuilder`.
* (Mostly) compatible for `String.format`.

## Getting Started
`string-formatter` requires JDK 8 (1.8.0_65 or later) to run annotation processors.

## Synopsis
Just define a format with `@StringFormatter` and `@Qualifier`, and use it.

```java
package com.github.imasahiro.stringprocessor.benchmark;

import javax.inject.Qualifier;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;

public class HelloWorld {
    @StringFormatter(value = "Hi %s, my name is %s.")
    @Qualifier
    @interface Formatter {}

    public static void main(String... args) {
        System.out.println(StringFormatter_Formatter.format("Alice", "Bob"));
    }
}
```

Then the annotation processor generate following java code based on StringFormatter annotation.

```java
package com.github.imasahiro.stringformatter.example;

import java.lang.Object;
import java.lang.String;
import java.util.Formattable;
import javax.annotation.Generated;

@Generated({"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor"})
public final class StringFormatter_Formatter {
  public static String format(final Formattable arg0, final Formattable arg1) {
    final StringBuilder sb = new StringBuilder();
    sb.append("Hi ");
    arg0.formatTo(new java.util.Formatter(sb), 0, -1, -1);
    sb.append(", my name is ");
    arg1.formatTo(new java.util.Formatter(sb), 0, -1, -1);
    sb.append(".");
    return sb.toString();
  }

  public static String format(final Formattable arg0, final Object arg1) {
    final StringBuilder sb = new StringBuilder();
    sb.append("Hi ");
    arg0.formatTo(new java.util.Formatter(sb), 0, -1, -1);
    sb.append(", my name is ");
    sb.append(String.valueOf(arg1));
    sb.append(".");
    return sb.toString();
  }

  public static String format(final Object arg0, final Formattable arg1) {
    final StringBuilder sb = new StringBuilder();
    sb.append("Hi ");
    sb.append(String.valueOf(arg0));
    sb.append(", my name is ");
    arg1.formatTo(new java.util.Formatter(sb), 0, -1, -1);
    sb.append(".");
    return sb.toString();
  }

  public static String format(final Object arg0, final Object arg1) {
    final StringBuilder sb = new StringBuilder();
    sb.append("Hi ");
    sb.append(String.valueOf(arg0));
    sb.append(", my name is ");
    sb.append(String.valueOf(arg1));
    sb.append(".");
    return sb.toString();
  }
}
```
