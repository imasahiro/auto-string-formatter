package com.github.imasahiro.stringformatter.processor;

import java.lang.reflect.Type;

import com.squareup.javapoet.CodeBlock.Builder;

class FixedString implements FormatString {
    private final String text;

    private FixedString(String text) {
        this.text = text;
    }

    static FixedString of(String s) {
        return new FixedString(s);
    }

    static FixedString of(String s, int begin, int end) {
        for (int i = begin; i < end; i++) {
            if (s.charAt(i) == '%') {
                String conversion = (i != end - 1) ? String.valueOf(s.charAt(i + 1)) : "";
                throw new IllegalArgumentException("Unrecognized conversion : " + conversion);
            }
        }
        return new FixedString(s.substring(begin, end));
    }

    @Override
    public int getIndex() {
        return -1;
    }

    @Override
    public void emit(Builder codeBlockBuilder, Type argumentType) {
        codeBlockBuilder.add("sb.append(\"" + text + "\");\n");
    }

    @Override
    public String toString() {
        return "FixedString(text:" + text + ")";
    }
}
