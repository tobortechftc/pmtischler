package com.github.pmtischler.vision;

import com.github.pmtischler.base.Vector2d;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Tests correctness of the BeaconDetector.
 */
public class BeaconDetectorTest {
    // The comparison threshold.
    private static final double diffThresh = 0.00001;
    // The image to detect on.
    private Mat img;
    // The colors to search for.
    private Mat colors;
    // The detector.
    private BeaconDetector detector;

    @Before
    public void setUp() throws Exception {
        // Load the OpenCV library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the test image.
        img = Imgcodecs.imread("testdata/beacon_test.png");

        // Create detector.
        detector = new BeaconDetector();

        // Set colors to search for.
        int[] red = {0, 0, 255};
        int[] blue = {255, 0, 0};
        int[][] acolors = {red, blue};
        colors = new Mat(acolors.length, 3, CvType.CV_32F);
        for (int i = 0; i < acolors.length; i++) {
            double[] c = new double[1];
            for (int j = 0; j < acolors[i].length; j++) {
                c[0] = acolors[i][j];
                colors.put(i, j, c);
            }
        }
    }

    @Test
    // Test detect.
    public void testDetect() throws Exception {
        Mat pos = detector.detect(img, 5, colors);
        Imgcodecs.imwrite("testdata/beacon_test_result.png", img);

        assertThat(pos.get(0, 0)[0], greaterThan(0.27));
        assertThat(pos.get(0, 0)[0], lessThan(0.47));

        assertThat(pos.get(1, 0)[0], greaterThan(0.5));
        assertThat(pos.get(1, 0)[0], lessThan(0.7));
    }
}
