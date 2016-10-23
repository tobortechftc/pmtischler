package com.github.pmtischler.opmode;

import android.content.Context;
import com.github.pmtischler.base.BlackBox;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.io.FileOutputStream;

/**
 * Recorded teleop mode.
 * This mode records the hardware which can later be played back in autonomous.
 * Select the manual control mode by changing the parent class.
 */
@TeleOp(name="pmtischler.RecordedTeleop", group="pmtischler")
@Disabled
public class RecordedTeleop extends MecanumDrive {
    /**
     * Extends teleop initialization to start a recorder.
     */
    public void init() {
        super.init();
        try {
            outputStream = hardwareMap.appContext.openFileOutput("recordedTeleop",
                                                                 Context.MODE_PRIVATE);
            recorder = new BlackBox.Recorder(hardwareMap, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

    /**
     * Extends teleop control to record hardware after loop.
     */
    public void loop() {
        super.loop();

        try {
            recorder.record(time);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

    /**
     * Closes the file to flush recorded data.
     */
    public void stop() {
        super.stop();

        try {
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // The output file stream.
    private FileOutputStream outputStream;
    // The hardware recorder.
    private BlackBox.Recorder recorder;
}
