package com.welmo.educational;

import com.welmo.andengine.ui.SimpleWelmoActivity;

/*
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.svg.opengl.texture.atlas.bitmap.SVGBitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.welmo.andengine.managers.ResourcesManager;
import com.welmo.andengine.managers.SceneManager;
import com.welmo.andengine.resources.descriptors.components.ParserXMLResourcesDescriptor;
import com.welmo.andengine.scenes.IManageableScene;
import com.welmo.andengine.scenes.MemoryScene;
import com.welmo.andengine.scenes.descriptors.components.GameLevel;
import com.welmo.andengine.scenes.descriptors.components.ParserXMLSceneDescriptor;
import com.welmo.andengine.ui.SimpleWelmoActivity;
import com.welmo.andengine.utility.AsyncResourcesScenesLoader;
import com.welmo.andengine.utility.IAsyncCallBack;

import android.util.Log;
import android.view.KeyEvent;
*/

public class SplashScreen extends SimpleWelmoActivity {
	/*
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = "SplashScreen";

	private static final int 		CAMERA_WIDTH = 800;
	private static final int 		CAMERA_HEIGHT = 480;
	private static final int 		MENU_EASY = 0;
	private static final int 		MENU_MEDIUM = MENU_EASY + 1;
	private static final int 		MENU_DIFFICULT = MENU_MEDIUM +1;
	private static final int 		MENU_HARD = MENU_DIFFICULT +1;
	
	// ===========================================================
	// Fields
	// ===========================================================	
	//Splash Screen Variables
	private static final int SPLASH_DURATION = 3; 	// Duration in seconds
	private static final float SPLASH_SCALE_FROM = 0.6f;	//Scale modifier
	//private static final String IMAGE = "logo.png";
	//private static final Class<ABCDaryApplication> FOLLOWING_ACTIVITY = ABCDaryApplication.class;	

	BitmapTextureAtlas 	pTextureAtals;
	ITextureRegion		pTextureRegion;
	
	// ===========================================================
	// Fields
	// ===========================================================
	//TODO to transform this in configurable parameter o scene
	protected MenuScene mStaticMenuScene, mPouUpMenuScene;
	private boolean bPopupDisplayed = false;
	SceneManager 					mSceneManager;
	ResourcesManager 				mResourceManager;
	Camera 							mCamera;

	final String FONTHBASEPATH = "font/";
	final String TEXTUREBASEPATH = "gfx/";

	
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
		
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

		//Enable audio option
		mCamera.setZClippingPlanes(-1000, 1000);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;

	}

	@Override
	public void onCreateResources() {	
		SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath(TEXTUREBASEPATH);
		pTextureAtals = new BitmapTextureAtlas(this.mEngine.getTextureManager(),1024, 1024, TextureOptions.BILINEAR);
		pTextureRegion = SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtals, this,
				"colorful_animals_scalable_vector_graphics_svg_inkscape_adobe_illustrator_clip_art_clipart.svg", 800,480,0,0);

		this.mEngine.getTextureManager().loadTexture(pTextureAtals);
		
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();		
		/* Center the splash on the camera. 
		final float centerX = (CAMERA_WIDTH - pTextureRegion.getWidth()) / 2;
		final float centerY = (CAMERA_HEIGHT - pTextureRegion.getHeight()) / 2;

		/* Create the sprite and add it to the scene. 
		final Sprite splashImage = new Sprite(centerX, centerY, pTextureRegion, this.getVertexBufferObjectManager());
		
		/* create background 
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
				/*SplashScreen.this.runOnUiThread(new Runnable() {
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

		IAsyncCallBack callback = new IAsyncCallBack() {

            @Override
            public void workToDo() {
        		readSceneAndResourcesDescriptors(); 
        		onCreateResourcesBis();
        		onCreateSceneBis();
            }
            @Override
            public void onComplete() {
            	mEngine.setScene((Scene)mSceneManager.getScene("ABCMainMenu"));
            }
        };

        AsyncResourcesScenesLoader loadingTask = new AsyncResourcesScenesLoader();
        loadingTask.setupTaskToLoadResource(callback);
        loadingTask.start();
	
        
		/////////////////////////////////////
		return scene;
	}


	// ===========================================================
	// Methods
	// ===========================================================
	private void readSceneAndResourcesDescriptors() { 
			String[] resourceFiles = { "resources/ABCDiaryResources.xml"};
			String[] sceneFiles = { "scenes/ABCDiaryMenuScene.xml","scenes/ABCDiaryScenes.xml", 
					"scenes/LettersScenes.xml","scenes/MenuOfLetters.xml","scenes/MenuOfLetter2.xml",
					"scenes/MemoryPokerTris.xml","scenes/MemoryDays.xml"};
			readResourceDescriptions(resourceFiles);
			readScenesDescriptions(sceneFiles); 
	} 
	
	private void readResourceDescriptions(String[] filesNames){
		// sax stuff 
		try { 
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser(); 
			XMLReader xr = sp.getXMLReader(); 
			
			//parse resources descriptions
			ParserXMLResourcesDescriptor resourceDescriptionHandler = new ParserXMLResourcesDescriptor(this); 
			xr.setContentHandler(resourceDescriptionHandler); 
			for (String filename:filesNames ){
				xr.parse(new InputSource(this.getAssets().open(filename))); 
			}
			
		} catch(ParserConfigurationException pce) { 
			Log.e("SAX XML", "sax parse error", pce); 
		} catch(SAXException se) { 
			Log.e("SAX XML", "sax error", se); 
		} catch(IOException ioe) { 
			Log.e("SAX XML", "sax parse io error", ioe); 
		} 
	}
	
	private void readScenesDescriptions(String[] filesNames){
		// sax stuff 
		try { 
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser(); 
			XMLReader xr = sp.getXMLReader(); 
			
			//parse resources descriptions
			ParserXMLSceneDescriptor resourceDescriptionHandler = new ParserXMLSceneDescriptor(this); 
			xr.setContentHandler(resourceDescriptionHandler); 
			for (String filename:filesNames ){
				xr.parse(new InputSource(this.getAssets().open(filename))); 
			}
			
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
	
	protected void onCreateResourcesBis() {
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
		mResourceManager.getSound("mon");
		mResourceManager.getSound("tue");
		mResourceManager.getSound("wed");
		mResourceManager.getSound("thu");
		mResourceManager.getSound("fri");
		mResourceManager.getSound("sat");
		mResourceManager.getSound("sun");
		mResourceManager.getSound("lun");
		mResourceManager.getSound("mar");
		mResourceManager.getSound("mer");
		mResourceManager.getSound("jeu");
		mResourceManager.getSound("ven");
		mResourceManager.getSound("sam");
		mResourceManager.getSound("dim");
		mResourceManager.getSound("bravo");
		mResourceManager.getSound("sedienanglais");
		mResourceManager.getSound("bien");
		this.mEngine.onReloadResources();
	}
	protected void onCreateSceneBis() {
		createPouUpMenu();
		mSceneManager = new SceneManager(this);
		this.mEngine.registerUpdateHandler(new FPSLogger());
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
		mSceneManager.BuildScenes("MemoryDays",this);
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
					// Remove the menu and reset it. 
					this.mPouUpMenuScene.back();
				} else {
					// Attach the menu. 
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
			memory.resetScene(GameLevel.EASY);	
			memory.clearChildScene();
			return true;
		case MENU_MEDIUM: 
			memory.resetScene(GameLevel.MEDIUM);
			memory.clearChildScene();
			return true;
		case MENU_DIFFICULT: 
			memory.resetScene(GameLevel.DIFFICULT);
			memory.clearChildScene();
			return true;
		case MENU_HARD: 
			memory.resetScene(GameLevel.HARD);
			memory.clearChildScene();
			return true;
		}
		memory.clearChildScene();
		return false;
	}

	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		ResourcesManager mgr = ResourcesManager.getInstance();
		mgr.ResumeGame();
	}

	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		super.onPauseGame();
		ResourcesManager mgr = ResourcesManager.getInstance();
		mgr.PauseGame();
	}*/
	
}