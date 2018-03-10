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

import com.google.common.base.Preconditions;

/**
 * DIY (Do It Yourself) implementation of floating point number.
 */
final class DiyFp {
    private static final long M32 = 0xFFFFFFFFL;

    private static final long DP_SIGNIFICAND_SIZE = 52;
    private static final long DP_EXPONENT_BIAS = 0x3FFL + DP_SIGNIFICAND_SIZE;
    private static final long DP_MIN_EXPONENT = -DP_EXPONENT_BIAS;
    private static final long DP_EXPONENT_MASK = 0x7FF0000000000000L;
    private static final long DP_SIGNIFICAND_MASK = 0x000FFFFFFFFFFFFFL;
    private static final long DP_HIDDEN_BIT = 0x0010000000000000L;

    private static final long DIY_SIGNIFICAND_SIZE = 64;

    private final long f;
    private final int e;

    private DiyFp(long f, int e) {
        this.f = f;
        this.e = e;
    }

    int getE() {
        return e;
    }

    long getF() {
        return f;
    }

    DiyFp minus(DiyFp that) {
        Preconditions.checkState(e == that.e && f >= that.f);
        return new DiyFp(f - that.f, e);
    }

    DiyFp multiply(DiyFp that) {
        long a = f >>> 32;
        long b = f & M32;
        long c = that.f >>> 32;
        long d = that.f & M32;
        long ac = a * c;
        long bc = b * c;
        long ad = a * d;
        long bd = b * d;
        long tmp = (bd >>> 32) + (ad & M32) + (bc & M32) + (1L << 31);
        return new DiyFp(ac + (ad >>> 32) + (bc >>> 32) + (tmp >>> 32), e + that.e + 64);
    }

    private static DiyFp normalizeBoundary(DiyFp in) {
        long f = in.getF();
        int e = in.getE();
        while ((f & DP_HIDDEN_BIT << 1) == 0) {
            f <<= 1;
            e--;
        }
        /* do the final shifts in one go. Don't forget the hidden bit (the '-1') */
        f <<= DIY_SIGNIFICAND_SIZE - DP_SIGNIFICAND_SIZE - 2;
        e -= DIY_SIGNIFICAND_SIZE - DP_SIGNIFICAND_SIZE - 2;
        return of(f, e);
    }

    static Pair<DiyFp, DiyFp> normalizedBoundaries(double d) {
        final DiyFp v = of(d);
        boolean significandIsZero = v.getF() == DP_HIDDEN_BIT;
        DiyFp pl = of((v.getF() << 1) + 1, v.getE() - 1);
        pl = normalizeBoundary(pl);
        DiyFp mi;
        if (significandIsZero) {
            mi = of((v.getF() << 2) - 1, v.getE() - 2);
        } else {
            mi = of((v.getF() << 1) - 1, v.getE() - 1);
        }
        mi = of(mi.getF() << mi.getE() - pl.getE(), pl.getE());
        return new Pair<>(mi, pl);
    }

    static DiyFp of(double d) {
        long d64 = Double.doubleToRawLongBits(d);
        long biasedE = (d64 & DP_EXPONENT_MASK) >>> DP_SIGNIFICAND_SIZE;
        long significand = d64 & DP_SIGNIFICAND_MASK;
        if (biasedE != 0) {
            return of(significand + DP_HIDDEN_BIT,
                      (int) (biasedE - DP_EXPONENT_BIAS));
        } else {
            return of(significand, (int) (DP_MIN_EXPONENT + 1));
        }
    }

    static DiyFp of(long bits, int e) {
        return new DiyFp(bits, e);
    }
}
