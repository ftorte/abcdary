package com.welmo.educational.scenes.description;

import com.welmo.educational.scenes.description.SpriteDescriptor.ModifierType;
import com.welmo.educational.scenes.description.SpriteDescriptor.SpritesEvents;

//inner class to manage modifiers and actions
public class Modifier{
	public SpritesEvents event = SpritesEvents.NO_EVENTS; ; 
	public ModifierType type = ModifierType.NO_MODIFIER;
	public float fScaleFactor = 1;
	public float fMoveFactor = 1; 
}