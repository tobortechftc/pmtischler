.. java:import:: android.hardware Camera

.. java:import:: com.github.pmtischler.vision BeaconDetector

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Autonomous

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode OpMode

.. java:import:: com.qualcomm.robotcore.hardware Servo

.. java:import:: org.opencv.core Core

.. java:import:: org.opencv.core CvType

.. java:import:: org.opencv.core Mat

.. java:import:: org.opencv.core MatOfByte

.. java:import:: org.opencv.imgcodecs Imgcodecs

BeaconPress
===========

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @Autonomous @Disabled public class BeaconPress extends OpMode implements Camera.PictureCallback

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

onPictureTaken
^^^^^^^^^^^^^^

.. java:method:: @Override public void onPictureTaken(byte[] data, Camera camera)
   :outertype: BeaconPress

