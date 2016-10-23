package com.github.pmtischler.opmode;

import com.github.pmtischler.base.BlackBox;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.FileInputStream;

/**
 * Playback autonomous mode.
 * This mode playbacks the recorded values previously recorded by teleop.
 */
@Autonomous(name="pmtischler.PlaybackAuto", group="pmtischler")
@Disabled
public class PlaybackAuto extends OpMode {
    /**
     * Creates the playback.
     */
    public void init() {
        try {
            inputStream = hardwareMap.appContext.openFileInput("recordedTeleop");
            player = new BlackBox.Player(inputStream, hardwareMap);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

    /**
     * Plays back the recorded hardware at the current time.
     */
    public void loop() {
        try {
            player.playback(time);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

    /**
     * Closes the file.
     */
    public void stop() {
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // The input file stream.
    private FileInputStream inputStream;
    // The hardware player.
    private BlackBox.Player player;
}
