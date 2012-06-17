package com.welmo.educational.utility;

import java.util.ArrayList;

public class TextureDescriptor extends ResourceDescriptor {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Member Variables
	// ===========================================================
	public boolean 								valid=false;
	public ArrayList<TextureRegionDescriptor>	Regions=null;
	
	// ===========================================================
	// Constructor
	// ===========================================================

	
	@SuppressWarnings("static-access")
	public TextureDescriptor() {
		Regions=new ArrayList<TextureRegionDescriptor>();
	}


	@Override
	public String toString() {
		return "ResourceDescriptor" +  "]";
	}

}
