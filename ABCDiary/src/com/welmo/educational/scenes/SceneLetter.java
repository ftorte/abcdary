package com.welmo.educational.scenes;

import com.welmo.educational.MenuArrayLettere;
import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.components.ClickableSprite;

public class SceneLetter extends ManageableScene {


	// ===========================================================
	// Constants	
	// ===========================================================
	//Log & Debug & trace
	private static final String TAG = "SceneLetter";
	private int miItemClicked=-1;
	
	// ===========================================================
	// Temporary scene descriptor to build schene
	@Override
	public void loadScene(String SceneName, ResourcesManager res) {
		super.loadScene(SceneName, res);
	}

	// ===========================================================
	// Methods - Initialization and menu preparation
	// ===========================================================

	//Constructor
	public SceneLetter(MenuArrayLettere pApplication){
		super();
		mClickLeastener=new SceneLetter.ClicalbeSpriteLeastener();
		//[FT] mApplication = pApplication;
	}

	//Constructor
	public SceneLetter(){
		super();
		mClickLeastener=new SceneLetter.ClicalbeSpriteLeastener();
	}

	// ===========================================================
	// Inner private classes to handle on click event
	// ===========================================================
	private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{

		@Override
		public void onClick(int ObjectID) {
			miItemClicked = ObjectID;
		}

	}

	@Override
	public void onActionChangeScene(int actionType, String nextScene) {
		// TODO Auto-generated method stub
		
	}
}
