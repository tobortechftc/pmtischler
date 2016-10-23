.. java:import:: com.github.pmtischler.control Mecanum

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode TeleOp

MecanumDrive
============

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @TeleOp @Disabled public class MecanumDrive extends RobotHardware

   Mecanum Drive controls for Robot.

Methods
-------
loop
^^^^

.. java:method:: public void loop()
   :outertype: MecanumDrive

   Mecanum drive control program.

setDrive
^^^^^^^^

.. java:method:: public void setDrive(double vD, double thetaD, double vTheta)
   :outertype: MecanumDrive

   Sets the drive chain power.

   :param vD: The desired robot speed. [-1, 1]
   :param thetaD: The angle at which the robot should move. [0, 2PI]
   :param vTheta: The desired rotation velocity. [-1, 1]

