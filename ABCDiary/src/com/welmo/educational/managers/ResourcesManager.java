package com.welmo.educational.managers;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.font.IFont;
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
import com.welmo.educational.scenes.description.tags.ResTags;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

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
	Engine mEngine;
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

	public void init(Context ctx, Engine eng){
		if(!initialized){
			// init resource manager with font & texture base path
			FontFactory.setAssetBasePath(FONTHBASEPATH);
			SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath(TEXTUREBASEPATH);
			//mCtx 	= ctx;
			mCtx 	= ctx.getApplicationContext();
			mEngine = eng;
			initialized = true;
		}
		else
			//if init called for already initilized manager but with different context and engine throw an exception
			if(mCtx != ctx || mEngine!= eng) //if intiliazation called on onother conxtx
				throw new IllegalArgumentException("ResourceManager, Init called two times with different context");
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
		BitmapTextureAtlas pTextureAtlas = new BitmapTextureAtlas(mEngine.getTextureManager(), pTextRegDsc.Parameters[ResTags.R_A_WIDTH_IDX], pTextRegDsc.Parameters[ResTags.R_A_HEIGHT_IDX], TextureOptions.BILINEAR);
		mapBitmapTexturesAtlas.put(pTextRegDsc.Name,pTextureAtlas);

		//iterate to all textureregion define in the texture
		for (TextureRegionDescriptor pTRDsc:pTextRegDsc.Regions){	
			
			if(mapBitmapTexturesAtlas.get(pTRDsc.Name)!=null) // check that texture region is new
				throw new IllegalArgumentException("In LoadTexture: Tentative to create a texture region that already exists ");

			//Create texture region
			mapTextureRegions.put(pTRDsc.Name, SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, 
					this.mCtx, pTRDsc.filename, pTRDsc.Parameters[ResTags.R_A_WIDTH_IDX], pTRDsc.Parameters[ResTags.R_A_HEIGHT_IDX], 
					pTRDsc.Parameters[ResTags.R_A_POSITION_X_IDX], pTRDsc.Parameters[ResTags.R_A_POSITION_Y_IDX]));
		}
				
		return pTextureAtlas;
	}
	
	public IFont LoadFont(String fontName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		FontDescriptor pFontDsc = pResDscMng.getFont(fontName);
		
		//create font
		Font newFont=null;
		if(pFontDsc.filename.contentEquals("")){
			//Create font from typeFace
			newFont = FontFactory.create(mEngine.getFontManager(),
					mEngine.getTextureManager(), pFontDsc.texture_sizeX,pFontDsc.texture_sizeY, pFontDsc.TypeFace, 
					pFontDsc.Parameters[ResTags.R_A_FONT_SIZE_IDX]);
		}
		else{
			final ITexture fontTexture = new BitmapTextureAtlas(Engine.getTextureManager(), pFontDsc.texture_sizeX,pFontDsc.texture_sizeY, TextureOptions.BILINEAR);
			mFont = FontFactory.createFromAsset(fontTexture, Engine.getTextureManager(), 
					pFontDsc.filename, pFontDsc.Parameters[ResTags.R_A_FONT_SIZE_IDX], pFontDsc.AntiAlias,
					android.graphics.Color.WHITE).load(Engine.getTextureManager(), Engine.getFontManager());

		}
		//add font to font manger
		this.mapFonts.put(pFontDsc.Name,newFont);
		//return the new font just created
		return newFont;
	}


	public ITextureRegion LoadTextureRegion(String textureRegionName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		TextureRegionDescriptor pTextRegDsc = pResDscMng.getTextureRegion(textureRegionName);
		if(pTextRegDsc == null)
			throw new IllegalArgumentException("In LoadTextureRegion: there is no description for the requested texture = " + textureRegionName);
		
		//To load a texture region the manager load the texture and all child regions
		LoadTexture(pTextRegDsc.textureName);
		
		//return the texture region that has just been loaded
		return mapTextureRegions.get(textureRegionName);
	}
	
	public ITextureRegion etTextureRegion(String textureRegionName){
		ITextureRegion theTexture = mapTextureRegions.get(textureRegionName);
		//if the texture region is not already loaded in the resource manager load it
		if(theTexture==null) 
			theTexture = LoadTextureRegion(textureRegionName);
		//return the found or loaded texture region
		return theTexture;
	}
	public Color getColor(String textureRegionName){
		return null;
	}
	
}
