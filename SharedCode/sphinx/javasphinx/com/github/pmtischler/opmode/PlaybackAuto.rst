.. java:import:: com.github.pmtischler.base BlackBox

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Autonomous

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode OpMode

.. java:import:: java.io FileInputStream

PlaybackAuto
============

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @Autonomous @Disabled public class PlaybackAuto extends OpMode

   Playback autonomous mode. This mode playbacks the recorded values previously recorded by teleop.

Methods
-------
init
^^^^

.. java:method:: public void init()
   :outertype: PlaybackAuto

   Creates the playback.

loop
^^^^

.. java:method:: public void loop()
   :outertype: PlaybackAuto

   Plays back the recorded hardware at the current time.

stop
^^^^

.. java:method:: public void stop()
   :outertype: PlaybackAuto

   Closes the file.

