package com.welmo.educational;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Intent;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.SceneMainMenu;


public class MainMenu extends SimpleBaseGameActivity {
	

	// ===========================================================
	// Constants
	// ===========================================================

	public static final float CAMERA_WIDTH = 800;
	public static final float CAMERA_HEIGHT = 480;
	
	private SceneMainMenu mTheScene;
	
	private static final Class<MenuArrayLettere> FOLLOWING_ACTIVITY = MenuArrayLettere.class;	

	// ===========================================================
	// Fields
	// ===========================================================

	ResourcesManager mResourceManager;

	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		mResourceManager = ResourcesManager.getInstance();
		mResourceManager.init(this, this.mEngine);
		mResourceManager.getTextureRegion("MenuItemLettere");
		mResourceManager.getTextureRegion("MenuArrayLetterA");
		mResourceManager.getTextureRegion("MenuBackGround");
		mResourceManager.EngineLoadResources(this.mEngine);
	}

	@Override
	protected Scene onCreateScene() {
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		mTheScene = new SceneMainMenu();
		mTheScene.init(this.getEngine(), this);
		mTheScene.loadScene("ABCMainMenu");

		//mResourceManager.EngineLoadResources(this.mEngine);
		
		return mTheScene;
	}
	
	public void LaunchLetterScreen(int letterID){
		
		Intent intent = new Intent(MainMenu.this, FOLLOWING_ACTIVITY);
		startActivity(intent);
		
	}
	@Override
	public void onBackPressed() {
			super.onBackPressed();
	}
}