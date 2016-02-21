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
```

Then the annotation processor generate following java code based on StringFormatter annotation.

```java
package com.github.imasahiro.stringformatter.example;

import java.lang.String;
import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Named;

@Generated({"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor"})
@Named
public final class Example_Formatter implements Example.Formatter {
    @Inject
    Example_Formatter() {
    }

    public final String formatTo(final String arg0, final String arg1) {
        final StringBuilder sb = new StringBuilder(16);
        sb.append("Hi ");
        sb.append(String.valueOf(arg0));
        sb.append(", my name is ");
        sb.append(String.valueOf(arg1));
        sb.append(".");
        return sb.toString();
    }
}
```
