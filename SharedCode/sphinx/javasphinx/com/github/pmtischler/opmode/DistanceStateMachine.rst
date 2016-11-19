.. java:import:: com.github.pmtischler.base StateMachine

.. java:import:: com.github.pmtischler.base StateMachine.State

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Autonomous

.. java:import:: com.qualcomm.robotcore.eventloop.opmode Disabled

.. java:import:: com.qualcomm.robotcore.eventloop.opmode OpMode

.. java:import:: com.qualcomm.robotcore.hardware DistanceSensor

.. java:import:: org.firstinspires.ftc.robotcore.external.navigation DistanceUnit

DistanceStateMachine
====================

.. java:package:: com.github.pmtischler.opmode
   :noindex:

.. java:type:: @Autonomous @Disabled public class DistanceStateMachine extends MecanumDrive

   State machine to move up to distance.

Fields
------
distanceSensor
^^^^^^^^^^^^^^

.. java:field::  DistanceSensor distanceSensor
   :outertype: DistanceStateMachine

Methods
-------
init
^^^^

.. java:method:: public void init()
   :outertype: DistanceStateMachine

   Initializes the state machine.

loop
^^^^

.. java:method:: public void loop()
   :outertype: DistanceStateMachine

   Runs the state machine.

