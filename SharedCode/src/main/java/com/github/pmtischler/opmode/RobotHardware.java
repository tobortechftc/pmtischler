package com.github.pmtischler.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.ArrayList;

/**
 * Hardware Abstraction Layer for Robot.
 * Provides common variables and functions for the hardware.
 */
public abstract class RobotHardware extends OpMode {
    // The motors on the robot.
    // The robot configuration in the app should match these names.
    public enum MotorName {
        DRIVE_FRONT_LEFT,
        DRIVE_FRONT_RIGHT,
        DRIVE_BACK_LEFT,
        DRIVE_BACK_RIGHT,
    }

    /**
     * Sets the power of the motor.
     * @param motor The motor to modify.
     * @param power The power to set.
     */
    public void setPower(MotorName motor, double power) {
        allMotors.get(motor.ordinal()).setPower(power);
    }

    /**
     * Initialize the hardware handles.
     */
    public void init() {
        allMotors = new ArrayList<DcMotor>();
        for (MotorName m : MotorName.values()) {
            DcMotor motor = hardwareMap.dcMotor.get(m.name());
            motor.setPower(0);
            allMotors.add(motor);
        }
    }

    /**
     * End of match, stop all actuators.
     */
    public void stop() {
        super.stop();

        for (DcMotor motor : allMotors) {
            motor.setPower(0);
        }
    }

    // All motors on the robot, in order of MotorName.
    private ArrayList<DcMotor> allMotors;
}
