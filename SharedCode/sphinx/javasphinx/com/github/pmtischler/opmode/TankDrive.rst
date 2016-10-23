.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode TeleOp

TankDrive
=========

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @TeleOp @Disabled public class TankDrive extends RobotHardware

   Tank Drive controls for Robot.

Methods
-------
loop
^^^^

.. java:method:: public void loop()
   :outertype: TankDrive

   Tank drive control program.

setDrive
^^^^^^^^

.. java:method:: public void setDrive(double left, double right)
   :outertype: TankDrive

   Sets the drive chain power.

   :param left: The power for the left two motors.
   :param right: The power for the right two motors.

