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

import com.github.imasahiro.stringformatter.annotation.StringFormatter;
import com.google.testing.compile.JavaFileObjects;

/**
 * {@code StringFormatterTest} tests {@link StringFormatter}.
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter(\"%d\")\n"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final short arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     com.github.imasahiro.stringformatter.runtime.IntegerFormatter.formatTo(sb, arg0, 0, -1);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     com.github.imasahiro.stringformatter.runtime.IntegerFormatter.formatTo(sb, arg0, 0, -1);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final long arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     com.github.imasahiro.stringformatter.runtime.IntegerFormatter.formatTo(sb, arg0, 0, -1);\n"
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter(\"%b\")\n"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter(\"%B\")\n"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter(\"%s\")\n"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.Object;\n"
                         + "import java.lang.String;\n"
                         + "import java.util.Formattable;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
                         + "  }\n"
                         + "\n"
                         + "  public static String format(final Formattable arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder(16);\n"
                         + "     arg0.formatTo(new java.util.Formatter(sb), 0, -1, -1);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final Object arg0) {\n"
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter(\"AA%%AA\")"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter(value = \"%%\", capacity = 128)"
                         + "    @Qualifier\n"
                         + "    @interface Formatter {}\n"
                         + "\n"
                         + "}\n"))
                 .processedWith(new StringFormatterProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString(
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         +
                         "@Generated({\"com.github.imasahiro.stringformatter.processor" +
                         ".StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
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
                         + "import com.github.imasahiro.stringformatter.annotation.StringFormatter;\n"
                         + "\n"
                         + "public class Baz {\n"
                         + "    @StringFormatter("
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
                         "foo.bar.StringFormatter_Formatter",
                         "package foo.bar;\n"
                         + "\n"
                         + "import java.lang.String;\n"
                         + "import javax.annotation.Generated;\n"
                         + "import javax.inject.Inject;\n"
                         + "import javax.inject.Named;\n"
                         + "\n"
                         + "@Generated({\"com.github.imasahiro.stringformatter.processor.StringFormatterProcessor\"})\n"
                         + "@Named\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  @Inject\n"
                         + "  StringFormatter_Formatter() {\n"
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
}
