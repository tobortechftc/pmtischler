Dead Reckoning
==============

In this tutorial you will perform `Dead Reckoning
<https://en.wikipedia.org/wiki/Dead_reckoning>`__, a technique to integrate
sensor values to estimate your current position in the world. From a given
starting position, this will predict where on the competition field you are. In
later tutorials we will use this position estimate to plan a path to a specific
position on the field.

Dead Reckoning's estimate becomes inaccurate quickly. It only deals with
derivatives of state, not state itself. For example, let's say we are using the
motor's encoders to estimate state. The encoder values are discrete, not
continuous, and the sensor can have noise, so integrating the signal will yield
inaccuracy. This error builds as time passes, until the estimate of robot
position is completley off from actual position. Dead Reckoning may be
appropriate if the sources of error and time spent useing it are sufficiently
small, which may very well be true in a 30 second autonomous period.

This tutorial's contents are coming soon.
