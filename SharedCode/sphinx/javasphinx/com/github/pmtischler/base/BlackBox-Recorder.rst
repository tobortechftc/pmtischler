.. java:import:: com.qualcomm.robotcore.hardware DcMotor

.. java:import:: com.qualcomm.robotcore.hardware HardwareDevice

.. java:import:: com.qualcomm.robotcore.hardware HardwareMap

.. java:import:: com.qualcomm.robotcore.hardware Servo

.. java:import:: java.io InputStream

.. java:import:: java.io OutputStream

.. java:import:: java.util List

BlackBox.Recorder
=================

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public static class Recorder
   :outertype: BlackBox

   Writes hardware state as a timeseries stream.

Constructors
------------
Recorder
^^^^^^^^

.. java:constructor:: public Recorder(HardwareMap hardware, OutputStream outputStream) throws Exception
   :outertype: BlackBox.Recorder

   Creates the recorder.

   :param hardware: The hardware to record.
   :param outputStream: The output stream to write.

Methods
-------
record
^^^^^^

.. java:method:: public void record(String deviceName, double time) throws Exception
   :outertype: BlackBox.Recorder

   Records the hardware at the time.

   :param deviceName: The device to record.
   :param time: The time to record hardware at (seconds).

