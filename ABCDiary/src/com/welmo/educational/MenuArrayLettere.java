package com.welmo.educational;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.managers.SceneManager;
import com.welmo.educational.scenes.ManageableScene;
import com.welmo.educational.scenes.SceneLetter;
import com.welmo.educational.scenes.SceneMainMenu;
import com.welmo.educational.scenes.SceneMenuArray;

public class MenuArrayLettere extends SimpleBaseGameActivity /*implements IActionOnSceneListener*/ {
	

	// ===========================================================
	// Constants
	// ===========================================================
	public static final float CAMERA_WIDTH = 800;
	public static final float CAMERA_HEIGHT = 480;
	public static final	String INTENT_KEY_PARAM_A = "LetterID";
	
	// ===========================================================
	// Fields
	// ===========================================================

	SceneManager<SceneMenuArray> 	mSceneLetterMenuManager;
	SceneManager<SceneLetter> 		mSceneLetterManager;
	SceneManager<ManageableScene> 	mSceneManager;
	SceneMainMenu  					mSceneMainMenu;
	ResourcesManager 				mResourceManager;

	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		mResourceManager = ResourcesManager.getInstance();
		mResourceManager.init(this, this.mEngine);
		mResourceManager.GetTextureRegion("MenuItemLettere");
		mResourceManager.GetTextureRegion("MenuArrayLetterA");
		mResourceManager.GetTextureRegion("MenuBackGround");
		mResourceManager.EngineLoadResources(this.mEngine);
		this.mEngine.onReloadResources();
	}
	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		mSceneManager = new SceneManager<ManageableScene>(ManageableScene.class);
		mSceneManager.init(this.getEngine(), this);
		mSceneManager.BuildScenes("SceneLetterA","SceneLetterA",mResourceManager);
		mSceneManager.BuildScenes("Test","Test",mResourceManager);
		mSceneManager.BuildScenes("Test2","Test2",mResourceManager);
		mSceneManager.BuildScenes("ABCMainMenu","ABCMainMenu",mResourceManager);	
		return mSceneManager.getScene("ABCMainMenu");
		
	}
	@Override
	public void onBackPressed() {
		Scene currentScene = this.mEngine.getScene();
		if(currentScene == mSceneManager.getScene("ABCMainMenu")){
			super.onBackPressed();
			return;
		}
		if(currentScene == mSceneManager.getScene("Test2")){
			this.mEngine.setScene(mSceneManager.getScene("Test"));
			return;
		}
		if(currentScene == mSceneManager.getScene("Test")){
			this.mEngine.setScene(mSceneManager.getScene("SceneLetterA"));
			return;
		}
		if(currentScene == mSceneManager.getScene("SceneLetterA")){
			this.mEngine.setScene(mSceneManager.getScene("ABCMainMenu"));
			return;
		}
	}
}