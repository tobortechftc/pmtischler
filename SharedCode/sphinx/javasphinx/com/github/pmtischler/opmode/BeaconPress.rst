.. java:import:: android.hardware Camera

.. java:import:: android.util Log

.. java:import:: com.github.pmtischler.base SimpleCamera

.. java:import:: com.github.pmtischler.vision BeaconDetector

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Autonomous

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode OpMode

.. java:import:: com.qualcomm.robotcore.hardware Servo

.. java:import:: org.opencv.core Core

.. java:import:: org.opencv.core CvType

.. java:import:: org.opencv.core Mat

BeaconPress
===========

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @Autonomous @Disabled public class BeaconPress extends OpMode

   Beacon detector which presses the red beacon side. It detects which sides are red and blue, and then actuates a servo.

Methods
-------
init
^^^^

.. java:method:: public void init()
   :outertype: BeaconPress

   Creates the detector, initializes the camera.

loop
^^^^

.. java:method:: public void loop()
   :outertype: BeaconPress

   Detects the colors, actuates the appropriate servo.

stop
^^^^

.. java:method:: public void stop()
   :outertype: BeaconPress

