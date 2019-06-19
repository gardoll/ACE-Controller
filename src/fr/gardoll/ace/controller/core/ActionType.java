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
  PAUSE, WAIT_PAUSE,
  RESUME, RESUME_END,
  CANCEL, WAIT_CANCEL, CANCEL_DONE,
  REINIT, REINIT_DONE,
  CLOSING;
}
