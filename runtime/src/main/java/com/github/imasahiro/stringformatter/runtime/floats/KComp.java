/*
 * Copyright (C) 2018 Masahiro Ide
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

package com.github.imasahiro.stringformatter.runtime.floats;

final class KComp {
    private static final double D_1_LOG2_10 = 0.30102999566398114; //  1 / lg(10)

    private KComp() {}

    static int k_comp(int e, int alpha, int gamma) {
        return (int) Math.ceil((alpha - e + 63) * D_1_LOG2_10);
    }
}
