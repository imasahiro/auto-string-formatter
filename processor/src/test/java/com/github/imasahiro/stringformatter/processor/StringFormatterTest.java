/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>.
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
                         + "\n"
                         + "@Generated({\"StringFormatterProcessor\"})\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  public static String format(final short arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final long arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(arg0);\n"
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
                         + "\n"
                         + "@Generated({\"StringFormatterProcessor\"})\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  public static String format(final boolean arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
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
                         + "\n"
                         + "@Generated({\"StringFormatterProcessor\"})\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  public static String format(final boolean arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
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
                         + "\n"
                         + "@Generated({\"StringFormatterProcessor\"})\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  public static String format(final Formattable arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     arg0.formatTo(new java.util.Formatter(sb), 0, -1, -1);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final Object arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
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
                         + "\n"
                         + "@Generated({\"StringFormatterProcessor\"})\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  public static String format() {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(\"AA\");\n"
                         + "     sb.append(\"%\");\n"
                         + "     sb.append(\"AA\");\n"
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
                         + "\n"
                         + "@Generated({\"StringFormatterProcessor\"})\n"
                         + "public final class StringFormatter_Formatter {\n"
                         + "  public static String format(final short arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final int arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "  public static String format(final long arg0) {\n"
                         + "     final StringBuilder sb = new StringBuilder();\n"
                         + "     sb.append(arg0);\n"
                         + "     return sb.toString();\n"
                         + "  }\n"
                         + "}"));
    }
}
