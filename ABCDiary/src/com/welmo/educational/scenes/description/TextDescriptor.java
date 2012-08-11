package com.welmo.educational.scenes.description;

public class TextDescriptor extends ResourceDescriptor{
	// enumerators to manage object types & object events
	public enum TextTypes {
		NO_TYPE, SIMPLE
	}
	public TextTypes type = TextTypes.NO_TYPE;
	public String resourceName;
	public String message;

}
