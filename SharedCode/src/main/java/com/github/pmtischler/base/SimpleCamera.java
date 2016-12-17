package com.github.pmtischler.base;

import android.os.Looper;
import android.os.Handler;
import android.content.Context;
import android.view.SurfaceView;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Camera for taking pictures.
 * Manages the Android camera lifecycle and returns OpenCV images.
 */
public class SimpleCamera implements Camera.PreviewCallback, Camera.PictureCallback {
    /**
     * Initializes the phone's camera.
     * Attempts to get the first camera, which should be the back camera.
     */
    public SimpleCamera(final Context context) {
        // Open the camera.
        camera = Camera.open();
        camera.setPreviewCallback(this);

        // Create surface on UI thread.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                synchronized(camera) {
                    // Create a surface to hold images.
                    surfaceView = new SurfaceView(context);
                    try {
                        camera.setPreviewDisplay(surfaceView.getHolder());
                    } catch (Exception e) {
                        Log.wtf(TAG, e);
                    }
                }
            }
        });
    }

    /**
     * Starts the process for capturing an image.
     * The image will be available from takeImage().
     * @return Whether the capture was started.
     */
    public boolean startCapture() {
        synchronized(camera) {
            if (surfaceView == null) {
                return false;
            }

            camera.startPreview();
            return true;
        }
    }

    /**
     * Gets the previously taken image.
     * @return The taken image, or null if the image is not available yet.
     */
    public Mat takeImage() {
        synchronized(this) {
            Mat taken = img;
            img = null;
            return taken;
        }
    }

    /**
     * Releases the camera.
     * Should be called when done with the camera to release it for future use.
     */
    public void stop() {
        synchronized(camera) {
            camera.release();
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i(TAG, "Preview frame received.");
        camera.takePicture(null, this, null);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Log.i(TAG, "Picture taken.");
        int channels = 3;
        int width = camera.getParameters().getPictureSize().width;
        int height = camera.getParameters().getPictureSize().height;
        if (data.length != width * height * channels) {
            // Data is not expected shape.
            Log.wtf(TAG, "Data from camera not expected size. Actual: " + data.length +
                         ", Expected: " + width * height * channels);
        }

        Mat parsedImg = new MatOfByte(data);
        parsedImg.reshape(channels, height);

        synchronized(this) {
            img = parsedImg;
        }
    }

    // Tag used for logging.
    private static final String TAG = "pmtischler.SimpleCamera";

    // Camera to take pictures with.
    private Camera camera;
    // Surface which holds pictures taken.
    private SurfaceView surfaceView;
    // The latest image.
    private Mat img;
}
