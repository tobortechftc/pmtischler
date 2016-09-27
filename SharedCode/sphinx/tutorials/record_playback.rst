Record & Playback
=================

In this tutorial you will write your first simple autonomous program. This
program will record the hardware state during each call to ``loop`` while the
robot is in manual mode. When the program is in autonomous mode, it'll play
back that recorded hardware log to mimic the behavior during manual operation.

Note this is not a robust autonomous method. It doesn't handle any changes in
robot or environment. If the robot changes weight, changes air drag, etc the
exact motor powers needed to turn will change, so the manual log previously
recorded will not have the same affect. If the environment changes, the robot
will not be able to adapt to avoid obstacles, deal with slippery conditions,
etc. In future more advanced tutorials we'll learn how to make better autonomy
programs.


This tutorial's contents are coming soon.
