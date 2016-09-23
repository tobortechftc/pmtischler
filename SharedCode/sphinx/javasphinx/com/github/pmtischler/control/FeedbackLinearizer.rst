.. java:import:: com.github.pmtischler.base Vector2d

FeedbackLinearizer
==================

.. java:package:: com.github.pmtischler.control
   :noindex:

.. java:type:: public class FeedbackLinearizer

   Feedback linearization and wheel mapping to convert robot velocity to wheels. Feedback linearization to convert robot velocity to forward and angular. v_x = desired velocity x in robot frame. v_y = desired velocity y in robot frame. ε = feedback linearization epsilon. v_f = forward velocity. ω = angular velocity. Equation: v_f = v_x. ω = v_y / ε. Convert forward and angular velocity into wheel velocities: ω = angular velocity. ω_{L,R} = left and right wheel angular velocities. R = wheel radius. L = baseline between wheels. v_f = forward velocity. Characteristic equations: ω = R (ω_R - ω_L) / L. v_f = R (ω_R + ω_L) / 2. Solving for ω_R and ω_L: ω_L = V_f / R - ω * L / (2R). ω_R = V_f / R + ω * L / (2R).

Constructors
------------
FeedbackLinearizer
^^^^^^^^^^^^^^^^^^

.. java:constructor:: public FeedbackLinearizer(double wheelRadius, double wheelBaseline, double feedbackEpsilon)
   :outertype: FeedbackLinearizer

   Creates a FeedbackLinearizer with robot and control paramters.

   :param wheelRadius: The wheel radius; R in the equations.
   :param wheelBaseline: The distance between the wheels; L in the equations.
   :param feedbackEpsilon: The feedback algo epsilon; ε in the equations.

Methods
-------
convertToWheelVelocities
^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Vector2d convertToWheelVelocities(double forwardVelocity, double angularVelocity)
   :outertype: FeedbackLinearizer

   Convert a forward and angular velocities to wheel velocities.

   :param forwardVelocity: The forward velocity.
   :param angularVelocity: The angular velocity (radians/sec).
   :return: The left wheel (x) and right wheel (y) angular velocities.

feedbackLinearize
^^^^^^^^^^^^^^^^^

.. java:method:: public Vector2d feedbackLinearize(Vector2d velocity)
   :outertype: FeedbackLinearizer

   Convert a velocity in robot frame to forward and angular velocities.

   :param velocity: The desired velocity of the robot in robot frame.
   :return: The forward (x) and angular (y) velocities (radians/sec).

getWheelVelocitiesForRobotVelocity
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

.. java:method:: public Vector2d getWheelVelocitiesForRobotVelocity(Vector2d velocity)
   :outertype: FeedbackLinearizer

   Gets wheel velocities to match a desired robot velocity.

   :param velocity: The desired velocity of the robot in robot frame.
   :return: The left wheel (x) and right wheel (y) angular velocities.

