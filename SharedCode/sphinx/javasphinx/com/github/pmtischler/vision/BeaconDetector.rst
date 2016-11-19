.. java:import:: com.github.pmtischler.base Vector2d

.. java:import:: java.util ArrayList

.. java:import:: java.util Collections

.. java:import:: org.opencv.core Core

.. java:import:: org.opencv.core CvType

.. java:import:: org.opencv.core Mat

.. java:import:: org.opencv.core Size

.. java:import:: org.opencv.core TermCriteria

.. java:import:: org.opencv.imgproc Imgproc

BeaconDetector
==============

.. java:package:: com.github.pmtischler.vision
   :noindex:

.. java:type:: public class BeaconDetector

   Beacon detector. Uses imagery to determine where the blue and red beacon centroids are relative to the robot. Procedure: + Cluster pixel colors into K clusters by euclidean distance in RGB space. + Find the clusters closest to red and blue (N=2 colors of interst). + Find median along x,y for each color cluster. Assumptions: + Red and blue are in the top K colors- most of the image is the beacon. + The cluster centroid is the beacon center- most of red/blue is beacon. + The image is a decently cropped picture of the beacon, with red/blue mostly coming from the beacon and not the background.

Constructors
------------
BeaconDetector
^^^^^^^^^^^^^^

.. java:constructor:: public BeaconDetector()
   :outertype: BeaconDetector

   Create a beacon detector.

Methods
-------
detect
^^^^^^

.. java:method:: public Mat detect(Mat origImg, int totalClusters, Mat colors)
   :outertype: BeaconDetector

   Detects a beacon.

   :param origImg: The image to detect the beacon inside. Overwritten with logical image.
   :param totalClusters: The total clusters of colors to find.
   :param colors: Colors (Nx3 RGB) to search for. Naive blue/red is {[255, 0, 0], [0, 0, 255]}. You can take pictures with the camera to try and find a more representative color palette (picture of beacon does not get pure red and blue).
   :return: Color centers (Nx2).

detect
^^^^^^

.. java:method:: public Mat detect(Mat origImg, int totalClusters, Mat colors, int clusterIterations, double clusterEpsilon, int clusterAttempts)
   :outertype: BeaconDetector

   Detects a beacon.

   :param origImg: The image to detect the beacon inside. Overwritten with logical image.
   :param totalClusters: The total clusters of colors to find.
   :param colors: Colors (Nx3 RGB) to search for. Naive blue/red is {[255, 0, 0], [0, 0, 255]}. You can take pictures with the camera to try and find a more representative color palette (picture of beacon does not get pure red and blue).
   :param clusterIterations: Number of iterations when performing kmeans clustering.
   :param clusterEpsilon: Epsilon where clustering can be terminated.
   :param clusterAttempts: Number of attempts (e.g. random initialization) to cluster.
   :return: Color centers (Nx2).

