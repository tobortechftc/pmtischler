.. java:import:: java.io EOFException

.. java:import:: java.io InputStream

.. java:import:: java.io ObjectInputStream

.. java:import:: java.io ObjectOutputStream

.. java:import:: java.io OutputStream

.. java:import:: java.util ArrayList

.. java:import:: java.util List

TimeseriesStream.DataPoint
==========================

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public static class DataPoint implements java.io.Serializable
   :outertype: TimeseriesStream

   Data point in a time series. A variable has a Datapoint's value until the next instance in the stream.

Fields
------
timestamp
^^^^^^^^^

.. java:field:: public final double timestamp
   :outertype: TimeseriesStream.DataPoint

value
^^^^^

.. java:field:: public final double value
   :outertype: TimeseriesStream.DataPoint

varname
^^^^^^^

.. java:field:: public final String varname
   :outertype: TimeseriesStream.DataPoint

Constructors
------------
DataPoint
^^^^^^^^^

.. java:constructor:: public DataPoint(String varname, double timestamp, double value)
   :outertype: TimeseriesStream.DataPoint

   Creates a DataPoint.

   :param varname: The name of the variable.
   :param timestamp: The time of the data point.
   :param value: The value of the variable.

