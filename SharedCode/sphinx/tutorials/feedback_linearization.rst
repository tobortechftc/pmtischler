Feedback Linearization
======================

This tutorial adds feedback linearization so that you can control the robot
with a single joystick that specifies the direction you want to move, rather
than directly controlling motor values. This can provide a more intuitive
control and free up a joystick. More importantly, higher-level algorithms for
autonomous robots (e.g. path planning) typically output a velocity vector
without accounting for the non-holonomic dynamics of the robot (e.g. tank
drive). Feedback linearization can convert from a path panning velocity vector
to motor speeds.

Feedback linearized control may not be more intuitive for you- you may prefer
to use :doc:`tank_drive`. You will need to practice driving with both modes to
determine which is easier. Either way, it is important to test Feedback
linearization using manual control now so that it can later be used by the
autonomous code.

:doc:`../javasphinx/com/github/pmtischler/control/FeedbackLinearizer`.  One of
the custom additions in `pmtischler/ftc_app
<https://github.com/pmtischler/ftc_app>`__ is a class to perform feedback
linearization. The main function of interest is
``getWheelVelocitiesForRobotVelocity``, which takes a velocity vector relative
to the robot's view of the world and returns wheel velocities that will yield
that overall velocity.

**Add Import & Variable**. The first step is to import the
``FeedbackLinearizer`` class and add a variable to hold it. Later we will
initialize it and then use it to get wheel velocities from a given gamepad. Add
the following additions to the ``TankDrive``:

.. code-block:: java

    // ... package and other imports
    import com.github.pmtischler.base.Vector2d;
    import com.github.pmtischler.control.FeedbackLinearizer;

    @TeleOp(name="TankDrive", group="TankDrive")
    public class TankDrive extends OpMode {
        // ... other variables
        private FeedbackLinearizer linearizer = null;

        // ... other code.
    }

**Add Robot Constants**. The feedback linearizer is designed to produce a
correct wheel velocity for a given overall velocity. In order to know how fast
to spin the motor, it needs to know the wheel size ``wheelRadius`` (the
distance traveled per rotation is the circumference of the wheel). In order to
know how fast to spin the motor in a turn, it needs to know how the distance
between the left and right wheels ``wheelBaseline``. Finally, in order to
linearize the function it needs to know the tradeoff between smooth moving and
movement accuracy ``feedbackEpsilon``. Add the following variables to the
class. Measure specific values for ``wheelRadius`` and ``wheelBaseline`` for
your robot. The code does not assume any specific units, but they must be
consistent everwhere, so let's use inches. Leave a value of ``1.0`` for
``feedbackEpsilon`` for now.

.. code-block:: java

    private final double wheelRadius = 1.0;      // 1 inch wheel radius.
    private final double wheelBaseline = 18.0;   // 18 inches between wheels.
    private final double feedbackEpsilon = 1.0;  // Tuning smoothness.

**Add Joystick to Speed Constant**. The linearizer takes as input the desired
robot velocity (speed and direction), and outputs wheel velocities (wheel
rotations per second). The desired robot velocity has units (inches per
second), whereas the joystick value is unitless (a percentage of range of
motion). You need a mapping between joystick value and max speed. Using the
``TankDrive`` program from before, measure the max forward speed of the robot
in inches per second, in it's lightest configuration, and then add the
following variable.

.. code-block:: java

    private final double maxRobotSpeed = 12;  // 12 inches per second.

**Add Wheel Speed to Motor Constant**. The linearizer produces wheel speeds,
but the robot can only control motor power. What motor power should you set to
achieve a specific wheel speed? That is a complicated question as it depends on
the motor, the wheel, the weight of the robot, the wind drag, the wheel slip,
etc. In the :doc:`pid_control` tutorial we will discuss a way to dynamically
deal with this problem. For now, we will make a simplifying assumption that
wheel speed is linear with motor power, and that at max power you achieve max
speed. Measure the max rotation speed of the wheel when the robot is lifted up
so the wheels aren't feeling resistance. Add the following variable to add this
constant as radians per second.

.. code-block:: java

    private final double maxWheelSpeed = 6.28;  // 6.28 radians per second.

**Add Clamping of Power**. The linearizer code produces a wheel velocity, and
we use the wheel speed to motor power constant to convert it. Let's say after
this conversion we have an output power of 10% for the left motor, and 200% for
the right motor, which would result in an arc left turn if the motor could go
to 200%. However, it only can produce 100% of power (intuitively), so as a
result in actually produces 10% for the left motor and 100% for the right,
yielding a larger arc. What we'd prefer is that the right motor use 100% power
and the left motor use 5% power so that the arc shape is maintained but we move
slower, and if we wanted to move faster in a different arc we'd change the
joystick input. Add the following function which will clamp the motor speeds to
100% while preserving the ratio between powers (thus preserving arc).

.. code-block:: java

    public void clampPowers(Vector2d motorPowers) {
        double maxPower = Math.max(Math.abs(motorPowers.getX()),
                                   Math.abs(motorPowers.getY()));
        if (maxPower > 1.0) {
            motorPowers.div(maxPower);
        }
    }

**Add Mode Select**. We want the code to be able to use tank drive or feedback
linearization. Add the following member variable which will select between the
two. This will allow you to test feedback linearization and tank drive
simultaneously to see which you prefer, and to have the ability to test
feedback linearization when needed as preparation for autonomy.

.. code-block:: java

    private boolean shouldFeedbackLinearize = true;

**Add FeedbackLinearizer**. Now that we have the code imported and the
constants defined, we can now add the code to use the feedback linearizer. Add
the following code to ``init`` and ``loop``.

.. code-block:: java

    public void init() {
        // ... other code.
        linearizer = new FeedbackLinearizer(wheelRadius, wheelBaseline, feedbackEpsilon);
    }

    public void loop() {
        double left = 0;
        double right = 0;

        if (shouldFeedbackLinearize) {
            // Convert joystics to robot velocity.
            Vector2d robotVelocity = new Vector2d(gamepad1.left_stick_y, gamepad1.left_stick_x);
            robotVelocity.mul(maxRobotSpeed);
            // Linearize to wheel velocities.
            Vector2d wheelVelocities = linearizer.getWheelVelocitiesForRobotVelocity(
                    robotvelocity);
            // Convert wheel velocities to motor power (later PID).
            Vector2d motorPowers = new Vector2d(wheelVelocities);
            motorPowers.div(maxWheelSpeed);
            // Clamp motor powers.
            clampPowers(motorPower);
            left = motorPowers.getX();
            right = motorPowers.getY();
        } else {
            // Tank drive.
            left = gamepad1.left_stick_y;
            right = gamepad1.right_stick_y;
        }

        leftFrontMotor.setPower(left);
        leftBackMotor.setPower(left);
        rightFrontMotor.setPower(right);
        rightBackMotor.setPower(right);
    }

Congratulations, you now have the ability to drive with feedback linearization!
This was a necessary step to higher-level autonomy, and can be a useful manual
control. You can change ``shouldFeedbackLinearize`` to switch between
``TankDrive`` and the feedback linearize mode. You can play with different
values of ``feedbackEpsilon`` to trade between smoothness and accuracy of
motion.
