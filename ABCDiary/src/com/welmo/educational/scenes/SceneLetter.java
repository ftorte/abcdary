package com.welmo.educational.scenes;

import java.util.Arrays;

import org.andengine.engine.Engine;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import com.welmo.educational.managers.ResourcesManager;


import android.content.Context;
import android.graphics.Color;

public class SceneLetter extends ManageableScene {

	
		// ===========================================================
	// Constants	
	// ===========================================================
	//Log & Debug & trace
		private static final String TAG = "SceneLetter";
		private static final boolean TRACE = false; 
		
	public static final char[] LETTERS={	
		'A','B','C','D','E','F','G',
		'H','I','J','K','L','M','N',
		'O','P','Q','R','S','T','U',
		'V','W','X','Y','Z'};
	public static final int 	NB_LETTERS 	= 27;
	private static final int FONT_SIZE = 48;
	// ===========================================================
	// Temporary scene descriptor to build schene
	String[] sceneDescriptor = new String[]{
				"1;4;",
				"img=image.svg, snd=soundA.xxx, shd2= snd=soundA.xxx;", 
				"img=image.svg, snd=soundA.xxx, shd2= snd=soundA.xxx;", 
				"img=image.svg, snd=soundA.xxx, shd2= snd=soundA.xxx;", 
				"img=image.svg, snd=soundA.xxx, shd2= snd=soundA.xxx;"};
	
	private class SceneWidgetDescriptor{
		// Constants
		private static final int TYPE_INVALID			=-1;
		private static final int TYPE_TEXTURE_SPRITE	=0;
		private static final int TYPE_FONT				=1;
		
			
		// variables
		public int type 			= TYPE_INVALID;
		public int p1				= -1;
		public int p2				= -1;
		public int p3				= -1;
		public int p4				= -1;
		public String resourceName	="";
		public String message		="";
		public HorizontalAlign ha	=HorizontalAlign.CENTER;
		boolean isClicable = false;
	}
	
	SceneWidgetDescriptor[] scDsc = null;
	
	TickerText theText;
	int nSceneLetter = 0;
	int nNbScenes=0;
	
	Engine mEngine;
	Context mContext;
	//private Font mDroidFont;
	
	
	public SceneLetter() {
		scDsc = new SceneWidgetDescriptor[2];
		//Background
		scDsc[0] = new SceneWidgetDescriptor();
		scDsc[0].type = SceneWidgetDescriptor.TYPE_TEXTURE_SPRITE;
		scDsc[0].p1 = 0;
		scDsc[0].p2 = 0;
		scDsc[0].resourceName = "MenuBackGround";
		
		//text
		scDsc[1] = new SceneWidgetDescriptor();
		scDsc[1].type = SceneWidgetDescriptor.TYPE_FONT;
		scDsc[1].p1 = 100;
		scDsc[1].p2 = 40;
		scDsc[1].p3 = 10;
		scDsc[1].ha = HorizontalAlign.CENTER;
		scDsc[1].resourceName = "FontDroid";
		scDsc[1].message = "Droid Font" + 1000000;
		
		//Central Image
		/*scDsc[2] = new SceneWidgetDescriptor();
		scDsc[0].type = SceneWidgetDescriptor.TYPE_TEXTURE_SPRITE;
		scDsc[0].p1 = 400;
		scDsc[0].p2 = 200;
		scDsc[0].resourceName = "TestLetterImage";*/
		
		
	}

	@Override
	public void loadScene2(String Scenedescriptor, ResourcesManager res) {
		loadScene( Scenedescriptor, res);
	}
	@Override
	public void loadScene(String Scenedescriptor, ResourcesManager res) {
		
		//if(TRACE) android.os.Debug.startMethodTracing("ABCDiary");	
		this.mEngine.registerUpdateHandler(new FPSLogger());

		for (int i = 0; i< scDsc.length; i++ )
		{
			SceneWidgetDescriptor dsc = scDsc[i];
			
			switch(dsc.type){
			case  SceneWidgetDescriptor.TYPE_INVALID:break;
			case  SceneWidgetDescriptor.TYPE_FONT:
				/* Create Text*/
				TickerTextOptions textOptions = new TickerTextOptions();
				textOptions.setCharactersPerSecond(dsc.p3);
				textOptions.setHorizontalAlign(dsc.ha);
				this.attachChild(theText = new TickerText(dsc.p1, dsc.p2, res.GetFont(dsc.resourceName), 
						dsc.message, textOptions, this.mEngine.getVertexBufferObjectManager()));
				break;

			case  SceneWidgetDescriptor.TYPE_TEXTURE_SPRITE:
				/* Create the background sprite and add it to the scene. */
				final Sprite newSprite = new Sprite(dsc.p1, dsc.p2, res.GetTexture(dsc.resourceName), 
						this.mEngine.getVertexBufferObjectManager());
				this.attachChild(newSprite);
				break;
			}
		}

	}

	@Override
	public void init(Engine theEngine, Context ctx) {
		// TODO Auto-generated method stub
		mEngine = theEngine;
		mContext = ctx;
	}
	
	public void resetScene(){
		//theText.reset();
	}

}
