Getting Started
===============

This page is intended to quickly go from nothing to a manual gamepad-controlled robot.


FTC Guides
----------

The following guides will walk you through installing all required software,
and setting up all required hardware. By the end of this section you should
have a gamepad-controlled robot and understand the ecosystem.

`FTC Next Gen Guide <https://github.com/ftctechnh/ftc_app/raw/master/doc/tutorial/FTC_NextGenGuide.pdf>`__
  This guide will explain the hardware that is available and how everything
  interacts.

`FTC Training Manual <https://github.com/ftctechnh/ftc_app/raw/master/doc/tutorial/FTCTraining_Manual.pdf>`__
  This guide will walk you through installing Android Studio (used to program
  the robot app), installing the Driver Control Station (app run on separate
  phone to connect gamepads), and running a sample robot program. Note Android
  Studio may already have been installed by your teacher, in which case the
  installation steps can be skipped.

`FTC Game & Season Info <http://www.firstinspires.org/resource-library/ftc/game-and-season-info>`__
  This page contains all the rules and regulations regarding robot construction
  and operation.

`FTC Technology Information & Resources <http://www.firstinspires.org/resource-library/ftc/technology-information-and-resources>`__
  This page contains all software related documentation.

`FTC Robot Building Resources <http://www.firstinspires.org/resource-library/ftc/robot-building-resources>`__
  This page contains all the hardware related documentation.


Git & Github
------------

This section describes Git and Github. By the end of this section you should
know how to use Git and Github to download the base robot code, to make local
changes, to commit those changes so you can go back to previous versions, and
to push those changes to the remote server.

`Version Control Systems (VCS) <https://en.wikipedia.org/wiki/Version_control>`__
    Professional software organizations use VCS to organize teams of Software
    Engineers working on a common project. It provides a means to backup the
    software remotely, look at the changes to code over time, revert back to a
    previous state, etc.

`Git <https://en.wikipedia.org/wiki/Git>`__
    Git is a VCS that many companies and open source projects have decided to
    use. It is used by the `FIRST FTC Competition
    <https://github.com/ftctechnh/ftc_app>`__ and by `pmtischler/ftc_app
    <https://github.com/pmtischler/ftc_app>`__ to release the base code for the
    robot.

`Github <https://github.com/>`__
    Github is a company which hosts Git projects.  It is the most popular
    choice for open source projects, and is used both by the competition and
    this page. In addition to a remote server for the Git repository (repo), it
    adds web-based systems for access-control, code reviews, Wiki pages, etc.


Learn Git
~~~~~~~~~

The following can be used to learn Git and Github.

`Github Hello World <https://guides.github.com/activities/hello-world/>`__
  This page will walk you through creating a Git repo hosted by Github,
  creating branches, changing and committing files, and performing a Pull
  Request (code review).
`Github Desktop App <https://desktop.github.com/>`__
  This app simplifies working with Git repositories hosted by Github. Download
  and install it. Note this may already be installed by your teacher.

`Hello World with Desktop App <https://help.github.com/desktop/guides/contributing/>`__
  The Github Hello World tutorial walks you through the process using their
  web interface. Typically you do things using local command-line tools, or
  with the Desktop App. After completing the tutorial through the web
  interface, try editing files locally, commiting the changes with the Desktop
  App, and pushing the changes with the Desktop App instead. When it comes time
  to develop the robot's code, you'll use Android Studio to make changes, and
  the Github Desktop App to save changes in the cloud.

`Git Book <https://git-scm.com/doc>`__
  This page contains a book on all things Git. It is important to understand
  how Git works so you don't accidentally delete your code, and so that you can
  use the Git system to develop smarter and faster. Read through this book. You
  don't need to implement the things it describes.



Fork pmtischler/ftc_app
~~~~~~~~~~~~~~~~~~~~~~~

The `pmtischler/ftc_app <https://github.com/pmtischler/ftc_app>`__ is a Git
repo hosted on Github. The repo is derived from the `FIRST FTC
repo <https://github.com/ftctechnh/ftc_app>`__, and builds on top of it
to add additional robot features to improve the development process for teams.

Teams should `fork this repo
<https://help.github.com/articles/fork-a-repo/>`__, or create a new repo which
is initialized with this original repo. The team can then `configure a remote
upstream <https://help.github.com/articles/configuring-a-remote-for-a-fork/>`__
so that teams can `synchronize the fork
<https://help.github.com/articles/syncing-a-fork/>`__ to pull changes from the
original repo into the fork repo. Note that this process may already have been
done by your teacher.


Team Repo
~~~~~~~~~

**Clone Team Repo**. Once the team has a personal repo that will contain the
code the team develops, and is initialized with the base robot code, the team
can ``clone`` the repo locally, which will copy it to your machine. This can be
done with the Github Desktop App.

**Import Project**. The next step is to open the Android Project in the repo to
start making code changes. Open Android Studio, select ``Import Project
(Gradle)``, and select the folder for the repo. This will load the project
using the ``Gradle (*.gradle)`` files in the repository which have been set up
for you.

**Edit a File**. In Android Studio, use the ``Project`` pane to open the file
``TeamCode/java/org.firstinspires.ftc.teamcode``. Edit the first line of this
file to add your team name:

.. code-block:: markdown

    ## TeamCode Module (YOUR TEAM NAME)

**Commit & Push**. Save the file you edited. Then use the Github Desktop App to
commit the file and push the changes to the server. Once you've done it
correctly, you should be able to see the changes at
``https://github.com/USERNAME/REPONAME/blob/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/readme.md``
(replace ``USERNAME`` and ``REPONAME`` based on how you forked).

You've successfully used your team repo to store changes to your code. If you
were to switch computers (e.g. original machine broke, you use multiple
computers, you have multiple team members), you could repeat the clone process
to get your code back. You can ``pull`` from the repo to get any ``push`` made
from another computer.


Robot Program
-------------

Let's create the first Robot program which simply makes a motor run at full
speed.

**Add a Robot Class**. In Android Studio, open the ``Project`` pane, right
click on ``TeamCode/java/org.firstinspires.ftc.teamcode``, and click ``New >
Java Class``. Set the name field to ``FullPower`` and the superclass to
``com.qualcomm.robotcore.eventloop.opmode.OpMode``. This will add the file and
open it in the editor.

**Add Imports**. Under the ``package`` line, add the following imports. This
will allow you to use the code contained in those files.

.. code-block:: java

	import com.qualcomm.robotcore.eventloop.opmode.OpMode;
	import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
	import com.qualcomm.robotcore.hardware.DcMotor;

**Register the Robot Class**. The base code uses annotations to register the
class as an OpMode, which will allow it to be selected in the robot's UI. Add
the annotation so that the class declaration looks like this:

.. code-block:: java

    @TeleOp(name="FullPower", group="FullPower")
    public class FullPower extends OpMode {
        // ...
    }

**Add Motor Variables**. We need a motor variable in order to refer to it when
we set the power. Add the following member variable to the class.

.. code-block:: java

    public DcMotor motor = null;

**Initialize the Robot**. The next step is to initialize the robot and set the
robot's motor variable. The ``init`` function is called when the robot is
started. Add the following function to the class.

.. code-block:: java

    public void init() {
        motor = hardwareMap.dcMotor.get("motor");
    }

**Set the Motor Power**. The next step is to set the motor power. The ``loop``
function is called repeatedly until the robot is stopped (e.g. match is over).
Add tthe following function to the class.

.. code-block:: java

    public void loop() {
        motor.setPower(1.0);
    }

**Configure Robot, Run Program**. The final step is to run the program on the
phone, create a robot configuration with a single motor named ``motor``, and
start the ``FullPower OpMode``. If you have connected everything and programmed
correctly, you should see the motor spin at full power. The following is the
final code you should have.

.. code-block:: java

	package org.firstinspires.ftc.teamcode;

	import com.qualcomm.robotcore.eventloop.opmode.OpMode;
	import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
	import com.qualcomm.robotcore.hardware.DcMotor;

	@TeleOp(name="FullPower", group="FullPower")
	public class FullPower extends OpMode {
		public DcMotor motor = null;

		public void init() {
			motor = hardwareMap.dcMotor.get("motor");
		}

		public void loop() {
			motor.setPower(1.0);
		}
	}

You now have an end-to-end example of programming a robot. Save everything,
commit it to Git, and push it so it's in the cloud. From here you can continue
to more advanced things in the :doc:`tutorials`.
