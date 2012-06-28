package com.welmo.educational.managers;



import java.util.HashMap;

import com.welmo.educational.scenes.description.ColorDescriptor;
import com.welmo.educational.scenes.description.MultiViewSceneDescriptor;
import com.welmo.educational.scenes.description.ParserXMLSceneDescriptor;
import com.welmo.educational.scenes.description.SceneDescriptor;
import com.welmo.educational.scenes.description.TextureDescriptor;
import com.welmo.educational.scenes.description.TextureRegionDescriptor;


public class ResourceDescriptorsManager {
		
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	// Resources
	protected HashMap<String,TextureDescriptor> 		hmTextureDscMap;
	protected HashMap<String,TextureRegionDescriptor> 	hmTextureRegionDscMap;
	protected HashMap<String,ColorDescriptor> 			hmColorDscMap;
	
	//Scene
	protected HashMap<String,SceneDescriptor> 			hmSceneDscMap;
	protected HashMap<String,MultiViewSceneDescriptor> 	hmMVSceneDscMap;
	
	private static ResourceDescriptorsManager 	mInstance=null;
	
	//--------------------------------------------------------
		// Constructors
		//--------------------------------------------------------
	private ResourceDescriptorsManager(){
		hmTextureDscMap = new HashMap<String,TextureDescriptor>();
		hmTextureRegionDscMap = new HashMap<String,TextureRegionDescriptor>();
		hmSceneDscMap = new HashMap<String,SceneDescriptor>();
		hmMVSceneDscMap = new HashMap<String,MultiViewSceneDescriptor>(); 
		hmColorDscMap = new HashMap<String,ColorDescriptor>();
	}
	@method
	public static ResourceDescriptorsManager getInstance(){
		if(mInstance == null)
			mInstance = new  ResourceDescriptorsManager();
		return mInstance;
	}
	
	//--------------------------------------------------------
	// Methods to add/get descriptors
	//--------------------------------------------------------
	//--------------------------------------------------------
	// TEXTURE
	//--------------------------------------------------------
	@method
	//Add a texture description to the texture descriptions list
	public void addTexture(String name, TextureDescriptor texture){
		if (hmTextureDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		hmTextureDscMap.put(name,texture);
	}
	@method
	public TextureDescriptor getTexture(String name){
		if (hmTextureRegionDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return hmTextureDscMap.get(name);
	}

	//--------------------------------------------------------
	// TEXTUREREGION
	//--------------------------------------------------------
	@method
	//Add the description of a texture region to the texture region descriptions list
	public void addTextureRegion(String name, TextureRegionDescriptor textureRegion){
		if (hmTextureRegionDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		hmTextureRegionDscMap.put(name,textureRegion);
	}
	@method
	public TextureRegionDescriptor getTextureRegion(String name){
		if (hmTextureRegionDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return hmTextureRegionDscMap.get(name);
	}
	//--------------------------------------------------------
	// SCENE
	//--------------------------------------------------------
	@method
	//Add the description of a scene descriptions list
	public void addScene(String name, SceneDescriptor scene){
		if (hmSceneDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		hmSceneDscMap.put(name,scene);
	}
	@method
	//Add the description of a scene descriptions list
	public SceneDescriptor getScene(String name){
		if (hmSceneDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return hmSceneDscMap.get(name);
	}
	//--------------------------------------------------------
		// MULTIVEIWSCENE
		//--------------------------------------------------------
	@method
	//Add the description of a scene descriptions list
	public void addMVScene(String name, MultiViewSceneDescriptor scene){
		if (hmSceneDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		hmMVSceneDscMap.put(name,scene);
	}
	@method
	//Add the description of a scene descriptions list
	public MultiViewSceneDescriptor getMVScene(String name){
		if (hmSceneDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return hmMVSceneDscMap.get(name);
	}
	
	@method
	//Add the description of a scene descriptions list
	public void addColor(String name, ColorDescriptor color){
		if (hmColorDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		hmColorDscMap.put(name,color);
	}
	@method
	//Add the description of a scene descriptions list
	public ColorDescriptor getColor(String name){
		if (hmColorDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return hmColorDscMap.get(name);
	}
}
