package com.welmo.educational.scenes.components.descriptors;

import java.util.ArrayList;
import java.util.List;

public class SpriteObjectDescriptor extends BasicObjectDescriptor{
	// enumerators to manage object types & object events
	public enum SpritesTypes {
	    NO_TYPE, STATIC, CLICKABLE, COMPOUND_SPRITE, ANIMATED
	}
	
	protected SpritesTypes type;
	protected String textureName;
	protected List<TextObjectDescriptor> textElements;
	protected List<SpriteObjectDescriptor> coumpoundElements;

	
	SpriteObjectDescriptor(){
		super();
		//coumpoundElements = new ArrayList<SpriteDescriptor>();
		textElements = new ArrayList<TextObjectDescriptor>();
		coumpoundElements = new ArrayList<SpriteObjectDescriptor>();
	}
	
	public SpritesTypes getType() {
		return type;
	}
	public void setType(SpritesTypes type) {
		this.type = type;
	}
	public String getTextureName() {
		return textureName;
	}
	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}
	public List<TextObjectDescriptor> getTextElements() {
		return textElements;
	}
	public List<SpriteObjectDescriptor> getCoumpoundElements() {
		return coumpoundElements;
	}

}
