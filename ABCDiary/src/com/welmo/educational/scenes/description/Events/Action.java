package com.welmo.educational.scenes.description.Events;

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
	public ActionMode stickMode = ActionMode.NO_MODE;
}