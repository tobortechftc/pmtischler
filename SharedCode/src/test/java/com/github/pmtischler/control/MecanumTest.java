package com.github.pmtischler.control;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Tests correctness of Mecanum calculations.
 */
public class MecanumTest {
    // The comparison threshold.
    private static final double diffThresh = 0.001;

    /**
     * Asserts that the input controls yields the expected output controls.
     */
    private void assertMecanum(double vD, double thetaD, double vTheta,
                               double frontLeft, double frontRight,
                               double backLeft, double backRight) {
        Mecanum.Wheels wheels = Mecanum.motionToWheels(vD, thetaD, vTheta);
        assertEquals(frontLeft, wheels.frontLeft, diffThresh);
        assertEquals(frontRight, wheels.frontRight, diffThresh);
        assertEquals(backLeft, wheels.backLeft, diffThresh);
        assertEquals(backRight, wheels.backRight, diffThresh);
    }

    @Test
    // Test Mecanum for direct strafing.
    public void testMecanumStrafing() throws Exception {
        // Forward.
        assertMecanum(1, 0, 0,
                      0.7071, 0.7071,
                      0.7071, 0.7071);
        // Right.
        assertMecanum(1, Math.PI / 2, 0,
                      0.7071, -0.7071,
                      -0.7071, 0.7071);
        // Back.
        assertMecanum(1, Math.PI, 0,
                      -0.7071, -0.7071,
                      -0.7071, -0.7071);
        // Left.
        assertMecanum(1, 3 * Math.PI / 2, 0,
                      -0.7071, 0.7071,
                      0.7071, -0.7071);

        // Front right.
        assertMecanum(1, Math.PI / 4, 0,
                      1, 0,
                      0, 1);
    }

    @Test
    // Test Mecanum for turning.
    public void testMecanumTurning() throws Exception {
        // Right.
        assertMecanum(0, 0, 1,
                      1, -1,
                      1, -1);
        // Left.
        assertMecanum(0, 0, -1,
                      -1, 1,
                      -1, 1);
    }

    @Test
    // Test Mecanum for moving and turning to clamp motors.
    public void testMecanumClamping() throws Exception {
        // Forward and full right.
        assertMecanum(1, 0, 1,
                      1, -0.1716,
                      1, -0.1716);
    }
}
