package com.welmo.educational.scenes.description.Events;

import com.welmo.educational.scenes.components.Stick;
import com.welmo.educational.scenes.components.Stick.StickMode;

public class Action{
	public enum ActionType {
		NO_ACTION, CHANGE_SCENE,STICK
	}
	public enum ActionMode {
		NO_MODE, STICK_MERGE, 
	}
	
	//[FT] public SpritesEvents event = SpritesEvents.NO_EVENTS; 
	public ActionType type =ActionType.NO_ACTION;
	public String NextScene="";
	public int stick_with=0;
	public StickMode stickMode = Stick.StickMode.NO_STICK;
}