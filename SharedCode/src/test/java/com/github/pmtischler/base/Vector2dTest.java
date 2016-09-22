package com.github.pmtischler.base;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests correctness of Vector2d.
 */
public class Vector2dTest {
    // The comparison threshold.
    private static final double diffThresh = 0.00001;

    @Test
    // Test constructor and accessors.
    public void testConstructor() {
        Vector2d v = new Vector2d(1, 2);
        assertEquals(1, v.getX(), diffThresh);
        assertEquals(2, v.getY(), diffThresh);
    }

    @Test
    // Test add().
    public void testAdd() {
        Vector2d v = new Vector2d(1, 2);
        Vector2d o = new Vector2d(3, 4);
        v.add(o);
        assertEquals(4, v.getX(), diffThresh);
        assertEquals(6, v.getY(), diffThresh);
    }

    @Test
    // Test sub().
    public void testSub() {
        Vector2d v = new Vector2d(1, 2);
        Vector2d o = new Vector2d(3, 4);
        v.sub(o);
        assertEquals(-2, v.getX(), diffThresh);
        assertEquals(-2, v.getY(), diffThresh);
    }
}
