package com.welmo.educational.scenes.description;


public class SceneObjectDescriptor extends ResourceDescriptor {
	public enum SceneObjectTypes {
	    SPRITE, CLICKABLE_SPRITE, FONT 
	}
	public SceneObjectTypes type;
	public String resourceName;
}
