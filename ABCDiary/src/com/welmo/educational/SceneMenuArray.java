package com.welmo.educational;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ClickDetector.IClickDetectorListener;

import com.welmo.andengine.scenes.ManageableScene;
import com.welmo.andengine.scenes.components.ClickableSprite;
import com.welmo.andengine.utility.MLOG;

import android.content.Context;
import android.util.Log;

public class SceneMenuArray extends ManageableScene implements IClickDetectorListener, IOnSceneTouchListener
{

	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "MenuArrayScene";
	//private int miItemClicked=-1;
	
	//other constants
	//private static final int INVALID_CHAR_CLICKED = -1;
	
	//private static final Class<MenuArrayLettere> ACTIVITY_MENUARRAY = MenuArrayLettere.class;	

	// ===========================================================
	// Fields - Items and global properties
	// ===========================================================
	protected MenuArrayLettere mApplication;
	
	// ===========================================================
	// Fields - Detectors
	// ===========================================================
	protected ClickDetector clickDetector;  									//An object which detect the click action on touch
	//ClicalbeSpriteLeastener mClickLeastener;
	// ===========================================================
	// Methods - Initialization and menu preparation
	// ===========================================================
	
	//Constructor
	public SceneMenuArray(MenuArrayLettere pApplication){
		super();
		// [FT] mClickLeastener=new SceneMenuArray.ClicalbeSpriteLeastener();
		mApplication = pApplication;
	}

	//Constructor
	public SceneMenuArray(){
		super();
		// [FT] mClickLeastener=new SceneMenuArray.ClicalbeSpriteLeastener();
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

		scene.setOnSceneTouchListener( this);
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
	}
	@Override
	public void init(Engine theEngine, Context ctx) {
		super.init(theEngine, ctx);
		this.mApplication = (MenuArrayLettere) ctx;
	}
	@Override
	public void loadScene(String SceneName) {
		// TODO Auto-generated method stub
		super.loadScene(SceneName);
		// enable the touch listener
		this.onShow(this);
	}
}
