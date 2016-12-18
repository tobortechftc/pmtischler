.. java:import:: java.io File

.. java:import:: java.io FileOutputStream

.. java:import:: android.os Looper

.. java:import:: android.os Handler

.. java:import:: android.os Environment

.. java:import:: android.content Context

.. java:import:: android.graphics PixelFormat

.. java:import:: android.graphics SurfaceTexture

.. java:import:: android.hardware Camera

.. java:import:: android.util Log

.. java:import:: android.view SurfaceHolder

.. java:import:: org.opencv.core Core

.. java:import:: org.opencv.core CvType

.. java:import:: org.opencv.core Mat

.. java:import:: org.opencv.core MatOfByte

.. java:import:: org.opencv.imgcodecs Imgcodecs

SimpleCamera
============

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public class SimpleCamera implements Camera.PreviewCallback, Camera.PictureCallback

   Camera for taking pictures. Manages the Android camera lifecycle and returns OpenCV images.

Constructors
------------
SimpleCamera
^^^^^^^^^^^^

.. java:constructor:: public SimpleCamera(Context context)
   :outertype: SimpleCamera

   Initializes the phone's camera. Attempts to get the first camera, which should be the back camera.

Methods
-------
onPictureTaken
^^^^^^^^^^^^^^

.. java:method:: @Override public void onPictureTaken(byte[] data, Camera camera)
   :outertype: SimpleCamera

onPreviewFrame
^^^^^^^^^^^^^^

.. java:method:: @Override public void onPreviewFrame(byte[] data, Camera camera)
   :outertype: SimpleCamera

startCapture
^^^^^^^^^^^^

.. java:method:: public boolean startCapture()
   :outertype: SimpleCamera

   Starts the process for capturing an image. The image will be available from takeImage().

   :return: Whether the capture was started.

stop
^^^^

.. java:method:: public void stop()
   :outertype: SimpleCamera

   Releases the camera. Should be called when done with the camera to release it for future use.

takeImage
^^^^^^^^^

.. java:method:: public Mat takeImage()
   :outertype: SimpleCamera

   Gets the previously taken image.

   :return: The taken image, or null if the image is not available yet.

