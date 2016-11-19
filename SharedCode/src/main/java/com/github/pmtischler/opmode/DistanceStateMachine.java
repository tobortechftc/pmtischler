package com.github.pmtischler.opmode;

import com.github.pmtischler.base.StateMachine;
import com.github.pmtischler.base.StateMachine.State;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * State machine to move up to distance.
 */
@Autonomous(name="pmtischler.DistanceStateMachine", group="pmtischler")
@Disabled
public class DistanceStateMachine extends MecanumDrive {

    /**
     * Moves forward until a distance threshold is met.
     */
    public class ForwardUntilDistance implements StateMachine.State {
        @Override
        public void start() {
        }

        @Override
        public State update() {
            if (distanceSensor.getDistance(DistanceUnit.INCH) > 3) {
                setDrive(1, 0, 0);
                return this;
            } else {
                setDrive(0, 0, 0);
                return leftForTime;
            }
        }
    }

    /**
     * Moves left for a specific amount of time.
     */
    public class LeftForTime implements StateMachine.State {
        @Override
        public void start() {
            startTime = time;
        }

        @Override
        public State update() {
            if (time - startTime < 2) {
                setDrive(1, Math.PI, 0);
                return this;
            } else {
                setDrive(0, 0, 0);
                return null;
            }
        }

        private double startTime;
    }

    /**
     * Initializes the state machine.
     */
    public void init() {
        super.init();

        // Create the states.
        forwardUntilDistance = new ForwardUntilDistance();
        leftForTime = new LeftForTime();
        // Start the state machine with forward state.
        machine = new StateMachine(forwardUntilDistance);
    }

    /**
     * Runs the state machine.
     */
    public void loop() {
        machine.update();
    }

    // Distance sensor reading forward.
    DistanceSensor distanceSensor;

    // The state machine manager.
    private StateMachine machine;
    // Move forward until distance.
    private ForwardUntilDistance forwardUntilDistance;
    // Move left for time.
    private LeftForTime leftForTime;
}
