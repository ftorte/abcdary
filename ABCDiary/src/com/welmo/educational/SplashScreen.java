package com.welmo.educational;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.util.color.Color;

import android.view.KeyEvent;

import com.welmo.andengine.managers.ResourcesManager;
import com.welmo.andengine.scenes.MemoryScene;
import com.welmo.andengine.scenes.descriptors.components.GameLevel;
import com.welmo.andengine.ui.SimpleWelmoActivity;


public class SplashScreen extends SimpleWelmoActivity implements IOnMenuItemClickListener  {

	private final String[] strFileResouce  	= {"resources/StartUpScenesResources.xml"};
	private final String[] strFileScenes  	= {"scenes/StartUpScenes.xml"};
	
	
	public SplashScreen() {
		super();
		this.mMainSceneName = new String("ABCMainMenu");
		mStartResourceDscFile = strFileResouce;
		this.mStartSceneDscFile = strFileScenes;
		this.mFirstSceneName = new String("LaunchScene");
	}
	protected MenuScene 			mStaticMenuScene, mPouUpMenuScene;
	// ===========================================================
	private static final int 		MENU_EASY = 0;
	private static final int 		MENU_MEDIUM = MENU_EASY + 1;
	private static final int 		MENU_DIFFICULT = MENU_MEDIUM +1;
	private static final int 		MENU_HARD = MENU_DIFFICULT +1;
	// ===========================================================
	
	String[] resourceFiles = { "resources/ABCDiaryResources.xml"};
	
	String[] sceneFiles = { "scenes/ABCDiaryMenuScene.xml","scenes/ABCDiaryScenes.xml", 
			"scenes/LettersScenes.xml","scenes/MenuOfLetters.xml","scenes/MenuOfLetter2.xml",
			"scenes/MemoryPokerTris.xml","scenes/MemoryDays.xml"};
	
	String[] listOfScenesToLoad = {"ABCMainMenu"};
	
	String[] listOfTexturesToLoad = {"MenuItemLettere","MenuArrayLetterA","MenuBackGround",
			"BcgA","Big-A","flower_pink","flower_blue_small","CardWiteBCG"};

	String[] listOfTiledTexturesToLoad = {"TheBee_flightRight","TheCardRegion","The30DeckCardRegion"};
	
	String[] listOfTMusicsToLoad = {};
	
	String[] listOfSoudsToLoad = {"mon","tue","wed","thu","fri","sat",
			"sun","lun","mar","mer","jeu","ven","sam","dim","bravo","sedienanglais","bien"};
	
	//String[] listOfSoudsToLoad = {};
	
	@Override
	protected void onLoadResourcesDescriptionsInBackGround() {
		this.readResourceDescriptions(resourceFiles);
	}
	@Override
	protected void onLoadScenesDescriptionsInBackGround() {
		this.readScenesDescriptions(sceneFiles); 
	}
	@Override
	protected void onLoadScenesInBackGound() {
		super.onLoadScenesInBackGound();
		createPouUpMenu();
		this.loadScenes(listOfScenesToLoad);
	}	
	@Override
	protected void onLoadResourcesInBackGround() {
		this.loadTextures(listOfTexturesToLoad);
		this.loadTiledTextures(listOfTiledTexturesToLoad);
		this.loadMusics(listOfTMusicsToLoad);
		this.loadSounds(listOfSoudsToLoad);
		super.onLoadResourcesInBackGround();
	}
	@Override
	public synchronized void onCustomResumeGame(){
		this.loadSounds(listOfSoudsToLoad);
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
}