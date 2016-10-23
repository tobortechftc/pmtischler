.. java:import:: com.qualcomm.robotcore.eventloop.opmode OpMode

.. java:import:: com.qualcomm.robotcore.hardware DcMotor

.. java:import:: java.util ArrayList

RobotHardware
=============

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: public abstract class RobotHardware extends OpMode

   Hardware Abstraction Layer for Robot. Provides common variables and functions for the hardware.

Methods
-------
init
^^^^

.. java:method:: public void init()
   :outertype: RobotHardware

   Initialize the hardware handles.

setPower
^^^^^^^^

.. java:method:: public void setPower(MotorName motor, double power)
   :outertype: RobotHardware

   Sets the power of the motor.

   :param motor: The motor to modify.
   :param power: The power to set.

stop
^^^^

.. java:method:: public void stop()
   :outertype: RobotHardware

   End of match, stop all actuators.

