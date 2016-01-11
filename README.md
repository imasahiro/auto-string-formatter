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
