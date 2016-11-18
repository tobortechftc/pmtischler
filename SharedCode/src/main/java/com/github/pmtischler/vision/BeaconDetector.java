package com.github.pmtischler.vision;

import com.github.pmtischler.base.Vector2d;
import java.util.ArrayList;
import java.util.Collections;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.Imgproc;

/**
 * Beacon detector.
 * Uses imagery to determine where the blue and red beacon centroids are
 * relative to the robot.
 * Procedure:
 *   + Cluster pixel colors into K clusters by euclidean distance in RGB space.
 *   + Find the clusters closest to red and blue (N=2 colors of interst).
 *   + Find median along x,y for each color cluster.
 * Assumptions:
 *   + Red and blue are in the top K colors- most of the image is the beacon.
 *   + The cluster centroid is the beacon center- most of red/blue is beacon.
 *   + The image is a decently cropped picture of the beacon, with red/blue
 *     mostly coming from the beacon and not the background.
 */
public class BeaconDetector {
    /**
     * Create a beacon detector.
     */
    public BeaconDetector() { }

    /**
     * Detects a beacon.
     * @param origImg The image to detect the beacon inside. Overwritten with logical image.
     * @param totalClusters The total clusters of colors to find.
     * @param colors Colors (Nx3 RGB) to search for. Naive red/blue is {[255,
     * 0, 0], [0, 0, 255]}. You can take pictures with the camera to try and
     * find a more representative color palette (picture of beacon does not get
     * pure red and blue).
     * @return Color centers (Nx2).
     */
    public Mat detect(Mat origImg, int totalClusters, Mat colors) {
        // Parameters selected for problem.
        int kClusterIterations = 5;
        double kClusterEpsilon = 1.0;
        int kClusterAttempts = 3;

        // Resize image to trade accuracy for speed.
        Mat img = new Mat();
        Imgproc.resize(origImg, img,
                       new Size(origImg.width()/9, origImg.height()/9));
        // Change format due to unsigned.
        Mat converted = new Mat();
        img.convertTo(converted, CvType.CV_32FC3);
        img = converted;

        // Cluster pixels into K color clusters.
        Mat colorSeq = new Mat(img.width() * img.height(), 3, CvType.CV_32F);
        for (int y = 0; y < img.height(); y++) {
            for (int x = 0; x < img.width(); x++) {
                int index = y * img.width() + x;
                float[] colorInt = new float[3];
                img.get(y, x, colorInt);
                for (int c = 0; c < 3; c++) {
                    float[] colorDouble = {colorInt[c]};
                    colorSeq.put(index, c, colorDouble);
                }
            }
        }
        Mat labels = new Mat();  // Best cluster for each color.
        Mat centers = new Mat(colors.size(), CvType.CV_32F);  // Center of each cluster.
        Core.kmeans(colorSeq, totalClusters, labels,
                    new TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER,
                                     kClusterIterations, kClusterEpsilon),
                    kClusterAttempts, Core.KMEANS_RANDOM_CENTERS, centers);

        // Find clusters closest to provided colors.
        int[] closestCluster = new int[colors.height()];
        for (int i = 0; i < closestCluster.length; i++) {
            Mat color = colors.submat(i, i + 1, 0, 3);
            double closestDist = Double.MAX_VALUE;
            for (int j = 0; j < centers.size().height; j++) {
                Mat center = centers.submat(j, j + 1, 0, 3);
                Mat diff = new Mat(1, 3, CvType.CV_32F);
                Core.subtract(center, color, diff);
                double dist = Core.norm(diff);
                if (dist < closestDist) {
                    closestDist = dist;
                    closestCluster[i] = j;
                }
            }
        }

        // Find middle (median on each axis) along clusters of interest.
        // Unclear how to do in O(N) in Java, use O(NlogN).
        Mat colorPositions = Mat.zeros(colors.height(), 2, CvType.CV_64F);
        Mat rendered = Mat.zeros(img.size(), CvType.CV_8UC3);
        for (int i = 0; i < closestCluster.length; i++) {
            Mat center = centers.submat(closestCluster[i], closestCluster[i] + 1, 0, 3);
            byte[] color = new byte[3];
            color[0] = (byte)center.get(0, 0)[0];
            color[1] = (byte)center.get(0, 1)[0];
            color[2] = (byte)center.get(0, 2)[0];
            ArrayList<Integer> xPos = new ArrayList<Integer>();
            ArrayList<Integer> yPos = new ArrayList<Integer>();
            for (int y = 0; y < img.height(); y++) {
                for (int x = 0; x < img.width(); x++) {
                    int index = y * img.width() + x;
                    int[] label = new int[1];
                    labels.get(index, 0, label);
                    if (label[0] == closestCluster[i]) {
                        rendered.put(y, x, color);
                        xPos.add(x);
                        yPos.add(y);
                    }
                }
            }
            if (xPos.size() == 0) {
                continue;
            }
            Collections.sort(xPos);
            Collections.sort(yPos);

            // Color the center.
            byte[] mcolor = {color[0], (byte)255, color[2]};
            rendered.put(yPos.get(yPos.size()/2), xPos.get(xPos.size()/2), mcolor);

            double[] x = {xPos.get(xPos.size()/2) / (float)img.width()};
            double[] y = {yPos.get(yPos.size()/2) / (float)img.height()};
            colorPositions.put(i, 0, x);
            colorPositions.put(i, 1, y);
        }
        rendered.copyTo(origImg);

        return colorPositions;
    }
}
