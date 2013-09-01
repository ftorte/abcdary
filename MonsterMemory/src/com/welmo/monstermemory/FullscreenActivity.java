package com.welmo.monstermemory;
import org.andengine.entity.scene.Scene;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import com.welmo.andengine.scenes.ManageableScene;
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
			"scenes/MainMenu.xml",
			"scenes/MenuMemories.xml",
			"scenes/MenuPuzzles.xml",
			"scenes/MenuColoring.xml",
			"scenes/Monster01Scenes.xml",
			"scenes/PuzzleScenes.xml",
			"scenes/ColoringMonster.xml"
	};

	public FullscreenActivity() {
		super();
		//setup the resource for the starting scenes
		setStartResourceDscFiles("resources/StartUpScenesResources.xml");
		setStartSceneDscFile("scenes/StartUpScenes.xml");
		//setup the first scene name
		setFirstSceneName("OpenScene");	
		//setup the first scene name
		setMainSceneName("MainMenu");
		
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
	
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			ManageableScene theCurrentScene = (ManageableScene) mEngine.getScene();
			Scene newScene = mSceneManager.getScene(theCurrentScene.getFatherScene());	
			if(newScene != null){
				this.onChangeScene(theCurrentScene.getFatherScene());
				return true;
			}
			else
				return super.onKeyDown(pKeyCode, pEvent);
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
}
