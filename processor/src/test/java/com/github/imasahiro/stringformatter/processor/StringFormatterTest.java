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

import org.junit.Test;

import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;
import com.github.imasahiro.stringformatter.annotation.Format;
import com.google.testing.compile.JavaFileObjects;

/**
 * Tests for {@link AutoStringFormatter} and {@link Format}.
 */
public final class StringFormatterTest {
    @Test
    public void testProcess_integer() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter\n"
                         + "  interface Formatter {\n"
                         + "    @Format(\"%d\")\n"
                         + "    String format(int d);\n"
                         + "  }\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     com.github.imasahiro.stringformatter.runtime.IntegerFormatter.formatTo(sb, arg0, 0, -1);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess_hex_integer() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter\n"
                         + "  interface Formatter {\n"
                         + "    @Format(\"%x\")\n"
                         + "    String format(int d);\n"
                         + "  }\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     com.github.imasahiro.stringformatter.runtime.HexIntegerFormatter.formatTo(sb, arg0, 0, -1);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess_boolean_lowerCase() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter\n"
                         + "  interface Formatter {\n"
                         + "    @Format(\"%b\")\n"
                         + "    String format(boolean b);\n"
                         + "  }\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final boolean arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(arg0 ? \"true\" : \"false\");\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess_boolean_upperCase() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter\n"
                         + "  interface Formatter {\n"
                         + "    @Format(\"%B\")\n"
                         + "    String format(boolean b);\n"
                         + "  }\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final boolean arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(arg0 ? \"TRUE\" : \"FALSE\");\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess_string() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter\n"
                         + "  interface Formatter {\n"
                         + "    @Format(\"%s\")\n"
                         + "    String format(int d);\n"
                         + "  }\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(String.valueOf(arg0));\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess_percent() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter"
                         + "  interface Formatter {\n"
                         + "    @Format(\"AA%%AA\")\n"
                         + "    String format();\n"
                         + "  }\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format() {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(\"AA\");\n"
                         + "     sb.append(\"%\");\n"
                         + "     sb.append(\"AA\");\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess_capacity() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "  @AutoStringFormatter"
                         + "  interface Formatter {\n"
                         + "    @Format(value = \"%%\", capacity = 128)\n"
                         + "    String format();\n"
                         + "  }\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         +
                         "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format() {\n"
                         + "     final StringBuilder sb = new StringBuilder(128);\n"
                         + "     sb.append(\"%\");\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    public void testProcess_many_formatter() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @AutoStringFormatter("
                         + "    \"Hi %s, my name is %s %s and I am %d years old.\" +"
                         + "    \"My body body fat percentage is %f%%.\")"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final short arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final long arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }

    @Test
    public void testProcess() throws Exception {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forSourceString(
                         "foo.bar.Baz",
                         "package foo.bar;\n"
                         + "\n"
                         + "import javax.inject.Qualifier;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.AutoStringFormatter;\n"
                         + "import com.github.imasahiro.stringformatter.annotation.Format;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @AutoStringFormatter\n"
                         + "    interface Formatter {\n"
                         + "        @Format(\"%02d:%02d\")\n"
                         + "        String formatTime(int hours, int minutes);\n"
                         + "    }\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.Baz_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class Baz_Formatter {\n"
                         + "  @Inject\n"
                         + "  Baz_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String formatTime(final int arg0, final int arg1) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     com.github.imasahiro.stringformatter.runtime.IntegerFormatter.formatTo(sb, arg0, 1, 2);\n"
                         + "     sb.append(\":\");\n"
                         + "     com.github.imasahiro.stringformatter.runtime.IntegerFormatter.formatTo(sb, arg1, 1, 2);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }
}
