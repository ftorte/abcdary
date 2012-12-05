package com.welmo.educational;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.view.KeyEvent;

import com.welmo.andengine.managers.ResourcesManager;
import com.welmo.andengine.managers.SceneManager;
import com.welmo.andengine.scenes.IManageableScene;
import com.welmo.andengine.scenes.MemoryScene;
import com.welmo.andengine.scenes.descriptors.components.GameLevel;

public class ABCDaryApplication extends SimpleBaseGameActivity implements IOnMenuItemClickListener /*implements IActionOnSceneListener*/ {


	// ===========================================================
	// Constants
	// ===========================================================
	public static final float CAMERA_WIDTH = 800;
	public static final float CAMERA_HEIGHT = 480;

	public static final int 		MENU_EASY = 0;
	public static final int 		MENU_MEDIUM = MENU_EASY + 1;
	public static final int 		MENU_DIFFICULT = MENU_MEDIUM +1;
	public static final int 		MENU_HARD = MENU_DIFFICULT +1;
	//private static final String 	MemoryScene = null;
	
	// ===========================================================
	// Fields
	// ===========================================================
	SceneManager 					mSceneManager;
	ResourcesManager 				mResourceManager;
	
	//TODO to transform this in configurable parameter o scene
	protected MenuScene mStaticMenuScene, mPouUpMenuScene;
	private boolean bPopupDisplayed = false;
	Camera 							mCamera;
	
	

	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		//Enable audio option
		mCamera.setZClippingPlanes(-1000, 1000);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		mResourceManager = ResourcesManager.getInstance();
		mResourceManager.init(this, this.mEngine);
		mResourceManager.getTextureRegion("MenuItemLettere");
		mResourceManager.getTextureRegion("MenuArrayLetterA");
		mResourceManager.getTextureRegion("MenuBackGround");
		mResourceManager.getTextureRegion("BcgA");
		mResourceManager.getTextureRegion("Big-A");	
		mResourceManager.getTextureRegion("flower_pink");
		mResourceManager.getTextureRegion("flower_blue_small");
		mResourceManager.getTiledTextureRegion("TheBee_flightRight");	
		mResourceManager.getTextureRegion("CardWiteBCG");
		mResourceManager.getMusic("Vento_Desertico");
		mResourceManager.getSound("Animal1");
		mResourceManager.getTiledTextureRegion("TheCardRegion");
		mResourceManager.getTiledTextureRegion("The30DeckCardRegion");
		mResourceManager.EngineLoadResources(this.mEngine);
		this.mEngine.onReloadResources();
	}
	@Override
	protected Scene onCreateScene() {
		createPouUpMenu();
		
		this.mEngine.registerUpdateHandler(new FPSLogger());
		mSceneManager = new SceneManager(this);
		mSceneManager.init(this.getEngine(), this);
		mSceneManager.BuildScenes("SceneLetterA",this);
		mSceneManager.BuildScenes("Test",this);
		mSceneManager.BuildScenes("Test2",this);
		mSceneManager.BuildScenes("ABCMainMenu",this);	
		mSceneManager.BuildScenes("LetterD",this);
		mSceneManager.BuildScenes("LetterE",this);
		mSceneManager.BuildScenes("LetterG",this);
		mSceneManager.BuildScenes("LetterA",this);
		mSceneManager.BuildScenes("LetterB",this);
		mSceneManager.BuildScenes("LetterH",this);
		mSceneManager.BuildScenes("LetterI",this);
		mSceneManager.BuildScenes("LetterL",this);
		mSceneManager.BuildScenes("LetterJ",this);
		mSceneManager.BuildScenes("MenuOfLetter2",this);
		mSceneManager.BuildScenes("MemoryPoker",this);
		
		//Music music = mResourceManager.getMusic("Vento_Desertico");
		//music.play();

		return (Scene)mSceneManager.getScene("ABCMainMenu");

	}
	@Override
	public void onBackPressed() {
		Scene currentScene = this.mEngine.getScene();
		if(currentScene instanceof IManageableScene){
			String fatherSceneName = ((IManageableScene)currentScene).getFatherScene();
			if(fatherSceneName.length() == 0)
				super.onBackPressed();
			else
				this.mEngine.setScene((Scene)mSceneManager.getScene(fatherSceneName));
		}
	}
	
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		
		
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			Scene theScene = mEngine.getScene();
			if(theScene instanceof MemoryScene){
				if(theScene.hasChildScene()) {
					/* Remove the menu and reset it. */
					this.mPouUpMenuScene.back();
				} else {
					/* Attach the menu. */
					theScene.setChildScene(this.mPouUpMenuScene, false, true, true);
				}
			}
			else 
				return super.onKeyDown(pKeyCode, pEvent);
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	protected void createPouUpMenu(){
		this.mPouUpMenuScene = new MenuScene(this.mCamera);
		
		TextMenuItem tmpMenuItem;
		//EASY
		tmpMenuItem = new TextMenuItem(MENU_EASY,mResourceManager.getFont("FontAndroid"),"Memory Easy \n", this.mEngine.getVertexBufferObjectManager());
		final IMenuItem easyMenuItem = new ColorMenuItemDecorator(
				tmpMenuItem,
				new Color(0.5f,0.5f,0.5f),new Color(1.0f,0.0f,0.0f));
		this.mPouUpMenuScene.addMenuItem(easyMenuItem);
		//MEDIMUM
		tmpMenuItem = new TextMenuItem(MENU_MEDIUM,mResourceManager.getFont("FontAndroid"),"Memory Medium \n", this.mEngine.getVertexBufferObjectManager());
		final IMenuItem mediumMenuItem = new ColorMenuItemDecorator(
				tmpMenuItem,
				new Color(0.5f,0.5f,0.5f),new Color(1.0f,0.0f,0.0f));
		this.mPouUpMenuScene.addMenuItem(mediumMenuItem);
		//Difficult
		tmpMenuItem = new TextMenuItem(MENU_DIFFICULT,mResourceManager.getFont("FontAndroid"),"Memory Difficult \n", this.mEngine.getVertexBufferObjectManager());
		final IMenuItem difficultMenuItem = new ColorMenuItemDecorator(
				tmpMenuItem,
				new Color(0.5f,0.5f,0.5f),new Color(1.0f,0.0f,0.0f));
		this.mPouUpMenuScene.addMenuItem(difficultMenuItem);
		//High
		tmpMenuItem = new TextMenuItem(MENU_HARD,mResourceManager.getFont("FontAndroid"),"Memory Hard \n", this.mEngine.getVertexBufferObjectManager());
		final IMenuItem hardMenuItem = new ColorMenuItemDecorator(
				tmpMenuItem,
				new Color(0.5f,0.5f,0.5f),new Color(1.0f,0.0f,0.0f));
		this.mPouUpMenuScene.addMenuItem(hardMenuItem);
		//Finalize creation of menu
		this.mPouUpMenuScene.buildAnimations();

		this.mPouUpMenuScene.setBackgroundEnabled(true);
		
		this.mPouUpMenuScene.setOnMenuItemClickListener(this);
	}
	

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		MemoryScene memory;
		memory = (MemoryScene)this.mEngine.getScene();
		switch(pMenuItem.getID()){
		case MENU_EASY:
			memory.ReLoadGame(GameLevel.EASY);			
			memory.clearChildScene();
			return true;
		case MENU_MEDIUM: 
			memory.ReLoadGame(GameLevel.MEDIUM);
			memory.clearChildScene();
			return true;
		case MENU_DIFFICULT: 
			memory.ReLoadGame(GameLevel.DIFFICULT);
			memory.clearChildScene();
			return true;
		case MENU_HARD: 
			memory.ReLoadGame(GameLevel.HARD);
			memory.clearChildScene();
			return true;
		}
		memory.clearChildScene();
		return false;
	}
}
