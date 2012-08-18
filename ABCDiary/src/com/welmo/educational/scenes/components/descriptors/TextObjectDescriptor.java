package com.welmo.educational.scenes.components.descriptors;

public class TextObjectDescriptor extends BasicObjectDescriptor{
	// enumerators to manage object types & object events
	public enum TextTypes {
		NO_TYPE, SIMPLE
	}
	protected String colorName;
	protected TextTypes type = TextTypes.NO_TYPE;;
	protected String message;
	protected String FontName;
	
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public TextTypes getType() {
		return type;
	}
	public void setType(TextTypes type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFontName() {
		return FontName;
	}
	public void setFontName(String fontName) {
		FontName = fontName;
	}
	public TextObjectDescriptor() {
		super();
		this.colorName=new String("");
		this.message=new String("");
		this.FontName=new String("");
	}
	

}
