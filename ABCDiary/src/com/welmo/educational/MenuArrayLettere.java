package com.welmo.educational;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
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
import com.welmo.educational.scenes.SceneMenuArray;

import android.content.Intent;

public class MenuArrayLettere extends SimpleBaseGameActivity {
	

	// ===========================================================
	// Constants
	// ===========================================================

	public static final float CAMERA_WIDTH = 800;
	public static final float CAMERA_HEIGHT = 480;
	public static final float fLeftMargin = 0.05f;
	public static final float fRightMargin = 0.05f;
	public static final float fColSpace = 0.01f;
	public static final float fTopMargin = 0.1f;
	public static final float fBottomtMargin = 0.1f;
	public static final float fRowSpace = 0.1f;
	public static final int nNbRow = 3;
	public static final int nNbCol = 7;		
	
	public static final	String INTENT_KEY_PARAM_A = "LetterID";
	
	// ===========================================================
	// Fields
	// ===========================================================

	SceneManager<SceneMenuArray> mSceneManager;
	SceneManager<SceneLetter> mSceneLetterManager;
	ResourcesManager mResourceManager;

	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		
		mResourceManager = new ResourcesManager();
		mResourceManager.init(this.getEngine(), this);

		//LoadResources for letter A
		//mResourceManager.LoadResources("SceneLetterA");

	}

	@Override
	protected Scene onCreateScene() {
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		mSceneManager = new SceneManager<SceneMenuArray>(SceneMenuArray.class);
		mSceneManager.init(this.getEngine(), this);
		mSceneManager.BuildScenes("SceneLetterA","SceneLetterA",mResourceManager);

		mSceneLetterManager = new SceneManager<SceneLetter>(SceneLetter.class);
		mSceneLetterManager.init(this.getEngine(), this);
		mSceneLetterManager.BuildScenes("Test","Test",mResourceManager);
		
		return mSceneManager.getScene("SceneLetterA");
	}
	
	public void LaunchLetterScreen(int letterID){
		SceneLetter theNewScene = mSceneLetterManager.getScene("Test");
		theNewScene.resetScene();
		this.mEngine.setScene(mSceneLetterManager.getScene("Test"));
		
	}
	@Override
	public void onBackPressed() {
		if(this.mEngine.getScene() == mSceneManager.getScene("SceneLetterA"))
			super.onBackPressed();
		this.mEngine.setScene(mSceneManager.getScene("SceneLetterA"));
	}
}