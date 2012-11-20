package com.welmo.educational;


import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.welmo.andengine.managers.ResourcesManager;
import com.welmo.andengine.managers.SceneManager;
import com.welmo.andengine.scenes.IManageableScene;

public class ABCDaryApplication extends SimpleBaseGameActivity /*implements IActionOnSceneListener*/ {


	// ===========================================================
	// Constants
	// ===========================================================
	public static final float CAMERA_WIDTH = 800;
	public static final float CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================
	SceneManager 					mSceneManager;
	ResourcesManager 				mResourceManager;

	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		//Enable audio option
		camera.setZClippingPlanes(-1000, 1000);
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
		this.mEngine.registerUpdateHandler(new FPSLogger());
		mSceneManager = new SceneManager();
		mSceneManager.init(this.getEngine(), this);
		mSceneManager.BuildScenes("SceneLetterA");
		mSceneManager.BuildScenes("Test");
		mSceneManager.BuildScenes("Test2");
		mSceneManager.BuildScenes("ABCMainMenu");	
		mSceneManager.BuildScenes("LetterD");
		mSceneManager.BuildScenes("LetterE");
		mSceneManager.BuildScenes("LetterG");
		mSceneManager.BuildScenes("LetterA");
		mSceneManager.BuildScenes("LetterB");
		mSceneManager.BuildScenes("LetterH");
		mSceneManager.BuildScenes("LetterI");
		mSceneManager.BuildScenes("LetterL");
		mSceneManager.BuildScenes("LetterJ");
		mSceneManager.BuildScenes("MenuOfLetter2");
		mSceneManager.BuildScenes("MemoryPoker");
		
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
}
