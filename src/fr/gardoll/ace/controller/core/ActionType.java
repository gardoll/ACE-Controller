package fr.gardoll.ace.controller.core;

public enum ActionType
{
  ARM_MOVING,
  CAROUSEL_MOVING,
  CAROUSEL_RELATIVE_MOVING,
  ARM_END_MOVING,
  CAROUSEL_END_MOVING,
  WITHDRAWING,
  INFUSING,
  PAUSING, WAIT_PAUSE, PAUSE_DONE,
  RESUME, RESUME_DONE,
  CANCELING, WAIT_CANCEL, CANCEL_DONE,
  REINIT, REINIT_DONE,
  OPEN_VALVE, CLOSE_VALVES, CLOSE_ALL_VALVES,
  CLOSING, USR_MSG, DRAIN_PUMP,
  SEQUENCE_START, SEQUENCE_AWAIT, SEQUENCE_AWAIT_DONE, SEQUENCE_DONE,
  H2O_RINCE, REFILL_PUMP, RINCE, SEQUENCE_PAUSE_START, SESSION_START, SESSION_DONE,
  INDEX_ARM, CAROUSEL_TO_TRASH, COLUMN_DIST_START, COLUMN_DIST_DONE,
  NEXT_SEQUENCE_PREP,PREPARING, SEQUENCE_PAUSE_DONE, CAROUSEL_TURN_RIGHT,
  CAROUSEL_TURN_LEFT, POST_LAST_SEQ, POST_SESSION, TEST;
}
