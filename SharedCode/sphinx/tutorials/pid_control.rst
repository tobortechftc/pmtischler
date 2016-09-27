PID Control
===========

In this tutorial you will use a PID Controller to dynamically pick a motor
power to achieve a specific motor speed. This can be used to remove the
linearity assumption in the :doc:`feedback_linearization` tutorial, and to
automatically adapt to changes in robot weight, air drag, etc.

`PID Control <https://en.wikipedia.org/wiki/PID_controller>`__. PID is a
control feedback mechanism which standards for proportional integral
derivative. It can help optimize situations where you have a variable of
interest which you can measure, say speed of the motor, and a separate variable
which you can control to achieve a speed, say motor power. The PID control
takes as input the error signal :math:`e(t)`, say difference between actual and
desired motor speed, and a series of tuning constants. PID control then outputs
the manipulated variable :math:`O(t)`. The standard form of the PID equation:

.. math::

    O(t) = K_p \left ( e(t) + \frac{1}{T_i} \int_{0}^{t} e(t) dt + T_d \frac{d}{dt} e(t) \right )

============ ==================================================
Variable     Description
============ ==================================================
:math:`t`    Time
:math:`O(t)` Output over time (e.g. motor power)
:math:`e(t)` Error of desired variable (e.g. motor speed)
:math:`T_i`  Eliminate sum of errors within this time.
:math:`T_d`  Account for predicted error this time into future.
:math:`K_p`  Gain to control rise-time vs. overshoot.
============ ==================================================

With the standard form, you set values for :math:`T_i` and :math:`T_p` based on
desired behavior, and then you tune :math:`K_p` to an optimal value for your
problem.

There is one common edge-case you need to think about. Let's say we were using
Pid to control motor power to achieve a drive speed. If we held the robot in
place to prevent the robot from achieving a desired speed, the integral term of
Pid would grow continuously. When you release the robot, the integral term will
dominate the others and prevent it from stopping. This phenomena is called
Integral Windup. You can prevent this from happening by limiting the integral
term to specific bounds. In practice you pick values which the integral term
shouldn't need to go beyond, while simultaneously reacting fast enough once the
robot is unblocked.

**Motor Speed via Encoders**.  To use PID to control a motor's speed, you need
to measure the actual motors speed. You can measure motor speed using an
encoder which measures the rotations per second, which can be obtained via
``DcMotor.getCurrentPosition()``. Add the following member variables and code
to compute motor speed. ``ticksPerRevolution`` is a constant you can obtain
from your the motor's encoder datasheet, and it specifies the number of encoder
positions in a single shaft revolution.

.. code-block:: java

    private final double ticksPerRevolution = 1000;  // Get for your motor and gearing.
    private double prevTime;  // The last time loop() was called.
    private int prevLeftEncoderPosition;   // Encoder tick at last call to loop().
    private int prevRightEncoderPosition;  // Encoder tick at last call to loop().

    public void init() {
        // ... other code
        prevTime = 0;
        prevLeftEncoderPosition = leftFrontMotor.getCurrentPosition();
        prevRightEncoderPosition = RightFrontMotor.getCurrentPosition();
    }

    public void loop() {
        // ... other code
        if (shouldFeedbackLinearize) {
            // ... other code
            // Compute speed of left,right motors.
            double deltaTime = time - prevTime;
            double leftSpeed = (leftFrontMotor.getCurrentPosition() - prevLeftEncoderPosition) /
                               deltaTime;
            double rightSpeed = (rightFrontMotor.getCurrentPosition() - prevRightEncoderPosition) /
                                deltaTime;
            // Track last loop() values.
            prevTime = time;
            prevLeftEncoderPosition = leftFrontMotor.getCurrentPosition();
            prevRightEncoderPosition = rightFrontMotor.getCurrentPosition();
        } else {
            // ... other code
        }
    }

**PID Constants**. Per the equation, there are three constants to control
operation of a PID controller. You will need to tune values for your specific
use case. Add the following member variables to the code.

.. code-block:: java

    private final double drivePidKp = 1;    // Tuning variable for PID.
    private final double drivePidTi = 1.0;  // Eliminate integral error in 1 sec.
    private final double drivePidTd = 0.1;  // Account for error in 0.1 sec.
    // Protect against integral windup by limiting integral term.
    private final double drivePidIntMax = maxWheelSpeed;  // Limit to max speed.

**PID Controller**. The custom code provides a
:doc:`../javasphinx/com/github/pmtischler/control/Pid` class. This will be used
to achieve a specific motor speed while controlling motor power. Add the
following code to use the Pid control.

.. code-block:: java

    // ... other code
    import com.github.pmtischler.control.Pid;

    @TeleOp(name="TankDrive", group="TankDrive")
    public class TankDrive extends OpMode {
        // ... other code
        private Pid leftDrive = null;
        private Pid rightDrive = null;

        public void init() {
            // ... other code
            leftDrive = new Pid(drivePidKp, drivePidTi, drivePidTd,
                                -drivePidIntMax, drivePidIntMax);
            rightDrive = new Pid(drivePidKp, drivePidTi, drivePidTd,
                                 -drivePidIntMax, drivePidIntMax);
        }

        public void loop() {
            // ... other code
            if (shouldFeedbackLinearize) {
                // ... other code
                // Use Pid to compute motor powers to achieve wheel velocity.
                left = leftDrive.update(wheelVelocities.getX(), leftSpeed,
                                        deltaTime);
                right = rightDrive.update(wheelVelocities.getY(), rightSpeed,
                                          deltaTime);
                // Clamp motor powers.
                Vector2d motorPower = new Vector2d(left, right);
                clampPowers(motorPower);
                left = motorPower.getX();
                right = motorPower.getY();
            } else {
                // ... other code
            }
        }
    }

Congratulations, you now have the ability to control a motor's speed using Pid!
You've removed the linearity assumption, making it more accurate. You've
removed hard-coded constants which wouldn't adapt to changes in robot weight or
air drag.

You may need to tune the values of :math:`K_p`, :math:`T_i`, and :math:`T_d`
for it to perform as desired. Tuning Pid controllers is a skill- one which will
take time and patience to learn.
