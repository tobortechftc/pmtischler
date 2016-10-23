package com.github.pmtischler.control;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Mecanum wheel drive calculations.
 * Input controls:
 *   V_d = desired robot speed.
 *   theta_d = desired robot velocity angle.
 *   V_theta = desired robot rotational speed.
 * Characteristic equations:
 *   V_{front,left} = V_d sin(theta_d + pi/4) + V_theta
 *   V_{front,right} = V_d cos(theta_d + pi/4) - V_theta
 *   V_{back,left} = V_d cos(theta_d + pi/4) + V_theta
 *   V_{back,right} = V_d sin(theta_d + pi/4) - V_theta
 */
public class Mecanum {
    /**
     * Mecanum wheels, used to get individual motor powers.
     */
    public static class Wheels {
        // The mecanum wheels.
        public final double frontLeft;
        public final double frontRight;
        public final double backLeft;
        public final double backRight;

        /**
         * Sets the wheels to the given values.
         */
        public Wheels(double frontLeft, double frontRight,
                      double backLeft, double backRight) {
            this.frontLeft = frontLeft;
            this.frontRight = frontRight;
            this.backLeft = backLeft;
            this.backRight = backRight;
        }
    }

    /**
     * Gets the wheel powers corresponding to desired motion.
     * @param vD The desired robot speed. [-1, 1]
     * @param thetaD The angle at which the robot should move. [0, 2PI]
     * @param vTheta The desired rotation velocity. [-1, 1]
     */
    public static Wheels motionToWheels(double vD, double thetaD, double vTheta) {
        double frontLeft = vD * Math.sin(thetaD + Math.PI / 4) + vTheta;
        double frontRight  = vD * Math.cos(thetaD + Math.PI / 4) - vTheta;
        double backLeft = vD * Math.cos(thetaD + Math.PI / 4) + vTheta;
        double backRight = vD * Math.sin(thetaD + Math.PI / 4) - vTheta;
        List<Double> motors = Arrays.asList(frontLeft, frontRight, backLeft, backRight);
        clampPowers(motors);
        return new Wheels(motors.get(0), motors.get(1), motors.get(2), motors.get(3));
    }

    /**
     * Clamps the motor powers while maintaining power ratios.
     * @param powers The motor powers to clamp.
     */
    private static void clampPowers(List<Double> powers) {
      double minPower = Collections.min(powers);
      double maxPower = Collections.max(powers);
      double maxMag = Math.max(Math.abs(minPower), Math.abs(maxPower));

      if (maxMag > 1.0) {
        for (int i = 0; i < powers.size(); i++) {
          powers.set(i, powers.get(i) / maxMag);
        }
      }
    }
}
