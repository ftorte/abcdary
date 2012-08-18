package com.welmo.educational.scenes.components.descriptors;

import java.util.ArrayList;

import com.welmo.educational.resources.components.descriptors.ResourceDescriptor;

public class MultiViewSceneDescriptor extends ResourceDescriptor{
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Member Variables
		// ===========================================================
		public ArrayList<SceneDescriptor>	scScenes=null;

		// ===========================================================
		// Constructor
		// ===========================================================


		@SuppressWarnings("static-access")
		public MultiViewSceneDescriptor() {
			scScenes=new ArrayList<SceneDescriptor>();
		}


		@Override
		public String toString() {
			return "ResourceDescriptor" +  "]";
		}
}
