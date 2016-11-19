package com.github.pmtischler.opmode;

import android.hardware.Camera;
import com.github.pmtischler.vision.BeaconDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Beacon detector which presses the red beacon side.
 * It detects which sides are red and blue, and then actuates a servo.
 */
@Autonomous(name="pmtischler.BeaconPress", group="pmtischler")
@Disabled
public class BeaconPress extends OpMode implements Camera.PictureCallback {
    /**
     * Creates the detector, initializes the camera.
     */
    public void init() {
        // Load the OpenCV library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Open the camera.
        camera = Camera.open();
        // No picture yet.
        img = null;
        lastPictureTime = 0;

        // Create the detector.
        detector = new BeaconDetector();

        // Create the colors of interest.
        int[][] red_blue = {{0, 0, 255}, {255, 0, 0}};
        colors = new Mat(red_blue.length, 3, CvType.CV_32F);
        for (int i = 0; i < red_blue.length; i++) {
            for (int d = 0; d < 3; d++) {
                int[] c = {red_blue[i][d]};
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
            camera.takePicture(null, this, null);
            lastPictureTime = time;
        }

        // Need an image to continue.
        if (img == null) {
            return;
        }
        telemetry.addLine("Image available, detecting position.");
        // Use the current image to detect red/blue.
        Mat positions = detector.detect(img, 5, colors);
        // If red on left, actuate left servo. Otherwise actuate right.
        if (positions.get(0, 0)[0] < positions.get(1, 0)[0]) {
            left.setPosition(90);
            right.setPosition(0);
        } else {
            left.setPosition(0);
            right.setPosition(90);
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        int channels = 3;
        int width = camera.getParameters().getPictureSize().width;
        int height = camera.getParameters().getPictureSize().height;
        if (data.length != width * height * channels) {
            // Data is not expected shape.
            throw new IllegalArgumentException(
                    "data from camera not expected size.");
        }

        Mat parsedImg = new MatOfByte(data);
        parsedImg.reshape(channels, height);
        img = parsedImg;
    }

    // The camera.
    private Camera camera;
    // The latest image.
    private Mat img;
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
