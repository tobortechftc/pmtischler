.. java:import:: java.util Arrays

.. java:import:: java.util Collections

.. java:import:: java.util List

Mecanum
=======

.. java:package:: com.github.pmtischler.control
   :noindex:

.. java:type:: public class Mecanum

   Mecanum wheel drive calculations. Input controls: V_d = desired robot speed. theta_d = desired robot velocity angle. V_theta = desired robot rotational speed. Characteristic equations: V_{front,left} = V_d sin(theta_d + pi/4) + V_theta V_{front,right} = V_d cos(theta_d + pi/4) - V_theta V_{back,left} = V_d cos(theta_d + pi/4) + V_theta V_{back,right} = V_d sin(theta_d + pi/4) - V_theta

Methods
-------
motionToWheels
^^^^^^^^^^^^^^

.. java:method:: public static Wheels motionToWheels(double vD, double thetaD, double vTheta)
   :outertype: Mecanum

   Gets the wheel powers corresponding to desired motion.

   :param vD: The desired robot speed. [-1, 1]
   :param thetaD: The angle at which the robot should move. [0, 2PI]
   :param vTheta: The desired rotation velocity. [-1, 1]
   :return: The wheels with clamped powers. [-1, 1]

