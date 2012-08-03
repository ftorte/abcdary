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

	// ===========================================================
	// Constructor
	// ===========================================================


	@SuppressWarnings("static-access")
	public SceneDescriptor() {
		scObjects=new ArrayList<SpriteDescriptor>();
	}


	@Override
	public String toString() {
		return "SpriteDescriptor" +  "]";
	}

}
