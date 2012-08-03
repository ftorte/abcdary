package com.welmo.educational;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.BaseTextureRegion;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.managers.SceneManager;
import com.welmo.educational.scenes.ManageableScene;
import com.welmo.educational.scenes.SceneLetter;
import com.welmo.educational.scenes.SceneMainMenu;
import com.welmo.educational.scenes.SceneMenuArray;

import android.content.Intent;

public class MenuArrayLettere extends SimpleBaseGameActivity {
	

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

		mSceneLetterMenuManager = new SceneManager<SceneMenuArray>(SceneMenuArray.class);
		mSceneLetterMenuManager.init(this.getEngine(), this);
		mSceneLetterMenuManager.BuildScenes("SceneLetterA","SceneLetterA",mResourceManager);

		mSceneLetterManager = new SceneManager<SceneLetter>(SceneLetter.class);
		mSceneLetterManager.init(this.getEngine(), this);
		mSceneLetterManager.BuildScenes("Test","Test",mResourceManager);
		
		mSceneMainMenu = new SceneMainMenu();
		mSceneMainMenu.init(this.getEngine(), this);
		mSceneMainMenu.loadScene("ABCMainMenu");
		
		//return mSceneLetterMenuManager.getScene("SceneLetterA");
		return mSceneMainMenu;
	}
	
	public void LaunchLetterScreen(int letterID){
		SceneLetter theNewScene = mSceneLetterManager.getScene("Test");
		theNewScene.resetScene();
		this.mEngine.setScene(mSceneLetterManager.getScene("Test"));
		
	}
	@Override
	public void onBackPressed() {
		if(this.mEngine.getScene() == mSceneLetterMenuManager.getScene("SceneLetterA"))
			super.onBackPressed();
		this.mEngine.setScene(mSceneLetterMenuManager.getScene("SceneLetterA"));
	}
}