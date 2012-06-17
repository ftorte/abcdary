package com.welmo.educational;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.utility.ParserXMLSceneDescriptor;

import android.content.Intent;
import android.util.Log;

public class SplashScreen extends SimpleBaseGameActivity  {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================	
	//Splash Screen Variables
	private static final int SPLASH_DURATION = 3; 	// Duration in seconds
	private static final float SPLASH_SCALE_FROM = 0.6f;	//Scale modifier
	//private static final String IMAGE = "logo.png";
	private static final Class<MenuArrayLettere> FOLLOWING_ACTIVITY = MenuArrayLettere.class;	


	ResourcesManager mResourceManager;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	public void onCreateResources() {		
		mResourceManager = new ResourcesManager();
		mResourceManager.init(this.getEngine(), this);
		//LoadResources
		mResourceManager.LoadResources("SceneSplashScreen");
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();

		ITextureRegion spashRegion = mResourceManager.GetTexture("SplashScreen");
		
		/* Center the splash on the camera. */
		final float centerX = (CAMERA_WIDTH - spashRegion.getWidth()) / 2;
		final float centerY = (CAMERA_HEIGHT - spashRegion.getHeight()) / 2;

		/* Create the sprite and add it to the scene. */
		final Sprite splashImage = new Sprite(centerX, centerY, spashRegion, this.getVertexBufferObjectManager());
				
		SequenceEntityModifier animation = new SequenceEntityModifier(
				new DelayModifier(0.5f),
				new ParallelEntityModifier(
						new ScaleModifier(SPLASH_DURATION, SPLASH_SCALE_FROM,1),
						new AlphaModifier(SPLASH_DURATION, 0,1)
						),
						new DelayModifier(0.5f)


				);
		
		animation.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				SplashScreen.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//Toast.makeText(SplashScreenActivity.this, "Sequence started.", Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				SplashScreen.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//	Toast.makeText(SplashScreenActivity.this, "Sequence finished.", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SplashScreen.this, FOLLOWING_ACTIVITY);
						startActivity(intent);
						//finish();
					}
				});
			}
		});		
		splashImage.setAlpha(0);
		splashImage.setScale(SPLASH_SCALE_FROM);
		splashImage.registerEntityModifier(animation);		
		scene.attachChild(splashImage);

		_parseXml();
		
		/////////////////////////////////////
		return scene;
	}


	// ===========================================================
	// Methods
	// ===========================================================
	private void _parseXml() { 
		// sax stuff 
		try { 
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser(); 
			XMLReader xr = sp.getXMLReader(); 

			ParserXMLSceneDescriptor dataHandler = new ParserXMLSceneDescriptor(); 
			xr.setContentHandler(dataHandler); 

			xr.parse(new InputSource(this.getAssets().open("scenes/resources.xml"))); 
			
		} catch(ParserConfigurationException pce) { 
			Log.e("SAX XML", "sax parse error", pce); 
		} catch(SAXException se) { 
			Log.e("SAX XML", "sax error", se); 
		} catch(IOException ioe) { 
			Log.e("SAX XML", "sax parse io error", ioe); 
		} 
	} 
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}