.. java:import:: com.qualcomm.robotcore.hardware DcMotor

.. java:import:: com.qualcomm.robotcore.hardware HardwareDevice

.. java:import:: com.qualcomm.robotcore.hardware HardwareMap

.. java:import:: com.qualcomm.robotcore.hardware Servo

.. java:import:: java.io InputStream

.. java:import:: java.io OutputStream

.. java:import:: java.util List

BlackBox.Player
===============

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public static class Player
   :outertype: BlackBox

   Reads hardware state from a timeseries stream and applies it.

Constructors
------------
Player
^^^^^^

.. java:constructor:: public Player(InputStream inputStream, HardwareMap hardware) throws Exception
   :outertype: BlackBox.Player

   Creates the player.

   :param inputStream: The input stream to read from.
   :param hardware: The hardware to manipulate.

Methods
-------
playback
^^^^^^^^

.. java:method:: public void playback(double time) throws Exception
   :outertype: BlackBox.Player

   Playbacks the hardware up to the time.

   :param time: The time to playback up to (seconds).

