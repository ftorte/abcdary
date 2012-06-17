package com.welmo.educational.managers;



import java.util.HashMap;

import com.welmo.educational.utility.ParserXMLSceneDescriptor;
import com.welmo.educational.utility.SceneDescriptor;
import com.welmo.educational.utility.TextureDescriptor;
import com.welmo.educational.utility.TextureRegionDescriptor;


public class ResourceDescriptorsManager {
		
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	// Resources
	protected HashMap<String,TextureDescriptor> mTextureDscMap;
	protected HashMap<String,TextureRegionDescriptor> mTextureRegionDscMap;
	
	//Scene
	protected HashMap<String,SceneDescriptor> mSceneDscMap;
	
	
	private static ResourceDescriptorsManager 	mInstance=null;
	
	//--------------------------------------------------------
		// Constructors
		//--------------------------------------------------------
	private ResourceDescriptorsManager(){
		mTextureDscMap = new HashMap<String,TextureDescriptor>();
		mTextureRegionDscMap = new HashMap<String,TextureRegionDescriptor>();
		mSceneDscMap = new HashMap<String,SceneDescriptor>();
	}
	@method
	public static ResourceDescriptorsManager getInstance(){
		if(mInstance == null)
			mInstance = new  ResourceDescriptorsManager();
		return mInstance;
	}
	
	//--------------------------------------------------------
	// Methods add/get managed objects
	//--------------------------------------------------------
	@method
	//Add a texture description to the texture descriptions list
	public void addTexture(String name, TextureDescriptor texture){
		if (mTextureDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		mTextureDscMap.put(name,texture);
	}
	
	@method
	//Add the description of a texture region to the texture region descriptions list
	public void addTextureRegion(String name, TextureRegionDescriptor textureRegion){
		if (mTextureRegionDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		mTextureRegionDscMap.put(name,textureRegion);
	}
	@method
	//Add the description of a scene descriptions list
	public void addScene(String name, SceneDescriptor scene){
		if (mSceneDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		mSceneDscMap.put(name,scene);
	}
	
	@method
	public TextureDescriptor getTexture(String name){
		if (mTextureRegionDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return mTextureDscMap.get(name);
	}
	
	@method
	public TextureRegionDescriptor getTextureRegion(String name){
		if (mTextureRegionDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return mTextureRegionDscMap.get(name);
	}
	@method
	//Add the description of a scene descriptions list
	public SceneDescriptor getScene(String name){
		if (mSceneDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return mSceneDscMap.get(name);
	}
}
