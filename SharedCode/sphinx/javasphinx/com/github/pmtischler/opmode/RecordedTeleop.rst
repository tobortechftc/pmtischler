.. java:import:: android.content Context

.. java:import:: com.github.pmtischler.base BlackBox

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode TeleOp

.. java:import:: java.io FileOutputStream

RecordedTeleop
==============

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @TeleOp @Disabled public class RecordedTeleop extends MecanumDrive

   Recorded teleop mode. This mode records the hardware which can later be played back in autonomous. Select the manual control mode by changing the parent class.

Methods
-------
init
^^^^

.. java:method:: public void init()
   :outertype: RecordedTeleop

   Extends teleop initialization to start a recorder.

loop
^^^^

.. java:method:: public void loop()
   :outertype: RecordedTeleop

   Extends teleop control to record hardware after loop.

stop
^^^^

.. java:method:: public void stop()
   :outertype: RecordedTeleop

   Closes the file to flush recorded data.

