package com.welmo.educational.scenes.description;

import java.util.ArrayList;


public class SpriteDescriptor extends ResourceDescriptor {
	// enumerators to manage object types & object events
	public enum SpritesTypes {
	    NO_TYPE, STATIC, CLICKABLE
	}
	public enum SpritesEvents {
	    NO_EVENTS, ON_CLICK, ON_MOVE
	}
	public enum ActionType {
		NO_ACTION, CHANGE_SCENE,
	}
	public enum ModifierType {
	    NO_MODIFIER, MOVE, SCALE
	}
	
	public SpritesTypes type;
	public String nextScene;
	public String resourceName;
	
	public ArrayList<Modifier> modifiers;
	public ArrayList<Action> actions;
}
