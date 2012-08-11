package com.welmo.educational.scenes.description;

import java.util.ArrayList;
import java.util.List;

public class SpriteDescriptor extends ResourceDescriptor {
	// enumerators to manage object types & object events
	public enum SpritesTypes {
	    NO_TYPE, STATIC, CLICKABLE, COMPOUND_SPRITE
	}
	
	public SpritesTypes type;
	public String resourceName;
	public List<SpriteDescriptor> coumpoundElements;
	public List<TextDescriptor> textElements;
	SpriteDescriptor(){
		coumpoundElements = new ArrayList<SpriteDescriptor>();
		textElements = new ArrayList<TextDescriptor>();
	}
}
