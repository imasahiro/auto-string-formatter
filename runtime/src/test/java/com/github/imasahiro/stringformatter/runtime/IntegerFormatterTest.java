package com.github.imasahiro.stringformatter.runtime;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class IntegerFormatterTest {
    @Test
    public void testLog2() {
        assertEquals(1, IntegerFormatter.log2(1));
        assertEquals(1, IntegerFormatter.log2(9));

        assertEquals(2, IntegerFormatter.log2(10));
        assertEquals(2, IntegerFormatter.log2(11));
        assertEquals(2, IntegerFormatter.log2(99));

        assertEquals(3, IntegerFormatter.log2(100));
        assertEquals(3, IntegerFormatter.log2(500));
        assertEquals(3, IntegerFormatter.log2(999));

        assertEquals(4, IntegerFormatter.log2(1000));
        assertEquals(4, IntegerFormatter.log2(5000));
        assertEquals(4, IntegerFormatter.log2(9999));

        assertEquals(5, IntegerFormatter.log2(10000));
        assertEquals(5, IntegerFormatter.log2(50000));
        assertEquals(5, IntegerFormatter.log2(99999));

        assertEquals(19, IntegerFormatter.log2(1000000000000000000L));
        assertEquals(19, IntegerFormatter.log2(5555555555555555555L));
        assertEquals(19, IntegerFormatter.log2(Long.MAX_VALUE));

    }
}
