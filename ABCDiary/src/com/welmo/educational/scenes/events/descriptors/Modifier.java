package com.welmo.educational.scenes.events.descriptors;


//inner class to manage modifiers and actions
public class Modifier{
	public enum ModifierType {
	    NO_MODIFIER, MOVE, SCALE
	}
	
	//[FT] public SpritesEvents event = SpritesEvents.NO_EVENTS;
	public ModifierType type = ModifierType.NO_MODIFIER;
	public float fScaleFactor = 1;
	public float fMoveFactor = 1; 
	public int stick_with=0;

}