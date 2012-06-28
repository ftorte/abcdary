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

	final int TD_TYPE 			= 0;
	final int TD_TXT_NAME 		= 1;
	final int TD_REG_NAME 		= 2;
	final int TD_REG__FILE 		= 3;
	final int TD_REG__W 		= 4;
	final int TD_REG__H 		= 5;
	final int TD_REG__PX 		= 6;
	final int TD_REG__PY 		= 7;



	// ===========================================================
	// Variables
	// ===========================================================
	Engine mEngine;
	Context mCtx;

	//Backgroud Textures
	protected BitmapTextureAtlas mBitmapTextureAtlas;
	protected ITextureRegion mMenuBackGround;
	//FaceTextureRegion;

	//Menu Textures
	protected ITextureRegion 				mMenuResetTextureRegion;
	protected ITextureRegion 				mMenuQuitTextureRegion;



	HashMap<String, Font> 					mapFonts = new HashMap<String, Font>();
	HashMap<String, ITextureRegion> 		mapTextureRegions = new HashMap<String, ITextureRegion>();
	HashMap<String, BitmapTextureAtlas> 	mapBitmapTexturesAtlas = new HashMap<String, BitmapTextureAtlas>();


	TextureManager 				mtextureManager = null;
	FontManager 				mFontManager = null;

	// ===========================================================

	public void init(Engine eng, Context ctx){
		// init resource manager with font & texture base path
		FontFactory.setAssetBasePath(FONTHBASEPATH);
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath(TEXTUREBASEPATH);
		mEngine 			= eng;
		mCtx 				= ctx;
		mtextureManager 	= mEngine.getTextureManager();
		mFontManager 		= mEngine.getFontManager();
	}

	//**********************************************************************************************
	// Load Resources Functions
	//**********************************************************************************************	
	public void LoadResources(String strResDescriptor){
		//if(TRACE) android.os.Debug.startMethodTracing("ABCDiary");
		if((mEngine == null) | (mCtx == null)){ 
			throw new NullPointerException("Scene Manager not initialized: mEngine or mContext are null"); 
		}

		//Load Scene Descriptor
		final int resID = mCtx.getResources().getIdentifier(strResDescriptor, "string", mCtx.getPackageName());
		String strSceneDescriptor = mCtx.getResources().getString(resID);
		String tokens[] = strSceneDescriptor.split(";");
		if(tokens.length != 3)
			throw new IllegalArgumentException("Scene Description invalid: Scene desciption doesen't has expected 3 values");

		//Load Scene Fonts
		if(!tokens[0].equals("-")){
			final int strArrayID = mCtx.getResources().getIdentifier(tokens[0], "array", mCtx.getPackageName());
			CharSequence[] fontList = mCtx.getResources().getTextArray(strArrayID);
			for(int index=0; index < fontList.length; index++){
				String fontDescription[] = fontList[index].toString().split(";");
				if(fontDescription.length != 3)
					throw new IllegalArgumentException("Font Description invalid: font desciption doesn't has 3 values");
				createFont(fontDescription[0], fontDescription[1], Integer.parseInt(fontDescription[2]));
			}
		}

		//Load Scene Textures & TextureRegions
		if(!tokens[1].equals("-")){
			final int strArrayID = mCtx.getResources().getIdentifier(tokens[1], "array", mCtx.getPackageName());
			CharSequence[] texturesList = mCtx.getResources().getTextArray(strArrayID);
			for(int index=0; index < texturesList.length; index++){
				String textureDescription[] = texturesList[index].toString().split(";");

				if(textureDescription.length != 8)
					throw new IllegalArgumentException("Texture Description invalid: texture desciption doesn't has 8 values but " + textureDescription.length);
				createTextures(textureDescription);
			}

			//Load textures in the engine
			for(String key:mapBitmapTexturesAtlas.keySet())
				mtextureManager.loadTexture(mapBitmapTexturesAtlas.get(key));
		}
	}

	public void LoadResources(String SceneName,ParserXMLSceneDescriptor prScene){
		
	}
	
	
	/*public Font LoadFont(String FontName){
		/*ScnComponentDsc scDesc = null;
		if((scDesc=ResourceDescriptorsManager.fonts.get(FontName)) == null)
			throw new NullPointerException(new String("The font " + FontName + " Is not a valid font name"));
		createFont(scDesc[0], scDesc[1], Integer.parseInt(scDesc[2]));
	}*/
	
	public ITextureRegion LoadTextureRegion(String textureRegionName){
		ResourceDescriptorsManager pResDscMng = ResourceDescriptorsManager.getInstance();
		TextureRegionDescriptor pTextRegDsc = pResDscMng.getTextureRegion(textureRegionName);
		if(pTextRegDsc == null)
			throw new IllegalArgumentException("In LoadTextureRegion: there is no description for the requested texture");
		LoadTexture(pTextRegDsc.textureName);
		return mapTextureRegions.get(textureRegionName);
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

		//iterate to all textureregion and load in the engine
		for (TextureRegionDescriptor pTRDsc:pTextRegDsc.Regions){	
			
			if(mapBitmapTexturesAtlas.get(pTRDsc.Name)!=null) // check that texture region is new
				throw new IllegalArgumentException("In LoadTexture: Tentative to create a texture region that already exists ");

			//Create texture region
			mapTextureRegions.put(pTRDsc.Name, SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, 
					this.mCtx, pTRDsc.filename, pTRDsc.Parameters[XMLTags.WIDTH_IDX], pTRDsc.Parameters[XMLTags.HEIGHT_IDX], 
					pTRDsc.Parameters[XMLTags.POSITION_X_IDX], pTRDsc.Parameters[XMLTags.POSITION_Y_IDX]));
		}
		
		//(Re)Load textures in the engine
		for(String key:mapBitmapTexturesAtlas.keySet())
			mtextureManager.loadTexture(mapBitmapTexturesAtlas.get(key));
		
		return pTextureAtlas;
	}
	
	//**********************************************************************************************
	// Private functions
	//**********************************************************************************************	
	private void createTextures(String[] TD){

		if (TD[this.TD_TYPE ].equals("T")){
			//check if texture already exists
			if(mapBitmapTexturesAtlas.get(TD[this.TD_TXT_NAME])!=null)
				return;
			else{
				String key = new String(TD[this.TD_TXT_NAME]);
				mapBitmapTexturesAtlas.put(key,new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR));
			}
			return;
			//TODO need to read texture dimension
		}
		else if(TD[this.TD_TYPE].equals("R")){
			//check if texture region already exists
			if(mapBitmapTexturesAtlas.get(TD[this.TD_REG_NAME])!=null)
				return;
			//check if texture exists if not exist exception
			BitmapTextureAtlas pTextureAtlas = null;
			if((pTextureAtlas = mapBitmapTexturesAtlas.get(TD[this.TD_TXT_NAME]))==null) 
				throw new NullPointerException("Texture region need texture bitmap to be created: Creation of " + TD[1]);
			// create texture region
			// Read wh,px,py values
			int w = Integer.parseInt(TD[this.TD_REG__W]);
			int h = Integer.parseInt(TD[this.TD_REG__H]);
			int px = Integer.
					parseInt(TD[this.TD_REG__PX]);
			int py = Integer.parseInt(TD[this.TD_REG__PY]);

			String key = new String(TD[this.TD_REG_NAME]);
			mapTextureRegions.put(key, SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, 
					this.mCtx, TD[this.TD_REG__FILE], w, h, px, py));
		}
	}
	private void createFont(String strFontNamekey, String strAssetPath, int pSize){
		mapFonts.put(strFontNamekey, FontFactory.createFromAsset(new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR),
				mCtx, strAssetPath, pSize, true, Color.WHITE).load(mtextureManager, mFontManager));
	}
	
	//**********************************************************************************************
	// Get Resources Methods
	//**********************************************************************************************	
	/*public Font GetFont(String fontName){
		final Font theFont = mapFonts.get(fontName);
		if(theFont==null) 
			//throw new NullPointerException(new String("The font " + fontName + " Doesen't Exist"));
			this.LoadFont(fontName);
		return theFont;
	}*/

	public ITextureRegion GetTexture(String textureRegionName){
		ITextureRegion theTexture = mapTextureRegions.get(textureRegionName);
		if(theTexture==null) 
			//throw new NullPointerException(new String("The texture region  " + textureRegionName + " Doesn't Exist"));
			theTexture = LoadTextureRegion(textureRegionName);
		return theTexture;
	}
	
}
