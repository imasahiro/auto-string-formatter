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
package com.github.imasahiro.stringformatter.processor.specifier;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.github.imasahiro.stringformatter.processor.FormatFlag;
import com.github.mustachejava.Mustache;

public abstract class FormatConversionType {
    static String getCode(Mustache template, Map<String, ?> scope) {
        StringWriter sw = new StringWriter();
        template.execute(sw, scope);
        return sw.toString();
    }

    public abstract Set<TypeMirror> getType(Types typeUtil, Elements elementUtil);

    public String emit(String arg, int width, int precision, Set<FormatFlag> flags, TypeMirror argumentType) {
        return "sb.append(" + arg + ");\n";
    }
}
