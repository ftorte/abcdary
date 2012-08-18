package com.welmo.educational.scenes.components.descriptors;

import java.util.ArrayList;

import com.welmo.educational.resources.components.descriptors.ResourceDescriptor;


public class SceneDescriptor extends BasicObjectDescriptor {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Member Variables
	// ===========================================================
	public ArrayList<SpriteObjectDescriptor>	scObjects=null;
	public ArrayList<TextObjectDescriptor>		scText=null;
	public BackGroundObjectDescriptor			thebackGround=null;
	String name="";
	// ===========================================================
	// Constructor
	// ===========================================================


	@SuppressWarnings("static-access")
	public SceneDescriptor() {
		scObjects=new ArrayList<SpriteObjectDescriptor>();
		scText=new ArrayList<TextObjectDescriptor>();
		thebackGround = new BackGroundObjectDescriptor();
	}


	@Override
	public String toString() {
		return "SpriteDescriptor" +  "]";
	}

}
