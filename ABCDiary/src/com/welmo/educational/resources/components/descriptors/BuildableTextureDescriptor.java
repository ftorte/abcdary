package com.welmo.educational.resources.components.descriptors;

import java.util.ArrayList;

public class BuildableTextureDescriptor extends ResourceDescriptor {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Member Variables
	// ===========================================================
	public boolean 									valid=false;
	public ArrayList<TiledTextureRegionDescriptor>	Regions=null;

	// ===========================================================
	// Constructor
	// ===========================================================


	@SuppressWarnings("static-access")
	public BuildableTextureDescriptor() {
		Regions=new ArrayList<TiledTextureRegionDescriptor>();
	}


	@Override
	public String toString() {
		return "ResourceDescriptor" +  "]";
	}

}

