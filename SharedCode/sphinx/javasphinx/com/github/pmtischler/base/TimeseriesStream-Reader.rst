.. java:import:: java.io EOFException

.. java:import:: java.io InputStream

.. java:import:: java.io ObjectInputStream

.. java:import:: java.io ObjectOutputStream

.. java:import:: java.io OutputStream

.. java:import:: java.util ArrayList

.. java:import:: java.util List

TimeseriesStream.Reader
=======================

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public static class Reader
   :outertype: TimeseriesStream

   Timeseries reader.

Constructors
------------
Reader
^^^^^^

.. java:constructor:: public Reader(InputStream inputStream) throws Exception
   :outertype: TimeseriesStream.Reader

   Creates the Reader.

   :param inputStream: The input stream to read from.

Methods
-------
read
^^^^

.. java:method:: public DataPoint read() throws Exception
   :outertype: TimeseriesStream.Reader

   Reads a DataPoint from the input stream.

   :return: DataPoint if available, null otherwise.

readUntil
^^^^^^^^^

.. java:method:: public List<DataPoint> readUntil(double time) throws Exception
   :outertype: TimeseriesStream.Reader

   Reads all DataPoint up to specific time.

   :param time: The timestamp to read up to (seconds, inclusive).
   :return: The DataPoint read.

