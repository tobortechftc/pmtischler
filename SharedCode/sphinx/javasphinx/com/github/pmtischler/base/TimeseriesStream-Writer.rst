.. java:import:: java.io EOFException

.. java:import:: java.io InputStream

.. java:import:: java.io ObjectInputStream

.. java:import:: java.io ObjectOutputStream

.. java:import:: java.io OutputStream

.. java:import:: java.util ArrayList

.. java:import:: java.util List

TimeseriesStream.Writer
=======================

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public static class Writer
   :outertype: TimeseriesStream

   Timeseries writer.

Constructors
------------
Writer
^^^^^^

.. java:constructor:: public Writer(OutputStream outputStream) throws Exception
   :outertype: TimeseriesStream.Writer

   Creates the Writer.

   :param outputStream: The output stream to write to.

Methods
-------
write
^^^^^

.. java:method:: public void write(DataPoint point) throws Exception
   :outertype: TimeseriesStream.Writer

   Writes a DataPoint to the output stream. Calls to this function must be done with non-decreasing timestamps.

