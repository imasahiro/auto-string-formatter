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

package com.github.imasahiro.stringformatter.processor.util;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * An class for logging/reporting compilation error/warning messages.
 */
public class ErrorReporter {
    private final Messager messager;

    public ErrorReporter(Messager messager) {
        this.messager = messager;
    }

    public void warn(String msg, Element e) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
    }

    public void error(String msg, Element e) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }

    /**
     * Reports error message and stop annotation processing.
     */
    public void fatal(String msg, Element e) {
        error(msg, e);
        throw new AbortProcessingException();
    }
}
