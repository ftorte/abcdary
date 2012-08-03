package com.welmo.educational.scenes.description;

import com.welmo.educational.scenes.description.SpriteDescriptor.ActionType;
import com.welmo.educational.scenes.description.SpriteDescriptor.SpritesEvents;

public class Action{
	public SpritesEvents event = SpritesEvents.NO_EVENTS; 
	public ActionType type =ActionType.NO_ACTION;
	public String NextScene="";
}