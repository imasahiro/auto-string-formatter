package com.github.imasahiro.stringformatter.processor;

import java.lang.reflect.Type;

import com.squareup.javapoet.CodeBlock.Builder;

interface FormatString {
    int getIndex();

    void emit(Builder codeBlockBuilder, Type argumentType);
}
