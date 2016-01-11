package com.github.imasahiro.stringformatter.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.github.imasahiro.stringformatter.annotation.StringFormatter;
import com.google.auto.common.MoreElements;
import com.squareup.javapoet.JavaFile;

@SupportedAnnotationTypes("com.github.imasahiro.stringformatter.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class StringFormatterProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(StringFormatter.class).forEach(element -> {
            System.out.println(element.toString());
            Formatter formatter = buildFormatterType(element);
            JavaFile javaFile = JavaFile.builder(formatter.getPackageName(),
                                                 formatter.getType())
                                        .build();
            try (Writer writer = processingEnv.getFiler()
                                              .createSourceFile(formatter.getSourceFileName())
                                              .openWriter()) {
                javaFile.writeTo(writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }

    private Formatter buildFormatterType(Element element) {
        StringFormatter fmt = element.getAnnotation(StringFormatter.class);
        String formatterName = "StringFormatter_" + element.getSimpleName();
        System.out.println(formatterName + ":" + fmt.value());
        return Formatter.builder()
                        .pkg(MoreElements.getPackage(element))
                        .name(formatterName)
                        .formatter(fmt.value())
                        .build();
    }

}
