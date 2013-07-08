package com.welmo.monstermemory;

import com.welmo.andengine.ui.SimpleWelmoActivity;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends SimpleWelmoActivity {
	//list of resources file to load while showing the startup file
	private final String[] resourceFiles = {
			"resources/Textures.xml",
	};
	//list of scene file to load
	private final String[] sceneFiles = {
			"scenes/Monster01Scenes.xml",
	};
	//List of sound to load
	//private final String[] listOfSoudsToLoad = {"mon","tue","wed","thu","fri","sat",
	//		"sun","lun","mar","mer","jeu","ven","sam","dim","bravo","sedienanglais","bien"};

	public FullscreenActivity() {
		super();
		//setup the resource for the starting scenes
		setStartResourceDscFiles("resources/StartUpScenesResources.xml");
		setStartSceneDscFile("scenes/StartUpScenes.xml");
		//setup the first scene name
		setFirstSceneName("OpenScene");	
		//setup the first scene name
		setMainSceneName("Monster01");
		
		setCameraWidth(1280);
		setCameraHeight(800);
		
	}
	@Override
	protected void onLoadResourcesDescriptionsInBackGround() {
		this.readResourceDescriptions(resourceFiles);
	}
	@Override
	protected void onLoadScenesDescriptionsInBackGround() {
		this.readScenesDescriptions(sceneFiles); 
	}
}
