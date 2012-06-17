package com.welmo.educational.managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

import com.welmo.educational.scenes.ManageableScene;


import android.content.Context;
import android.graphics.Color;

public class SceneManager<T extends ManageableScene> {

	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug & trace
	private static final String TAG = "SceneManager";
	//private static final boolean TRACE = true; 
	
	// ===========================================================
	// Variables
	// ===========================================================
	private Engine 				mEngine=null;
	private Context 			mContext=null;
	
	HashMap<String, T> mapScenes = new HashMap<String, T>();
	private final Class clss;
	 
	//final Scene scene = new Scene();
	// ===========================================================
	public SceneManager(Class<T> clss) {  
		this.clss = clss;   
	}  
	

	public void init(Engine engine, Context ctx)
	{
		mEngine = engine;		
		mContext = ctx;
	}
	
	//crete scene and add to the map with name strSceneName
	public void BuildScenes(String strSceneDescription, String strSceneName, ResourcesManager resResourcesManager){
		if((mEngine == null) | (mContext == null)){ 
			throw new NullPointerException("Scene Manager not initialized: mEngine or mContext are null"); 
		}
		T scene = null;
		try {
			scene = (T) clss.newInstance();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scene.init(mEngine, mContext);
		//scene.loadScene(strSceneName,resResourcesManager);
		scene.loadScene2(strSceneName,resResourcesManager);
		mapScenes.put(strSceneName, scene);
		
	}
	
	// Get the scene strSceneName
	public T getScene(String strSceneName){
		return mapScenes.get(strSceneName);
	}

	// Get the scene strSceneName
	public void addScene(String strSceneName, T theScene){
		mapScenes.put(strSceneName, theScene);
	}
}