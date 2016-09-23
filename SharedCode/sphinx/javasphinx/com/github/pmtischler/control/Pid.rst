Pid
===

.. java:package:: com.github.pmtischler.control
   :noindex:

.. java:type:: public class Pid

   PID Controller: kp * (e + (integral(e) / ti) + (td * derivative(e))). https://en.wikipedia.org/wiki/PID_controller#Ideal_versus_standard_PID_form

Constructors
------------
Pid
^^^

.. java:constructor:: public Pid(double kp, double ti, double td, double integralMin, double integralMax)
   :outertype: Pid

   Creates a PID Controller.

   :param kp: Proportional factor to scale error to output.
   :param ti: The number of seconds to eliminate all past errors.
   :param td: The number of seconds to predict the error in the future.
   :param integralMin: The min of the running integral.
   :param integralMax: The max of the running integral.

Methods
-------
clampValue
^^^^^^^^^^

.. java:method:: public static double clampValue(double value, double min, double max)
   :outertype: Pid

   Clamps a value to a given range.

   :param value: The value to clamp.
   :param min: The min clamp.
   :param max: The max clamp.
   :return: The clamped value.

update
^^^^^^

.. java:method:: public double update(double desiredValue, double actualValue, double dt)
   :outertype: Pid

   Performs a PID update and returns the output control.

   :param desiredValue: The desired state value (e.g. speed).
   :param actualValue: The actual state value (e.g. speed).
   :param dt: The amount of time (sec) elapsed since last update.
   :return: The output which impacts state value (e.g. motor throttle).

