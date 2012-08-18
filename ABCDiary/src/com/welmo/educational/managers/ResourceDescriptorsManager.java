package com.welmo.educational.managers;



import java.util.HashMap;

import com.welmo.educational.resources.components.descriptors.ColorDescriptor;
import com.welmo.educational.resources.components.descriptors.FontDescriptor;
import com.welmo.educational.resources.components.descriptors.TextureDescriptor;
import com.welmo.educational.resources.components.descriptors.TextureRegionDescriptor;
import com.welmo.educational.scenes.components.descriptors.MultiViewSceneDescriptor;
import com.welmo.educational.scenes.components.descriptors.ParserXMLSceneDescriptor;
import com.welmo.educational.scenes.components.descriptors.SceneDescriptor;


public class ResourceDescriptorsManager {
		
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	// Resources
	protected HashMap<String,TextureDescriptor> 		hmTextureDscMap;
	protected HashMap<String,TextureRegionDescriptor> 	hmTextureRegionDscMap;
	protected HashMap<String,ColorDescriptor> 			hmColorDscMap;
	protected HashMap<String,FontDescriptor> 			hmFontDscMap;
	
	// singleton Instance
	private static ResourceDescriptorsManager 	mInstance=null;
	
	//--------------------------------------------------------
		// Constructors
		//--------------------------------------------------------
	private ResourceDescriptorsManager(){
		hmTextureDscMap = new HashMap<String,TextureDescriptor>();
		hmTextureRegionDscMap = new HashMap<String,TextureRegionDescriptor>();
		hmColorDscMap = new HashMap<String,ColorDescriptor>();
		hmFontDscMap = new HashMap<String,FontDescriptor>();
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
	// COLOR
	//--------------------------------------------------------
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
	//--------------------------------------------------------
	// FONT
	//--------------------------------------------------------
	@method
	//Add the description of a scene descriptions list
	public void addFont(String name, FontDescriptor font){
		if (hmFontDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		hmFontDscMap.put(name,font);
	}
	@method
	//Add the description of a scene descriptions list
	public FontDescriptor getFont(String name){
		if (hmFontDscMap == null)
			throw new NullPointerException("ResurceDescriptorsManager not initialized correctly"); 
		return hmFontDscMap.get(name);
	}
}
