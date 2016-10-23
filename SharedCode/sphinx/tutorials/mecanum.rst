Mecanum Drive
=============

Mecanum drive allows the robot to move at any angle and rotate in place. This
will make it faster and more effective to maneuver the robot, like lining up to
press a button. Mecanum wheels have lower grip, causing it to be less effective
to go up/down ramps or climb over obstacles.

The roller component of the wheel should create a diagonal between the
front-left and bottom-right, and between the front-right and bottom-left. The
following equations can be used to control the mecanum wheels. See `Simplistic
Control of a Mecanum Drive
<http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf>`__ for
details.

.. math::

    V_{front,left} &= V_d sin \left ( \theta_d + \frac{\pi}{4} \right ) + V_\theta \\
    V_{front,right} &= V_d cos \left ( \theta_d + \frac{\pi}{4} \right ) - V_\theta \\
    V_{back,left} &= V_d cos \left ( \theta_d + \frac{\pi}{4} \right ) + V_\theta \\
    V_{back,right} &= V_d sin \left ( \theta_d + \frac{\pi}{4} \right ) - V_\theta

================ ===================================================
Variable         Description
================ ===================================================
:math:`V_x`      Motor power for wheel :math:`x` [-2, 2].
:math:`V_d`      The desired robot speed [-1, 1].
:math:`\theta_d` Desired robot angle while moving [0, :math:`2\pi`].
:math:`V_\theta` Desired speed for changing direction [-1, 1].
================ ===================================================

The robot must be controlled by a gamepad with joysticks, so from these
joysticks you need to determine :math:`V_d`, :math:`\theta_d`,
:math:`V_\theta`. The left joystick's direction will be used for
:math:`\theta_d`, and the joystick's magnitude for :math:`V_d`. The right
joystick's x-axis value will be used for :math:`V_\theta`. This will provide
first-person-shooter style control. The following equations will provide this
for joysticks :math:`J_x`.

.. math::

    V_d &= \sqrt{J_{left, x}^2 + J_{left, y}^2} \\
    \theta_d &= arctan(J_{left, y}, J_{left x}) \\
    V_\theta &= J_{right, x}

**Add N-Motor Clamping**. Previously we wrote ``clampPowers`` which took 2
motor powers, left and right, and clamped them to 100%. We have the same need
here- we need to clamp the motor power to 100% while maintaining the ratio of
motor powers to maintain angle and rotation speed. Add the following
``clampPowers`` function next to the previous version.

.. code-block:: java

  public void clampPowers(List<double> powers) {
    double minPower = Collections.min(powers);
    double maxPower = Collections.max(powers);
    double maxMag = Math.max(Math.abs(minPower), Math.abs(maxPower));

    if (maxMag > 1.0) {
      for (int i = 0; i < powers.size(); i++) {
        powers[i] /= maxMag;
      }
    }
  }

**Add Mode Select**. We want the ability to control the robot with mecanum
wheels or with regular wheels. Add the following member variable which will
select between the two. This will allow you to select the control style by
changing this variable, without having to change your code.

.. code-block:: java

  private final boolean shouldMecanumDrive = true;

**Add Mecanum Drive**. The mecanum drive is a straightforward implementation of
the formulas above. Update the ``loop`` function with the following code.

.. code-block:: java

  public void loop() {
    if (shouldMecanumDrive) {
        double vD = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) +
                              Math.pow(gamepad1.left_stick_y, 2));
        double thetaD = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
        double vTheta = gamepad1.right_stick_x;

        double leftFront = vD * Math.sin(thetaD + Math.PI / 4) + vTheta;
        double rightFront = vD * Math.cos(thetaD + Math.PI / 4) - vTheta;
        double leftBack = vD * Math.cos(thetaD + Math.PI / 4) + vTheta;
        double rightBack = vD * Math.sin(thetaD + Math.PI / 4) - vTheta;

        List<double> motors = Arrays.asList(leftFront, rightFront, leftBack, rightBack);
        clampPowers(motors);
        leftFrontMotor.setPower(motors[0]);
        rightFrontMotor.setPower(motors[1]);
        leftBackMotor.setPower(motors[2]);
        rightBackMotor.setPower(motors[3]);
    } else {
        // ... previous loop code.
    }
  }

Congratulations, you now have the ability to drive with Mecanum wheels!
