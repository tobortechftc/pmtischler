Velocity Vortex Autonomy
========================

At this point, we have learned all the building blocks to make an advanced
autonomy program for the Velocity Vortex game. In this tutorial we will use
these building blocks to autonomously shoot the balls into the vortex, drive to
each beacon and press the team's colored button, nock the larger ball of the
center, and park on the ramp. The first step is to start with a state machine.

**State Machine**. The following shows the state machine that can be
implemented. Start off by creating `StateMachine.State` objects which represent
these states. Add comments noting when the state machine will transition.

.. graphviz::

    digraph G {
        start [label="START"];
        shoot [label="Shoot Ball"];
        drive_to_first_beacon [label="Drive to First Beacon"];
        press_beacon [label="Press Beacon"];
        drive_to_second_beacon [label="Drive to Second Beacon"];
        drive_ball_ramp [label="Drive Into Ball, Then Drive Onto Ramp"];
        end [label="END"];

        start -> shoot;
        shoot -> drive_to_first_beacon [label="Playback Done"];
        drive_to_first_beacon -> press_beacon [label="Playback Done"];
        press_beacon -> drive_to_second_beacon [label="First Detection and Press Done"];
        drive_to_second_beacon -> press_beacon [label="Playback Done"];
        press_beacon -> drive_ball_ramp [label="Second Detection and Press Done"];
        drive_ball_ramp -> end [label="Playback Done"];
    }

**Record & Playback**. Shooting the balls, driving to beacons, driving into the
big ball, and drive onto the ramp can be implemented as record and playback.
Record a manual drive for each of these states, and then playback as part of
the state machine. Each file will start from time `t=0`, but the playback will
start at different times (not `t=0`), so you will need to subtract the start
time when executing playback. You can then transition between states after
enough time has elapsed to fully playback the current file.

**Beacon Detection**. Detecting the beacon and pressing the team's colored
button can be done using the camera and detection code from a previous
tutorial. You can write the program with a variable indicating whether you are
on the Blue or Red team, which you can use to determine whether your button is
the left or right one. You can then create a subclass per color which inherits
from this base autonomous class, where the subclass specifies the value for the
color variable. At the start of the match, you can select the opmode
corresponding to your team's color.

It's up to you to leverage what you've learned to build this end-to-end
autonomous program. Good luck!
