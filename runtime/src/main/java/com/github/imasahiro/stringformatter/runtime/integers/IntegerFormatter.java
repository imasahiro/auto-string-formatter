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
package com.github.imasahiro.stringformatter.runtime.integers;

public class IntegerFormatter {
    public static final int PADDED_WITH_ZEROS = 1;
    private static final boolean ENSURE_CAPACITY = true;
    private static final boolean DISABLE_INT_TO_ASCII_UNROLLING = true;

    private static final char[] digits99 = {
            '0', '0', '0', '1', '0', '2', '0', '3', '0', '4', '0', '5', '0', '6', '0', '7', '0', '8', '0', '9',
            '1', '0', '1', '1', '1', '2', '1', '3', '1', '4', '1', '5', '1', '6', '1', '7', '1', '8', '1', '9',
            '2', '0', '2', '1', '2', '2', '2', '3', '2', '4', '2', '5', '2', '6', '2', '7', '2', '8', '2', '9',
            '3', '0', '3', '1', '3', '2', '3', '3', '3', '4', '3', '5', '3', '6', '3', '7', '3', '8', '3', '9',
            '4', '0', '4', '1', '4', '2', '4', '3', '4', '4', '4', '5', '4', '6', '4', '7', '4', '8', '4', '9',
            '5', '0', '5', '1', '5', '2', '5', '3', '5', '4', '5', '5', '5', '6', '5', '7', '5', '8', '5', '9',
            '6', '0', '6', '1', '6', '2', '6', '3', '6', '4', '6', '5', '6', '6', '6', '7', '6', '8', '6', '9',
            '7', '0', '7', '1', '7', '2', '7', '3', '7', '4', '7', '5', '7', '6', '7', '7', '7', '8', '7', '9',
            '8', '0', '8', '1', '8', '2', '8', '3', '8', '4', '8', '5', '8', '6', '8', '7', '8', '8', '8', '9',
            '9', '0', '9', '1', '9', '2', '9', '3', '9', '4', '9', '5', '9', '6', '9', '7', '9', '8', '9', '9'
    };

    public static StringBuilder formatTo(StringBuilder sb, short v, int flags, int width) {
        long unsigned = writeLeftPadding(sb, v, flags, width);
        return formatTo0(sb, unsigned);
    }

    public static StringBuilder formatTo(StringBuilder sb, int v, int flags, int width) {
        long unsigned = writeLeftPadding(sb, v, flags, width);
        return formatTo0(sb, unsigned);
    }

    public static StringBuilder formatTo(StringBuilder sb, long v, int flags, int width) {
        long unsigned = writeLeftPadding(sb, v, flags, width);
        return formatTo0(sb, unsigned);
    }

    private static StringBuilder formatTo0(StringBuilder sb, long unsigned) {
        if (DISABLE_INT_TO_ASCII_UNROLLING) {
            return formatWithByteArray(sb, unsigned);
        } else {
            return formatToStringUnrolled(sb, unsigned);
        }
    }

    private static long writeLeftPadding(StringBuilder sb, long val, int flags, int width) {
        long unsigned = Math.abs(val);
        boolean negative = val < 0;
        int len = IntegerUtils.log10(unsigned) + (negative ? 1 : 0);
        if (ENSURE_CAPACITY) {
            sb.ensureCapacity(sb.length() + len + width);
        }
        if ((flags & PADDED_WITH_ZEROS) != PADDED_WITH_ZEROS) {
            for (int i = len; i < width; i++) {
                sb.append(' ');
            }
        }
        if (negative) {
            sb.append('-');
        }
        if ((flags & PADDED_WITH_ZEROS) == PADDED_WITH_ZEROS) {
            for (int i = len; i < width; i++) {
                sb.append('0');
            }
        }
        return unsigned;
    }

    private static StringBuilder formatToStringUnrolled(StringBuilder sb, long val) {
        // val = aaaabbbbccccddddeeee
        if (val <= 99999999) {
            formatLessThan100Million(sb, val);
        } else if (val <= 9999999999999999L) {
            formatLessThan10Quadrillion(sb, val);
        } else {
            formatMoreThan10Quadrillion(sb, val);
        }
        return sb;
    }

    private static void formatMoreThan10Quadrillion(StringBuilder sb, long val) {
        // val = aaaabbbbccccddddeeee
        int a = (int) (val / 10000000000000000L);
        long bcde = val % 10000000000000000L;
        int a1 = a / 100 * 2;
        int a2 = a % 100 * 2;
        int bc = (int) (bcde / 100000000);
        int de = (int) (bcde % 100000000);
        int b = bc / 10000;
        int c = bc % 10000;
        int d = de / 10000;
        int e = de % 10000;

        int b1 = b / 100 * 2;
        int b2 = b % 100 * 2;
        int c1 = c / 100 * 2;
        int c2 = c % 100 * 2;
        int d1 = d / 100 * 2;
        int d2 = d % 100 * 2;
        int e1 = e / 100 * 2;
        int e2 = e % 100 * 2;

        if (a >= 1000) {
            sb.append(digits99[a1]);
        }
        if (a >= 100) {
            sb.append(digits99[a1 + 1]);
        }
        if (a >= 10) {
            sb.append(digits99[a2]);
        }
        sb.append(digits99[a2 + 1]);
        sb.append(digits99[b1]);
        sb.append(digits99[b1 + 1]);
        sb.append(digits99[b2]);
        sb.append(digits99[b2 + 1]);
        sb.append(digits99[c1]);
        sb.append(digits99[c1 + 1]);
        sb.append(digits99[c2]);
        sb.append(digits99[c2 + 1]);
        sb.append(digits99[d1]);
        sb.append(digits99[d1 + 1]);
        sb.append(digits99[d2]);
        sb.append(digits99[d2 + 1]);
        sb.append(digits99[e1]);
        sb.append(digits99[e1 + 1]);
        sb.append(digits99[e2]);
        sb.append(digits99[e2 + 1]);
    }

    private static void formatLessThan10Quadrillion(StringBuilder sb, long val) {
        // val = bbbbccccddddeeee
        long bc = val / 100000000;
        long de = val % 100000000;
        int b = (int) (bc / 10000);
        int c = (int) (bc % 10000);
        int d = (int) (de / 10000);
        int e = (int) (de % 10000);

        int b1 = b / 100 * 2;
        int b2 = b % 100 * 2;
        int c1 = c / 100 * 2;
        int c2 = c % 100 * 2;
        int d1 = d / 100 * 2;
        int d2 = d % 100 * 2;
        int e1 = e / 100 * 2;
        int e2 = e % 100 * 2;
        if (bc >= 10000000) {
            sb.append(digits99[b1]);
        }
        if (bc >= 1000000) {
            sb.append(digits99[b1 + 1]);
        }
        if (bc >= 100000) {
            sb.append(digits99[b2]);
        }
        if (bc >= 10000) {
            sb.append(digits99[b2 + 1]);
        }
        if (bc >= 1000) {
            sb.append(digits99[c1]);
        }
        if (bc >= 100) {
            sb.append(digits99[c1 + 1]);
        }
        if (bc >= 10) {
            sb.append(digits99[c2]);
        }
        if (bc >= 1) {
            sb.append(digits99[c2 + 1]);
        }
        sb.append(digits99[d1]);
        sb.append(digits99[d1 + 1]);
        sb.append(digits99[d2]);
        sb.append(digits99[d2 + 1]);
        sb.append(digits99[e1]);
        sb.append(digits99[e1 + 1]);
        sb.append(digits99[e2]);
        sb.append(digits99[e2 + 1]);
    }

    private static void formatLessThan100Million(StringBuilder sb, long val) {
        // val = ddddeeee
        if (val <= 9999) {
            // val = eeee
            int e1 = (int) (val / 100 * 2);
            int e2 = (int) (val % 100 * 2);
            if (val >= 1000) {
                sb.append(digits99[e1]);
            }
            if (val >= 100) {
                sb.append(digits99[e1 + 1]);
            }
            if (val >= 10) {
                sb.append(digits99[e2]);
            }
            sb.append(digits99[e2 + 1]);
        } else {
            // val = ddddeeee
            int d = (int) (val / 10000);
            int e = (int) (val % 10000);
            int d1 = d / 100 * 2;
            int d2 = d % 100 * 2;
            int e1 = e / 100 * 2;
            int e2 = e % 100 * 2;
            if (d >= 1000) {
                sb.append(digits99[d1]);
            }
            if (d >= 100) {
                sb.append(digits99[d1 + 1]);
            }
            if (d >= 10) {
                sb.append(digits99[d2]);
            }
            sb.append(digits99[d2 + 1]);
            sb.append(digits99[e1]);
            sb.append(digits99[e1 + 1]);
            sb.append(digits99[e2]);
            sb.append(digits99[e2 + 1]);
        }
    }

    private static StringBuilder formatWithByteArray(StringBuilder sb, long val) {
        char[] buf = new char[21];
        int len = IntegerUtils.log10(val);

        int index = len;
        while (val >= 100) {
            int idx = (int) (val % 100 * 2);
            buf[--index] = digits99[idx + 1];
            buf[--index] = digits99[idx];
            val /= 100;
        }
        if (val < 10) {
            buf[--index] = (char) ('0' + val);
        } else {
            buf[--index] = digits99[(int) (val * 2 + 1)];
            buf[--index] = digits99[(int) (val * 2)];
        }
        return sb.append(buf);
    }
}
