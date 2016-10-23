Record & Playback
=================

In this tutorial you will write your first simple autonomous program. This
program will record the hardware state during each call to ``loop`` while the
robot is in manual mode. When the program is in autonomous mode, it'll play
back that recorded hardware log to mimic the manual operation.

Note this is not a robust autonomous method. It doesn't handle any changes in
robot or environment. If the robot changes weight, changes air drag, etc the
exact motor powers needed to turn will change, so the manual log previously
recorded will not have the same affect. If the environment changes, the robot
will not be able to adapt to avoid obstacles, deal with slippery conditions,
etc. In future more advanced tutorials we'll learn how to make better autonomy
programs.

:doc:`../javasphinx/com/github/pmtischler/base/BlackBox`. One of the custom
additions in `pmtischler/ftc_app <https://github.com/pmtischler/ftc_app>`__ is
a class to record the robot's hardware state and play it back. That is, it'll
record the motors and servo states during Teleop mode, and then playback that
recorded log during Autonomous mode. The robot will mimic the actions of the
manual driver. This class uses the name of the hardware devices, so if you
change the name of the devices you will need to re-record.

**Create Recorded Teleop Class.** First we will create a new class called
``RecordedTeleop``. This class will inherit from the Teleop mode you've
developed, like ``TankDrive``. This will reuse all the code to drive the robot,
and we'll extend it to record the hardware.

.. code-block:: java

    package org.firstinspires.ftc.teamcode;

    import android.content.Context;
    import com.github.pmtischler.base.BlackBox;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import java.io.FileOutputStream;

    @Teleop(name="RecordedTeleop", group="RecordedTeleop")
    public class RecordedTeleop extends TankDrive {

    }

You'll notice we've imported the ``BlackBox`` code. We've also imported
``Context`` and ``FileOutputStream`` which will be needed to open a file for
which to write the data.

**Open File, Construct Recorder**. We will override the ``init`` function to
create a file to write data to, and then create a ``BlackBox.Recorder`` that
will use the file to write hardware data. If this fails, we'll want to print
the error and stop the robot, so we wrap the process in a try-catch.

.. code-block:: java

    public void init() {
        super.init();  // TankDrive teleop initialization.

        try {
            // Open a file named "recordedTeleop" in the app's folder.
            outputStream = hardwareMap.appContext.openFileOutput("recordedTeleop",
                                                                 Context.MODE_PRIVATE);
            // Setup a hardware recorder.
            recorder = new BlackBox.Recorder(hardwareMap, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

**Careful: File Overwriting**. The file we created will be created each time
the program is run. This means that the second time you run the ``OpMode``
it'll overwrite the previously recorded file. You need to make sure you backup
this file after recording so that you don't accidentally lose a good file.

**Record over Time**. Now we want to record the state of the hardware to the
file. The ``BlackBox.Recorder`` has a ``record`` function which does exactly
that. We will call it with the current time so the hardware state is written
with the timestamp. Similar to setup, if it fails we wan't to print the error
and stop the robot.

.. code-block:: java

    public void loop() {
        super.loop();  // TankDrive teleop control code.

        try {
            // Record the hardware state at the current time.
            recorder.record(time);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

**Close to Flush Contents**. When the Teleop mode is complete we need to close
the file so the data is written. If there is a problem we will print the error,
but as we are already stopping we don't need to request the ``OpMode`` stop.

.. code-block:: java

    public void stop() {
        super.stop();  // TankDrive stop code.

        try {
            // Close the file to write recorded data.
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

You now have a Teleop program which can use your base Teleop code to drive, and
can use the ``BlackBox`` to record telemetry data. Run your program and test it
out. Check the App's data folder to see that the file was written, and that it
has data (has non-zero file size).

**Create Playback Autonomous Class**. Now it's time to create the autonomous
``OpMode`` which will playback the previously recorded file. Create a new class
called ``PlaybackAuto``. This time it will inherit from ``OpMode`` instead of a
Teleop class.

.. code-block:: java

    package org.firstinspires.ftc.teamcode;

    import com.github.pmtischler.base.BlackBox;
    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import java.io.FileInputStream;

    @Autonomous(name="PlaybackAuto", group="PlaybackAuto")
    public class PlaybackAuto extends OpMode {

    }

**Open File, Construct Player**. Similar to the recorder, we will now open the
file and construct a player.

.. code-block:: java

    public void init() {
        try {
            // Open previously written file full of hardware data.
            inputStream = hardwareMap.appContext.openFileInput("recordedTeleop");
            // Create a player to playback the hardware log.
            player = new BlackBox.Player(inputStream, hardwareMap);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

**Playback Recorded Data**. Similar to the recorder, we will now call
``playback`` on the player. This will determine the state of the robot when it
was recording at this time in the match, and then set the motors and servos to
those values.

.. code-block:: java

    public void loop() {
        try {
            // Update the hardware to mimic human during recorded Teleop.
            player.playback(time);
        } catch (Exception e) {
            e.printStackTrace();
            requestOpModeStop();
        }
    }

**Close the File**. At the end of the mode, close the file. This is necessary
to terminate cleanly, so the robot can run multiple times without error.

.. code-block:: java

    public void stop() {
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

Congratulations! You now have an autonomous mode that can mimic a human driver.
Test out the mode, see if it accurately mimics the driver. Does it do the same
thing every time? How sensitive is it to initial conditions (e.g. initial
heading or position on the field)?
