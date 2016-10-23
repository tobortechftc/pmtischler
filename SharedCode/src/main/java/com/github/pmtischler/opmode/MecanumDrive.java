package com.github.pmtischler.opmode;

import com.github.pmtischler.control.Mecanum;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Mecanum Drive controls for Robot.
 */
@TeleOp(name="pmtischler.MecanumDrive", group="pmtischler")
@Disabled
public class MecanumDrive extends RobotHardware {
    /**
     * Sets the drive chain power.
     * @param vD The desired robot speed. [-1, 1]
     * @param thetaD The angle at which the robot should move. [0, 2PI]
     * @param vTheta The desired rotation velocity. [-1, 1]
     */
    public void setDrive(double vD, double thetaD, double vTheta) {
        Mecanum.Wheels wheels = Mecanum.motionToWheels(vD, thetaD, vTheta);
        setPower(MotorName.DRIVE_FRONT_LEFT, wheels.frontLeft);
        setPower(MotorName.DRIVE_BACK_LEFT, wheels.frontRight);
        setPower(MotorName.DRIVE_FRONT_RIGHT, wheels.backLeft);
        setPower(MotorName.DRIVE_BACK_RIGHT, wheels.backRight);
    }

    /**
     * Mecanum drive control program.
     */
    public void loop() {
        double vD = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) +
                              Math.pow(gamepad1.left_stick_y, 2));
        double thetaD = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
        double vTheta = gamepad1.right_stick_x;
        setDrive(vD, thetaD, vTheta);
    }
}
