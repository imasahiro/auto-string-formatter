/*
 * Copyright (C) 2016 Masahiro Ide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.imasahiro.stringformatter.processor;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import org.junit.Ignore;
import org.junit.Test;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;
import com.google.testing.compile.JavaFileObjects;

/**
 * Tests for {@link AutoStringFormatter} and {@link Format}.
 */
public final class StringFormatterTest {
    private static final String GENERATED_ANNOTATION =
            "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})";

    @Test
    public void testProcess_integer() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"%d\")",
                         "    String format(int d);",
                         "  }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format(final int arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(arg0);",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_hex_integer() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"%x\")",
                         "    String format(int d);",
                         "  }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format(final int arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     com.github.imasahiro.stringformatter.runtime.integers." +
                         "HexIntegerFormatter.formatTo(sb, arg0, 0, -1);",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_boolean_lowerCase() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"%b\")",
                         "    String format(boolean b);",
                         "  }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format(final boolean arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(arg0 ? \"true\" : \"false\");",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_boolean_upperCase() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"%B\")",
                         "    String format(boolean b);",
                         "  }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format(final boolean arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(arg0 ? \"TRUE\" : \"FALSE\");",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_string() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"%s\")",
                         "    String format(int d);",
                         "  }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format(final int arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(String.valueOf(arg0));",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_percent() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"AA%%AA\")",
                         "    String format();",
                         "  }",
                         "",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format() {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(\"AA\");",
                         "     sb.append(\"%\");",
                         "     sb.append(\"AA\");",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_capacity() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(value = \"%%\", capacity = 128)",
                         "    String format();",
                         "  }",
                         "",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format() {",
                         "     final StringBuilder sb = new StringBuilder(128);",
                         "     sb.append(\"%\");",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Ignore("TODO Update test case")
    @Test
    public void testProcess_many_formatter() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "    @AutoStringFormatter(",
                         "    \"Hi %s, my name is %s %s and I am %d years old.\" +",
                         "    \"My body body fat percentage is %f%%.\")",
                         "    @Qualifier",
                         "    @interface Formatter {}",
                         "",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String format(final short arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(arg0);",
                         "     return sb.toString();",
                         "  }",
                         "  public final String format(final int arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(arg0);",
                         "     return sb.toString();",
                         "  }",
                         "  public final String format(final long arg0) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     sb.append(arg0);",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "    @AutoStringFormatter",
                         "    interface Formatter {",
                         "        @Format(\"%02d:%02d\")",
                         "        String formatTime(int hours, int minutes);",
                         "    }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;",
                         "",
                         "import java.lang.String;",
                         "import javax.annotation.Generated;",
                         "import javax.inject.Named;",
                         "",
                         GENERATED_ANNOTATION,
                         "@Named",
                         "public final class Baz_Formatter implements Baz.Formatter {",
                         "  public final String formatTime(final int arg0, final int arg1) {",
                         "     final StringBuilder sb = new StringBuilder(16);",
                         "     com.github.imasahiro.stringformatter.runtime.integers." +
                         "IntegerFormatter.formatTo(sb, arg0, 1, 2);",
                         "     sb.append(\":\");",
                         "     com.github.imasahiro.stringformatter.runtime.integers." +
                         "IntegerFormatter.formatTo(sb, arg1, 1, 2);",
                         "     return sb.toString();",
                         "  }",
                         "}"));
    }

    @Test
    public void testProcess_not_acceptable() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceLines(
                         "foo.bar.Baz",
                         "package foo.bar;",
                         "",
                         "import javax.inject.Qualifier;",
                         "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;",
                         "import com.github.imasahiro.stringformatter.annotation.Format;",
                         "",
                         "public class Baz {",
                         "  @AutoStringFormatter",
                         "  interface Formatter {",
                         "    @Format(\"%d\")",
                         "    String format(String d);",
                         "  }",
                         "}"))
                 .processedWith(new StringFormatterProcessor())
                 .failsToCompile()
                 .withErrorContaining(" cannot not apply to ");
    }
}
