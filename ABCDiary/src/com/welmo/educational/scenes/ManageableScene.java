package com.welmo.educational.scenes;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import com.welmo.educational.managers.ResourcesManager;

import android.content.Context;

abstract  public class ManageableScene extends Scene {
	public void loadResources(){};
	public void loadScene(String Scenedescriptor, ResourcesManager res){};
	public void loadScene2(String Scenedescriptor, ResourcesManager res){};
	public void init( Engine theEngine,Context ctx){};
	
	protected Context mApplication = null;
	ManageableScene(){
		
	}
	public void loadScene(String SceneName) {
		// TODO Auto-generated method stub
	}
	
}
