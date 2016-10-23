package com.github.pmtischler.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Tank Drive controls for Robot.
 */
@TeleOp(name="pmtischler.TankDrive", group="pmtischler")
@Disabled
public class TankDrive extends RobotHardware {
    /**
     * Sets the drive chain power.
     * @param left The power for the left two motors.
     * @param right The power for the right two motors.
     */
    public void setDrive(double left, double right) {
        setPower(MotorName.DRIVE_FRONT_LEFT, left);
        setPower(MotorName.DRIVE_BACK_LEFT, left);
        setPower(MotorName.DRIVE_FRONT_RIGHT, right);
        setPower(MotorName.DRIVE_BACK_RIGHT, right);
    }

    /**
     * Tank drive control program.
     */
    public void loop() {
        setDrive(gamepad1.left_stick_y, gamepad1.right_stick_y);
    }
}
