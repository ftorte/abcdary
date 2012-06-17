package com.welmo.educational.scenes;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.input.touch.detector.ScrollDetector.IScrollDetectorListener;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseCubicOut;

import com.welmo.educational.MenuArrayLettere;
import com.welmo.educational.managers.ResourceDescriptorsManager;
import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.components.ClickableSprite.IClickLeastener;
import com.welmo.educational.utility.SceneDescriptor;
import com.welmo.educational.utility.SceneObjectDescriptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SceneMenuArray extends ManageableScene implements IClickDetectorListener, IOnSceneTouchListener
{

	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "MenuArrayScene";
	private static final boolean debug = true;
	
	
	//other constants
	private static final int INVALID_CHAR_CLICKED = -1;

	// ===========================================================
	// Fields - Items and global properties
	// ===========================================================
	protected List<ITextureRegion> columns = new ArrayList<ITextureRegion>(); 	//A list that contains items textures to build menu.	
	protected float cameraWidth; 												// The width of the window view, the menu slider is in full screen
	protected int nNbRows;
	protected int nNbCols;
	protected MenuArrayLettere mApplication;
	protected ResourceDescriptorsManager pResDescMgr;
	
	// ===========================================================
	// Fields - View objects
	// ===========================================================
    protected Entity container;													//Entity object that contains items, it moves on scroll.
	protected List<Sprite> sprites = new ArrayList<Sprite>();					//A list that contains menu items;
	
	// ===========================================================
	// Fields - Detectors
	// ===========================================================
	protected ClickDetector clickDetector;  									//An object which detect the click action on touch
	ClicalbeSpriteLeastener mClickLeastener;
	// ===========================================================
	// Methods - Initialization and menu preparation
	// ===========================================================

	private Engine mEngine;

	private Context mContext;
	
	//Constructor
	public SceneMenuArray(MenuArrayLettere pApplication){
		super();
		mApplication = pApplication;
		InitSceneValues();
		mClickLeastener =  new ClicalbeSpriteLeastener();
		pResDescMgr = ResourceDescriptorsManager.getInstance();
	}

	//Constructor
	public SceneMenuArray(){
		super();
		mApplication = null;
		InitSceneValues();
		mClickLeastener =  new ClicalbeSpriteLeastener();
		pResDescMgr = ResourceDescriptorsManager.getInstance();
	}
	
	// ===========================================================
	// Methods - Show and Hide menu
	// ===========================================================
	
	/**
	 * When menu is attach to be use, call this method to activate detector and touch events
	 * 
	 * @param scene (Scene) - scene where the menu is attached
	 */
	public void onShow(Scene scene)
	{
		if(clickDetector == null)
			clickDetector = new ClickDetector(this);
		
		clickDetector.reset();
		clickDetector.setEnabled(true);

		scene.setOnSceneTouchListener(this);
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}


	/**
	 * When menu is detach to be use, call this method to deactivate detector and touch events and reset container moves
	 * 
	 * @param scene (Scene) - scene from the menu is detached
	 */
	public void onHide(Scene scene)
	{
		clickDetector.setEnabled(false);
		scene.setOnSceneTouchListener(null);
	}

	// ===========================================================
	// Methods - Events IOnSceneTouchListener
	// ===========================================================
	/** 
	 * Transmit touch event to detectors to catch correct touch action
	 * 
	 * @see org.andengine.entity.scene.Scene.IOnSceneTouchListener#onSceneTouchEvent(org.andengine.entity.scene.Scene, org.andengine.input.touch.TouchEvent)
	 */
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		 if(debug) Log.i(TAG,"onSceneTouchEvent " + pSceneTouchEvent.getAction());
		 clickDetector.onTouchEvent(pSceneTouchEvent);
         return true;
	}

	// ===========================================================
	// Methods - Events IClickDetectorListener
	// ===========================================================
	/** 
	 * Change this fonction or override it to defined the action on clic on an item.
	 * 
	 * @see org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener#onClick(org.andengine.input.touch.detector.ClickDetector, int, float, float)
	 */
	@Override
	public void onClick(ClickDetector pClickDetector, int pPointerID,
			float pSceneX, float pSceneY) {

		if(debug) Log.i(TAG,"onClick");
		if(debug) Log.i(TAG,"Menu ID = " + mClickLeastener.miItemClicked);
		
		if(mClickLeastener.miItemClicked != this.INVALID_CHAR_CLICKED)
			mApplication.LaunchLetterScreen(mClickLeastener.miItemClicked);	
		mClickLeastener.miItemClicked = this.INVALID_CHAR_CLICKED;
	}


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
	
	private void InitSceneValues() {
		scDsc = new SceneWidgetDescriptor[4];
		
		//A 		
		scDsc[0] = new SceneWidgetDescriptor();
		scDsc[0].type = SceneWidgetDescriptor.TYPE_TEXTURE_SPRITE;
		scDsc[0].p1 = 0;
		scDsc[0].p2 = 0;
		scDsc[0].p3 = 64;
		scDsc[0].p4 = 64;
		scDsc[0].resourceName = "MenuArrayLetterA";

		//B	
		scDsc[1] = new SceneWidgetDescriptor();
		scDsc[1].type = SceneWidgetDescriptor.TYPE_TEXTURE_SPRITE;
		scDsc[1].p1 = 128;
		scDsc[1].p2 = 0;
		scDsc[1].p3 = 64;
		scDsc[1].p4 = 64;
		scDsc[1].resourceName = "MenuArrayLetterA";

		//A 		
		scDsc[2] = new SceneWidgetDescriptor();
		scDsc[2].type = SceneWidgetDescriptor.TYPE_TEXTURE_SPRITE;
		scDsc[2].p1 = 0;
		scDsc[2].p2 = 128;
		scDsc[2].p3 = 64;
		scDsc[2].p4 = 64;
		scDsc[2].resourceName = "MenuArrayLetterA";
				
		//text
		scDsc[3] = new SceneWidgetDescriptor();
		scDsc[3].type = SceneWidgetDescriptor.TYPE_FONT;
		scDsc[3].p1 = 100;
		scDsc[3].p2 = 40;
		scDsc[3].p3 = 10;
		scDsc[3].ha = HorizontalAlign.CENTER;
		scDsc[3].resourceName = "FontDroid";
		scDsc[3].message = "Droid Font" + 1000000;
		

	}
	
	
	
	public void loadScene(String SceneName,ResourcesManager res) {
		SceneDescriptor scDsc = pResDescMgr.getScene(SceneName);
		int i=0;
		for(SceneObjectDescriptor scObjDsc:scDsc.scObjects){
			final int itemToLoad = i++;
			switch(scObjDsc.type){
			case SPRITE:
				break;
			case CLICKABLE_SPRITE:
				/* Create the clickable sprites elements */
				final ClickableSprite newSprite = new ClickableSprite(scObjDsc.Parameters[0], scObjDsc.Parameters[1], 
						scObjDsc.Parameters[2], scObjDsc.Parameters[3], res.GetTexture(scObjDsc.resourceName), 
						this.mEngine.getVertexBufferObjectManager());
				newSprite.SetClickListener(mClickLeastener);
				newSprite.setID(itemToLoad);
				this.attachChild(newSprite);
				this.registerTouchArea(newSprite);

				sprites.add(newSprite);
				break;
			}
		}
		this.onShow(this);
	}

	@Override
	public void init(Engine theEngine, Context ctx) {
		mEngine = theEngine;
		mContext = ctx;
		
		this.mApplication = (MenuArrayLettere) ctx;
	}
	
	// ===========================================================
	// Inner private classes
	// ===========================================================
	private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{

		public int miItemClicked=-1;
		@Override
		public void onClick(int ObjectID) {
			miItemClicked = ObjectID;
		}
		public void reset() {
			miItemClicked=-1;
		}
		
	}
}
