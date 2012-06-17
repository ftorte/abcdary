package com.welmo.educational.utility;

public class SceneObjectDescriptor extends ResourceDescriptor {
	public enum SceneObjectTypes {
	    SPRITE, CLICKABLE_SPRITE 
	}
	public SceneObjectTypes type;
	public String resourceName;
}
