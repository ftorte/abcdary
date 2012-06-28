package com.welmo.educational.scenes.description;

import java.util.ArrayList;

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
