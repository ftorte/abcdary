package com.welmo.educational.managers;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.welmo.educational.scenes.description.ColorDescriptor;
import com.welmo.educational.scenes.description.FontDescriptor;
import com.welmo.educational.scenes.description.ParserXMLSceneDescriptor;
import com.welmo.educational.scenes.description.TextureDescriptor;
import com.welmo.educational.scenes.description.TextureRegionDescriptor;
import com.welmo.educational.scenes.description.XMLTags;

import android.content.Context;
import android.graphics.Color;

public class ResourcesManager {
	// ===========================================================
	// Constants
	// ===========================================================
	final String FONTHBASEPATH = "font/";
	final String TEXTUREBASEPATH = "gfx/";

	// ===========================================================
	// Variables
	// ===========================================================
	Context mCtx;
	boolean initialized = false;

	HashMap<String, Font> 					mapFonts;
	HashMap<String, ITextureRegion> 		mapTextureRegions;
	HashMap<String, BitmapTextureAtlas> 	mapBitmapTexturesAtlas;

	
	// singleton Instance
	private static ResourcesManager 	mInstance=null;

	//--------------------------------------------------------
	// Constructors
	//--------------------------------------------------------
	private ResourcesManager(){
		mapFonts = new HashMap<String, Font>();
		mapTextureRegions = new HashMap<String, ITextureRegion>();
		mapBitmapTexturesAtlas = new HashMap<String, BitmapTextureAtlas>();
	}
	@method
	public static ResourcesManager getInstance(){
		if(mInstance == null)
			mInstance = new  ResourcesManager();
		return mInstance;
	}
		
	// ===========================================================

	public void init(Context ctx){
		if(!initialized){
			// init resource manager with font & texture base path
			FontFactory.setAssetBasePath(FONTHBASEPATH);
			SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath(TEXTUREBASEPATH);
			//mCtx 	= ctx;
			mCtx 	= ctx.getApplicationContext();
		}
	}
	
	public void EngineLoadResources(Engine theEndgine){
		TextureManager textureManager = theEndgine.getTextureManager();
		for(String key:mapBitmapTexturesAtlas.keySet())
			textureManager.loadTexture(mapBitmapTexturesAtlas.get(key));
	}

	public ITexture LoadTexture(String textureName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		TextureDescriptor pTextRegDsc = pResDscMng.getTexture(textureName);

		//check if texture already exists
		if(mapBitmapTexturesAtlas.get(pTextRegDsc.Name)!=null)
			throw new IllegalArgumentException("In LoadTexture: Tentative to load a texture already loaded");

		//Create the texture
		BitmapTextureAtlas pTextureAtlas = new BitmapTextureAtlas(pTextRegDsc.Parameters[XMLTags.WIDTH_IDX], pTextRegDsc.Parameters[XMLTags.HEIGHT_IDX], TextureOptions.BILINEAR);
		mapBitmapTexturesAtlas.put(pTextRegDsc.Name,pTextureAtlas);

		//iterate to all textureregion define in the texture
		for (TextureRegionDescriptor pTRDsc:pTextRegDsc.Regions){	
			
			if(mapBitmapTexturesAtlas.get(pTRDsc.Name)!=null) // check that texture region is new
				throw new IllegalArgumentException("In LoadTexture: Tentative to create a texture region that already exists ");

			//Create texture region
			mapTextureRegions.put(pTRDsc.Name, SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, 
					this.mCtx, pTRDsc.filename, pTRDsc.Parameters[XMLTags.WIDTH_IDX], pTRDsc.Parameters[XMLTags.HEIGHT_IDX], 
					pTRDsc.Parameters[XMLTags.POSITION_X_IDX], pTRDsc.Parameters[XMLTags.POSITION_Y_IDX]));
		}
				
		return pTextureAtlas;
	}
	

	public ITextureRegion LoadTextureRegion(String textureRegionName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		TextureRegionDescriptor pTextRegDsc = pResDscMng.getTextureRegion(textureRegionName);
		if(pTextRegDsc == null)
			throw new IllegalArgumentException("In LoadTextureRegion: there is no description for the requested texture");
		
		//To load a texture region the manager load the texture and all child regions
		LoadTexture(pTextRegDsc.textureName);
		
		//return the texture region that has just been loaded
		return mapTextureRegions.get(textureRegionName);
	}
	
	public ITextureRegion GetTextureRegion(String textureRegionName){
		ITextureRegion theTexture = mapTextureRegions.get(textureRegionName);
		//if the texture region is not already loaded in the resource manager load it
		if(theTexture==null) 
			theTexture = LoadTextureRegion(textureRegionName);
		//return the found or loaded texture region
		return theTexture;
	}
	
}
