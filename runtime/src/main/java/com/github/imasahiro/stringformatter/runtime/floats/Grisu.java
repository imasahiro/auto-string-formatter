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

import com.github.imasahiro.stringformatter.runtime.integers.IntegerFormatter;

public final class Grisu {
    private static final int q = 64;
    private static final int alpha = -59;
    private static final int gamma = -56;

    private static final int TEN2 = 100;

    private Grisu() {}

    /**
     * Appends string formatted floating point number to {@link StringBuffer}.
     */
    public static StringBuilder formatDouble(StringBuilder sb, double value) {
        GrisuResult r = grisu2(value, sb);
        sb.append('e');
        return fillExponent(sb, r.k);
    }

    static class GrisuResult {
        int length;
        int k;

        GrisuResult(int length, int k) {
            this.length = length;
            this.k = k;
        }
    }

    private static GrisuResult grisu2(double v, StringBuilder buffer) {
        GrisuResult result = new GrisuResult(0, 0);
        Pair<DiyFp, DiyFp> normalized = DiyFp.normalizedBoundaries(v);
        final DiyFp w_m = normalized.first();
        final DiyFp w_p = normalized.second();

        int mk = KComp.k_comp(w_p.getE() + q, alpha, gamma);
        DiyFp cMk = CachedPowerOfTens.get(mk);
        DiyFp wp = w_p.multiply(cMk);
        DiyFp wm = w_m.multiply(cMk);
        wm = DiyFp.of(wm.getF() + 1, wm.getE());
        wp = DiyFp.of(wp.getF() - 1, wp.getE());
        DiyFp delta = wp.minus(wm);
        result.k = -mk;
        digit_gen(wp, delta, buffer, result);
        return result;
    }

    private static void digit_gen(DiyFp mp, DiyFp delta, StringBuilder buffer, GrisuResult r) {
        DiyFp one = DiyFp.of(1L << -mp.getE(), mp.getE());
        int p1 = (int) (mp.getF() >>> -one.getE()); /// Mp_cut
        long p2 = mp.getF() & (one.getF() - 1);
        r.length = 0;
        long kappa = 3;
        int div = TEN2;
        while (kappa > 0) { /// Mp_inv1
            long d = p1 / div;
            if (d > 0 || r.length > 0) {
                buffer.append(d);
                r.length++;
            }
            p1 %= div;
            kappa--;
            div /= 10;
            if ((p1 << -one.getE()) + p2 <= delta.getF()) { /// Mp_delta
                r.k += kappa;
                return;
            }
        }
        do {
            p2 *= 10;
            long d = p2 >>> -one.getE();
            if (d > 0 || r.length > 0) {
                buffer.append(d); /// Mp_inv2
                r.length++;
            }
            p2 &= one.getF() - 1;
            kappa--;
            delta = DiyFp.of(delta.getF() * 10, delta.getE());
        } while (p2 > delta.getF());
        r.k += kappa;
    }

    private static StringBuilder fillExponent(StringBuilder sb, int k) {
        return IntegerFormatter.formatTo(sb, k, 0, 0);
    }
}
