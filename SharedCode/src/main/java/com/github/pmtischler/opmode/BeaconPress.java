package com.github.pmtischler.opmode;

import android.hardware.Camera;
import android.util.Log;
import com.github.pmtischler.base.SimpleCamera;
import com.github.pmtischler.vision.BeaconDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Beacon detector which presses the red beacon side.
 * It detects which sides are red and blue, and then actuates a servo.
 */
@Autonomous(name="pmtischler.BeaconPress", group="pmtischler")
@Disabled
public class BeaconPress extends OpMode {
    /**
     * Creates the detector, initializes the camera.
     */
    public void init() {
        // Load the OpenCV library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Create the camera.
        camera = new SimpleCamera(hardwareMap.appContext);
        // No picture yet.
        lastPictureTime = 0;

        // Create the detector.
        detector = new BeaconDetector();

        // Create the colors of interest.
        int[][] red_blue = {{0, 0, 255}, {255, 0, 0}};
        colors = new Mat(red_blue.length, 3, CvType.CV_32F);
        for (int i = 0; i < red_blue.length; i++) {
            for (int d = 0; d < 3; d++) {
                double[] c = {red_blue[i][d]};
                colors.put(i, d, c);
            }
        }

        // Get the servos.
        left = (Servo)hardwareMap.get("left_servo");
        right = (Servo)hardwareMap.get("right_servo");
    }

    /**
     * Detects the colors, actuates the appropriate servo.
     */
    public void loop() {
        // Take picture every 1 second.
        if (time > 1 + lastPictureTime) {
            telemetry.addLine("Taking picture.");
            camera.startCapture();
            lastPictureTime = time;
        }

        // Get the latest image.
        Mat img = camera.takeImage();
        if (img == null) {
            // Image is not yet available.
            return;
        }
        telemetry.addLine("Image available, detecting position.");

        // Use the current image to detect red/blue.
        Mat positions = detector.detect(img, 5, colors);
        // If red on left, actuate left servo. Otherwise actuate right.
        if (positions.get(0, 0)[0] < positions.get(1, 0)[0]) {
            left.setPosition(1);
            right.setPosition(0);
        } else {
            left.setPosition(0);
            right.setPosition(1);
        }
    }

    public void stop() {
        // Stop the camera so can be used in future runs.
        camera.stop();
        camera = null;
    }

    // Tag used for logging.
    private static final String TAG = "pmtischler.BeaconPress";

    // Used to take pictures of the beacon.
    private SimpleCamera camera;
    // Last image picture time.
    private double lastPictureTime;
    // The detector.
    private BeaconDetector detector;
    // The colors of interest.
    private Mat colors;
    // The servos.
    private Servo left;
    private Servo right;
}
