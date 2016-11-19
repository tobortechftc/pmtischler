State Machine
=============

In this tutorial you will implement a `Finite State Machine
<https://en.wikipedia.org/wiki/Finite-state_machine>`__. A state machine is
made up of a series of states, and a series of transitions between states. A
state can be something like "drive forward until distance sensor reads less
than 3 inches", and a transition can be something like "when it reads less than
3 inches then change to the drive for time state". In this tutorial, we will
implement an autonomous mode that drives forward until the distance sensor
reads less than 3 inches, after which it will drive left for 2 seconds. It uses
the previous :doc:`mecanum` tutorial for driving, and requires a `setDrive(vD,
vTheta, thetaD, vTheta)` function.

**Program Structure**. The first step is to create a skeleton of the final
program. Notice that there are two internal classes representing the states of
the state machine, one for moving forward until a distance threshold is met,
and another which moves left for an amount of time. Each state has 2 functions,
`start` which is called when the state machine first enters the state, and
`update` which updates the robot in the state and returns the next state to be
in. The state machine transitions when the current state's `update` function
returns a different state.

.. code-block:: java

    import com.github.pmtischler.base.StateMachine;
    import com.github.pmtischler.base.StateMachine.State;
    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.hardware.DistanceSensor;
    import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

    @Autonomous(name="ForwardThenLeftStateMachine", group="ForwardThenLeftStateMachine")
    public class ForwardThenLeftStateMachine extends MecanumDrive {

        /**
         * Moves forward until a distance threshold is met.
         */
        public class ForwardUntilDistance implements StateMachine.State {
            @Override
            public void start() {
            }

            @Override
            public State update() {
            }
        }

        /**
         * Moves left for a specific amount of time.
         */
        public class LeftForTime implements StateMachine.State {
            @Override
            public void start() {
            }

            @Override
            public State update() {
            }
        }

        /**
         * Initializes the state machine.
         */
        public void init() {
        }

        /**
         * Runs the state machine.
         */
        public void loop() {
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

**Setup & Run State Machine**. The next step is to setup and run the state
machine. Setup involves instantiating each state, creating a state machine
manager, and providing it with an initial state. Running involves telling the
state machine manager to update, which will call the current state's `update`
function, and track the returned state for the next update.

.. code-block:: java

    public void init() {
        super.init();  // Initialize mecanum drive.

        // Create the states.
        forwardUntilDistance = new ForwardUntilDistance();
        leftForTime = new LeftForTime();
        // Start the state machine with forward state.
        machine = new StateMachine(forwardUntilDistance);
    }

    public void loop() {
        machine.update();  // Run one update in state machine.
    }

**Define Drive Until Distance State**. The next step is to define the first
state, driving forward until a distance is met. There is no initialization
needed for the state. Updating the state involves checking the distance sensor.
If we haven't yet reached the desired distance, we command the motors to drive
forward. If we have reached the desired distance, we return the next state:
driving left until for an amount of time.

.. code-block:: java

    public class ForwardUntilDistance implements StateMachine.State {
        @Override
        public void start() {
        }

        @Override
        public State update() {
            if (distanceSensor.getDistance(DistanceUnit.INCH) > 3) {
                // Haven't yet reached distance, drive forward.
                setDrive(1, 0, 0);
                return this;
            } else {
                // Reached distance, switch to left for time state.
                setDrive(0, 0, 0);
                return leftForTime;
            }
        }
    }

**Define Drive For Time State**. The next step is to define the second state,
driving left for an amount of time. To initialize the state, save the time the
state started. To update the state, drive left. The state is done when the
difference between current time and start time is greater than 2 seconds. The
effect will be to drive left for 2 seconds.

.. code-block:: java

    public class LeftForTime implements StateMachine.State {
        @Override
        public void start() {
            startTime = time;
        }

        @Override
        public State update() {
            if (time - startTime < 2) {
                // Less than 2 seconds elapsed, drive left.
                setDrive(1, Math.PI, 0);
                return this;
            } else {
                // 2 seconds elapsed, stop and terminate state machine.
                setDrive(0, 0, 0);
                return null;
            }
        }

        private double startTime;
    }

Congratulations! You can now use a state machine to define multiple robot
states and when to transition between them. You can use this to implement much
more complex state machines for complex autonomous behavior.
