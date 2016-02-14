package com.github.imasahiro.stringformatter.runtime;

import com.google.common.annotations.VisibleForTesting;

public class IntegerFormatter {
    void format(StringBuilder sb, short v) {
        long unsigned = Math.abs(v);
        format0(sb, unsigned, v < 0);
    }

    void format(StringBuilder sb, int v) {
        long unsigned = Math.abs(v);
        format0(sb, unsigned, v < 0);
    }

    void format(StringBuilder sb, long v) {
        long unsigned = Math.abs(v);
        format0(sb, unsigned, v < 0);
    }

    static private final long ILOG10_TABLE[] = {
            1L,
            10L,
            100L,
            1000L,
            10000L,
            100000L,
            1000000L,
            10000000L,
            100000000L,
            1000000000L,
            10000000000L,
            100000000000L,
            1000000000000L,
            10000000000000L,
            100000000000000L,
            1000000000000000L,
            10000000000000000L,
            100000000000000000L,
            1000000000000000000L,
            Long.MAX_VALUE
    };

    @VisibleForTesting
    static int log2(long unsigned) {
        return Long.SIZE - Long.numberOfLeadingZeros(unsigned - 1);
    }

    static private int ilog10ul(long unsigned) {
        //#define LOG2(N) ((unsigned)((sizeof(long long) * 8) - __builtin_clzll((N)-1)))
        if (unsigned == 0) {
            return 1;
        } else {
            int t = ((log2(unsigned) + 1) * 1233) / 4096;
            return t + (unsigned >= ILOG10_TABLE[t] ? 1 : 0);
        }
    }

    private void format0(StringBuilder sb, long unsigned, boolean negative) {
    }
}
