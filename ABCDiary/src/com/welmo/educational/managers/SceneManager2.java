package com.welmo.educational.managers;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.welmo.educational.scenes.IManageableScene;
import com.welmo.educational.scenes.ManageableScene;
import com.welmo.educational.scenes.SceneMenuArray;


import android.content.Context;
import android.graphics.Color;

public class SceneManager2 {

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
		
	HashMap<String, IManageableScene> mapScenes =null;
	
	// singleton Instance
	private static SceneManager2 	mInstance=null;
	boolean initialized = false;


	//final Scene scene = new Scene();
	// ===========================================================
	private SceneManager2() {  
		mapScenes = new HashMap<String, IManageableScene>();
	}  
	@method
	public static SceneManager2 getInstance(){
		if(mInstance == null)
			mInstance = new  SceneManager2();
		return mInstance;
	}

	// ===========================================================

	public void init(Context ctx, Engine eng){
		if(!initialized){
			mEngine = eng;
			mContext = ctx;
			initialized = true;
		}
		else
			//if initialization called for already initialized manager but with different context and engine throw an exception
			if(mContext != ctx || mEngine!= eng) //if initialization called on another context
				throw new IllegalArgumentException("ResourceManager, Init called two times with different context");
	}
		
	
	//crete scene and add to the map with name strSceneName
	public void BuildScenes(String strSceneName){
		if((mEngine == null) | (mContext == null)){ 
			throw new NullPointerException("Scene Manager not initialized: mEngine &/or mContext are null"); 
		}
		ManageableScene scene = null;
		scene = new ManageableScene();
		scene.init(mEngine, mContext);
		scene.loadScene(strSceneName);
		mapScenes.put(strSceneName, scene);
		
	}
	
	// Get the scene strSceneName
	public IManageableScene getScene(String strSceneName){
		return mapScenes.get(strSceneName);
	}

	// Get the scene strSceneName
	public void addScene(String strSceneName, ManageableScene theScene){
		mapScenes.put(strSceneName, theScene);
	}
}