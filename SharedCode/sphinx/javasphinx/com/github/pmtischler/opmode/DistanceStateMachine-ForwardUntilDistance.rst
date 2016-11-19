.. java:import:: com.github.pmtischler.base StateMachine

.. java:import:: com.github.pmtischler.base StateMachine.State

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Autonomous

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode OpMode

.. java:import:: com.qualcomm.robotcore.hardware DistanceSensor

.. java:import:: org.firstinspires.ftc.robotcore.external.navigation DistanceUnit

DistanceStateMachine.ForwardUntilDistance
=========================================

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: public class ForwardUntilDistance implements StateMachine.State
   :outertype: DistanceStateMachine

   Moves forward until a distance threshold is met.

Methods
-------
start
^^^^^

.. java:method:: @Override public void start()
   :outertype: DistanceStateMachine.ForwardUntilDistance

update
^^^^^^

.. java:method:: @Override public State update()
   :outertype: DistanceStateMachine.ForwardUntilDistance

