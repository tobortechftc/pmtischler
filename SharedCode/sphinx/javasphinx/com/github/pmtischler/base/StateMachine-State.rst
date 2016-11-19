StateMachine.State
==================

.. java:package:: com.github.pmtischler.base
   :noindex:

.. java:type:: public static interface State
   :outertype: StateMachine

   A state in the state machine.

Methods
-------
start
^^^^^

.. java:method:: public void start()
   :outertype: StateMachine.State

   Called when the state first becomes the active state.

update
^^^^^^

.. java:method:: public State update()
   :outertype: StateMachine.State

   Called on each update.

   :return: The next state to run.

