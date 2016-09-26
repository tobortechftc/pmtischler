Tank Drive
==========

This tutorial adds tank drive manual controls to a 4-wheel robot. That is, the
left joystick will control the left motors and the right joystick will control
the right motors. The robot should have 4 wheels: two on the left, and two on
the right. Two of those motors should be in the front, and two in the back. You
should name the robots the following:

- ``leftFront``
- ``leftBack``
- ``rightFront``
- ``rightBack``

**Skeleton**. The following code block contains the standard skeleton of code
that every robot program will be based on. Create a new class under
``TeamCode/java/org.firstinspires.ftc.teamcode`` and call it
``TankDrive.java``. Add the following code to the file:

.. code-block:: java

    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;

    @TeleOp(name="TankDrive", group="TankDrive")
    public class TankDrive extends OpMode {

        public void init() {
            // Run once when driver hits "INIT".
            // Robot setup code goes here.
        }

        public void start() {
            // Run once when driver hits "PLAY".
            // Robot start-of-match code goes here.
        }

        public void loop() {
            // Code executed continuously until robot end.
        }

        public void stop() {
            // Run once when driver hits "STOP" or time elapses.
            // Robot end-of-match code goes here.
        }
    }

**Motor Variables**. After creating the robot class with the skeleton, the next
step is to create variables for the 4 motors. These will later be initialized
to the named motors above, and then set to the joystick inputs. Add the
following as class member variables.

.. code-block:: java

    private DcMotor leftFrontMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightFrontMotor = null;
    private DcMotor rightBackMotor = null;

**Robot Initialization**. In the robot initialization step, you need to assign
the specific motor values to the variables we created in the last step. We will
also set the initial power to zero, so the robot doesn't start moving on
power-up before we hit the play button. Replace the ``init`` function with the
following:

.. code-block:: java

    public void init() {
        leftFrontMotor = hardwareMap.dcMotor.get("leftFront");
        leftBackMotor = hardwareMap.dcMotor.get("leftBack");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFront");
        rightBackMotor = hardwareMap.dcMotor.get("rightBack");

        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }

**Joystick Controls**. The joystick can be accessed through the ``gamepad``
variable. Tank drive can be implemented by setting motor power to joystick
y-axis values. When you push forward on the joystick, the motor power will be
set to 100% and the robot will move forward. When you push backward on the
youstick, the motor power will be set to -100% and the robot will move
backward. With two joystics, each controlling one side of the robot, pushing
forward on one joystick while pushing backward on the other joystick will cause
the robot to turn in place. Replace the ``loop`` function with the following:

.. code-block:: java

    public void loop() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;

        leftFrontMotor.setPower(left);
        leftBackMotor.setPower(left);
        rightFrontMotor.setPower(right);
        rightBackMotor.setPower(right);
    }

**End of Match Stop**. At the end of the match you want the robot to stop
moving. If the last call to ``loop`` at the end of the match had set a non-zero
motor power, then the robot will continue to move in that direction
indefinitely, without you being able to stop it. For this reason, we always
want to set the end-condition, for all motors to have zero power, in the
``stop`` function. Replace the ``stop`` function with the following:

.. code-block:: java

    public void stop() {
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }

**Motor Polarity**. If you were to test the program, you would be able to
control the motors with the gamepad, but the motors turn direction might be
reversed. You can handle this type of problem two ways. First, you could
reverse the wire connections (e.g. connect red header to black socket) from the
motor to the motor controller, which will reverse the input signal to the
motor. This is easier in terms of coding, but harder in terms of wiring.
Second, you could modify the code to invert the control signal, so a 100%
gamepad signal will set a -100% motor signal. The following shows an
example of such:

.. code-block:: java

    double left = gamepad1.left_stick_y;
    leftFrontMotor.setPower(-left);

Congratulations, you should now be able to drive a 4-wheel robot using
tank-drive style controls!
