Beacon Color Detection
======================

In this tutorial you will use the phone's camera to detect which side of the
image has the red and blue beacon colors. This will rely on color detection,
clustering, and centroid algorithms. It will use the `OpenCV
<http://opencv.org/>`__ library to perform calculations, and has been already
added to the `pmtischler/ftc_app <https://github.com/pmtischler/ftc_app>`__
repo.

**Load OpenCV Libraries**. The first step is to setup `OpenCV`. Create a new
opmode, import the necessary dependencies, and load the libraries. Load the
code onto the phone and verify you can initialize without crashing. This
verifies `OpenCV` is setup correctly.

.. code-block:: java

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

    @Autonomous(name="BeaconPress", group="BeaconPress")
    public class BeaconPress extends OpMode {

        public void init() {
            // Load the OpenCV library.
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }
    }

**Take Pictures**. The next step is to take pictures with Android's Camera API
and convert it into `OpenCV` `Mat` class. This involves opening the camera,
initiating a picture camera, and handling the data when the picture is taken.
For now, we will take the picture and save it as a variable. After adding the
following code, look at the debug log and verify that you see the telemetry
line indicating an image was taken and is available for beacon detection.

.. code-block:: java

    public class BeaconPress extends OpMode implements Camera.PictureCallback {
        // The camera.
        private Camera camera;
        // The latest image.
        private Mat img;
        // Last image picture time.
        private double lastPictureTime;

        public void init() {
            // ... Other code

            // Open the camera.
            camera = Camera.open();
            // No picture yet.
            img = null;
            lastPictureTime = 0;
        }

        public void loop() {
            // Take picture every 1 second.
            if (time > 1 + lastPictureTime) {
                camera.takePicture(null, this, null);
                lastPictureTime = time;
            }

            // Image is needed to continue.
            if (img == null) {
                return;
            }
            // Log that image is available for detection.
            telemetry.addLine("Image available, detecting position.");
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
    }

**Detect Beacons**. Next we will use the
:doc:`../javasphinx/com/github/pmtischler/vision/BeaconDetector` class to
detect the centroids of red and blue, which can be used to determine whether
the red color is on the left or right.

.. code-block:: java

    // The detector.
    private BeaconDetector detector;
    // The colors of interest.
    private Mat colors;

    public void init() {
        // ... other code.

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
    }

    public void loop() {
        // ... other code, img is available.

        // Use the current image to detect red/blue.
        Mat positions = detector.detect(img, 5, colors);
        // If red on left, actuate left servo. Otherwise actuate right.
        if (positions.get(0, 0)[0] < positions.get(1, 0)[0]) {
            telemetry.addLine("Red is on the left.");
        } else {
            telemetry.addLine("Red is on the right.");
        }
    }

**Actuate Servos**. Finally, now that we can determine whether the red is on
the left or right, we will actuate a servo to press the beacon button.

.. code-block:: java

    // The servos.
    private Servo left;
    private Servo right;

    public void init() {
        // ... other code

        // Get the servos.
        left = (Servo)hardwareMap.get("left_servo");
        right = (Servo)hardwareMap.get("right_servo");
    }

    public void loop() {
        // ... other code

        // Based on red color position, actuate servo.
        if (positions.get(0, 0)[0] < positions.get(1, 0)[0]) {
            left.setPosition(1);
            right.setPosition(0);
        } else {
            left.setPosition(0);
            right.setPosition(1);
        }
    }

Congratulations! You now have an autonomous mode that can detect beacons and
actuate a servo to press the red side button.
