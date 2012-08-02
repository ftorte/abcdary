package com.welmo.educational.scenes;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;

import android.content.Context;
import android.util.Log;

import com.welmo.educational.MainMenu;
import com.welmo.educational.MenuArrayLettere;
import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.utility.MLOG;


public class SceneMainMenu extends ManageableScene implements IClickDetectorListener, IOnSceneTouchListener
{

	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "MenuArrayScene";
	
	//other constants
	private static final int INVALID_CHAR_CLICKED = -1;

	// ===========================================================
	// Fields - Items and global properties
	// ===========================================================
	protected MainMenu mApplication;
	
	// ===========================================================
	// Fields - Detectors
	// ===========================================================
	protected ClickDetector clickDetector;  									//An object which detect the click action on touch
	//ClicalbeSpriteLeastener mClickLeastener;
	// ===========================================================
	// Methods - Initialization and menu preparation
	// ===========================================================
	
	//Constructor
	public SceneMainMenu(MainMenu pApplication){
		super();
		mClickLeastener=new SceneMainMenu.ClicalbeSpriteLeastener();
		mApplication = pApplication;
	}

	//Constructor
	public SceneMainMenu(){
		super();
		mClickLeastener=new SceneMainMenu.ClicalbeSpriteLeastener();
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
		 if(MLOG.LOG) Log.i(TAG,"onSceneTouchEvent " + pSceneTouchEvent.getAction());
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

		if(MLOG.LOG) Log.i(TAG,"onClick");
		if(MLOG.LOG) Log.i(TAG,"Menu ID = " + this.mClickLeastener.getObjectID());
		
		//Check if valid object
		if(mClickLeastener.getObjectID() != this.INVALID_CHAR_CLICKED)
			mApplication.LaunchLetterScreen(mClickLeastener.getObjectID());	
		
		//reset click event
		mClickLeastener.onClick(this.INVALID_CHAR_CLICKED);
	}

	

	@Override
	public void init(Engine theEngine, Context ctx) {
		super.init(theEngine, ctx);
		this.mApplication = (MainMenu) ctx;
	}
	
	// ===========================================================
	// Inner private classes
	// ===========================================================
	private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{

		private int miItemClicked=-1;
		@Override
		public void onClick(int ObjectID) {
			miItemClicked = ObjectID;
		}
		@Override
		public void reset() {
			miItemClicked=-1;
		}
		@Override
		public int getObjectID(){
			return miItemClicked;
		}
	}

	@Override
	public void loadScene(String SceneName, ResourcesManager res) {
		// TODO Auto-generated method stub
		super.loadScene(SceneName, res);
		// FT disable load resource res.EngineLoadResources(this.mEngine);
		// enable the touch listener
		this.onShow(this);
	}
}
