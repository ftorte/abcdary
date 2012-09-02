package com.welmo.educational.managers;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.welmo.educational.resources.components.descriptors.BuildableTextureDescriptor;
import com.welmo.educational.resources.components.descriptors.ColorDescriptor;
import com.welmo.educational.resources.components.descriptors.FontDescriptor;
import com.welmo.educational.resources.components.descriptors.TextureDescriptor;
import com.welmo.educational.resources.components.descriptors.TextureRegionDescriptor;
import com.welmo.educational.resources.components.descriptors.TiledTextureRegionDescriptor;
import com.welmo.educational.scenes.description.tags.ResTags;

import android.content.Context;



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

	private HashMap<String, Font> 							mapFonts;
	private HashMap<String, ITextureRegion> 				mapTextureRegions;
	private HashMap<String, BitmapTextureAtlas> 			mapBitmapTexturesAtlas;
	private HashMap<String, Color> 							mapColors;
	private HashMap<String, BuildableBitmapTextureAtlas> 	mapBuildablBitmapTexturesAtlas;
	private HashMap<String, ITiledTextureRegion>  			mapTiledTextureRegions;
	
	// singleton Instance
	private static ResourcesManager 	mInstance=null;

	//--------------------------------------------------------
	// Constructors
	//--------------------------------------------------------
	private ResourcesManager(){
		mapFonts = new HashMap<String, Font>();
		mapTextureRegions = new HashMap<String, ITextureRegion>();
		mapBitmapTexturesAtlas = new HashMap<String, BitmapTextureAtlas>();
		mapColors = new HashMap<String, Color>();
		mapBuildablBitmapTexturesAtlas = new HashMap<String, BuildableBitmapTextureAtlas>();
		mapTiledTextureRegions = new HashMap<String, ITiledTextureRegion>();

		initialized = false;
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
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(TEXTUREBASEPATH);
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

	public ITexture loadTexture(String textureName){
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
	
	public IFont loadFont(String fontName){
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
			final ITexture fontTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), pFontDsc.texture_sizeX,pFontDsc.texture_sizeY, TextureOptions.BILINEAR);
			newFont = FontFactory.createFromAsset(mEngine.getFontManager(), fontTexture,this.mCtx.getAssets(), 
					pFontDsc.filename, pFontDsc.Parameters[ResTags.R_A_FONT_SIZE_IDX], pFontDsc.AntiAlias,
					android.graphics.Color.WHITE);
			newFont.load();

		}
		
		//add font to font manger
		this.mapFonts.put(pFontDsc.Name,newFont);
		//return the new font just created
		return newFont;
	}


	public ITextureRegion loadTextureRegion(String textureRegionName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		TextureRegionDescriptor pTextRegDsc = pResDscMng.getTextureRegion(textureRegionName);
		if(pTextRegDsc == null)
			throw new IllegalArgumentException("In LoadTextureRegion: there is no description for the requested texture = " + textureRegionName);
		
		//To load a texture region the manager load the texture and all child regions
		loadTexture(pTextRegDsc.textureName);
		
		//return the texture region that has just been loaded
		return mapTextureRegions.get(textureRegionName);
	}
	
	public ITextureRegion getTextureRegion(String textureRegionName){
		ITextureRegion theTexture = mapTextureRegions.get(textureRegionName);
		//if the texture region is not already loaded in the resource manager load it
		if(theTexture==null) 
			theTexture = loadTextureRegion(textureRegionName);
		//return the found or loaded texture region
		return theTexture;
	}
	public Color loadColor(String colorName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		ColorDescriptor pColorDsc = pResDscMng.getColor(colorName);
		
		//create color
		Color newColor = null;
		newColor = new Color((float)(pColorDsc.Parameters[ResTags.R_A_RED_IDX]/255.0), 
				(float)(pColorDsc.Parameters[ResTags.R_A_GREEN_IDX]/255.0),
						(float)( pColorDsc.Parameters[ResTags.R_A_BLUE_IDX]/255.0));	
		
		//add font to font manger
		this.mapColors.put(colorName,newColor);
		
		//return the new font just created
		return newColor;
	}
		
	public Color getColor(String colorName){
		Color theColor = mapColors.get(colorName);
		//if the texture region is not already loaded in the resource manager load it
		if(theColor==null) 
			theColor = loadColor(colorName);
		//return the found or loaded texture region
		return theColor;
		
	}
	public IFont getFont(String fontName){
		IFont theFont = mapFonts.get(fontName);
		//if the texture region is not already loaded in the resource manager load it
		if(theFont==null) 
			theFont = loadFont(fontName);
		//return the found or loaded texture region
		return theFont;
	}
	
	public ITexture loadBuildableTexture(String textureName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		BuildableTextureDescriptor pTextRegDsc = pResDscMng.getBuildableTexture(textureName);
	
		//check if texture already exists
		if(mapBuildablBitmapTexturesAtlas.get(pTextRegDsc.Name)!=null)
			throw new IllegalArgumentException("In LoadTexture: Tentative to load a texture already loaded");

		//Create the texture
		BuildableBitmapTextureAtlas pBuildableTextureAtlas = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), pTextRegDsc.Parameters[ResTags.R_A_WIDTH_IDX], pTextRegDsc.Parameters[ResTags.R_A_HEIGHT_IDX], TextureOptions.NEAREST);
		mapBuildablBitmapTexturesAtlas.put(pTextRegDsc.Name,pBuildableTextureAtlas);

		//iterate to all tiled textures regions define in the texture
		for (TiledTextureRegionDescriptor pTRDsc:pTextRegDsc.Regions){	
			
			if(mapBitmapTexturesAtlas.get(pTRDsc.Name)!=null) // check that texture region is new
				throw new IllegalArgumentException("In LoadTexture: Tentative to create a texture region that already exists ");

			//Create texture region
			mapTiledTextureRegions.put(pTRDsc.Name, 
					BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(pBuildableTextureAtlas, 
							this.mCtx, pTRDsc.filename, pTRDsc.column, pTRDsc.row));
		}
		try {
			pBuildableTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			pBuildableTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		return pBuildableTextureAtlas;
	}
	
	
	public ITiledTextureRegion loadTiledTextureRegion(String tiledTextureRegionName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		TiledTextureRegionDescriptor pTiledTextRegDsc = pResDscMng.getTiledTextureRegion(tiledTextureRegionName);
		if(pTiledTextRegDsc == null)
		 	throw new IllegalArgumentException("In LoadTiledTextureRegion: there is no description for the requested texture = " + tiledTextureRegionName);
		 
		//To load a texture region the manager load the texture and all child regions
		loadBuildableTexture(pTiledTextRegDsc.textureName);
		
		//return the texture region that has just been loaded
		return mapTiledTextureRegions.get(tiledTextureRegionName);
	}
	public ITiledTextureRegion getTiledTexture(String tiledTextureRegionName){
		ITiledTextureRegion theTiledTexture = this.mapTiledTextureRegions.get(tiledTextureRegionName);
		//if the texture region is not already loaded in the resource manager load it
		if(theTiledTexture==null) 
			theTiledTexture = loadTiledTextureRegion(tiledTextureRegionName);
		//return the found or loaded texture region
		return theTiledTexture;
	}
}
