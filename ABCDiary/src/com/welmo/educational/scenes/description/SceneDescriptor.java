package com.welmo.educational.scenes.description;

import java.util.ArrayList;


public class SceneDescriptor extends ResourceDescriptor {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Member Variables
	// ===========================================================
	public ArrayList<SpriteDescriptor>	scObjects=null;
	public ArrayList<TextDescriptor>	scText=null;
	// ===========================================================
	// Constructor
	// ===========================================================


	@SuppressWarnings("static-access")
	public SceneDescriptor() {
		scObjects=new ArrayList<SpriteDescriptor>();
		scText=new ArrayList<TextDescriptor>();
	}


	@Override
	public String toString() {
		return "SpriteDescriptor" +  "]";
	}

}
