package com.welmo.educational.utility;

import java.util.ArrayList;

public class SceneDescriptor extends ResourceDescriptor {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Member Variables
	// ===========================================================
	public ArrayList<SceneObjectDescriptor>	scObjects=null;

	// ===========================================================
	// Constructor
	// ===========================================================


	@SuppressWarnings("static-access")
	public SceneDescriptor() {
		scObjects=new ArrayList<SceneObjectDescriptor>();
	}


	@Override
	public String toString() {
		return "ResourceDescriptor" +  "]";
	}

}
