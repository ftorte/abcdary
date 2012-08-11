package com.welmo.educational.scenes;

import com.welmo.educational.MenuArrayLettere;
import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.description.Events.Action.ActionType;

public class SceneLetter extends ManageableScene {


	// ===========================================================
	// Constants	
	// ===========================================================
	//Log & Debug & trace
	private static final String TAG = "SceneLetter";
	private int miItemClicked=-1;
	
	// ===========================================================
	// Methods - Initialization and menu preparation
	// ===========================================================

	//Constructor
	public SceneLetter(MenuArrayLettere pApplication){
		super();
		// [FT] mClickLeastener=new SceneLetter.ClicalbeSpriteLeastener();
		//[FT] mApplication = pApplication;
	}

	//Constructor
	public SceneLetter(){
		super();
		// [FT] mClickLeastener=new SceneLetter.ClicalbeSpriteLeastener();
	}

	// ===========================================================
	// Inner private classes to handle on click event
	// ===========================================================
	// [FT] private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{
	// [FT]
	// [FT]	@Override
	// [FT]	public void onClick(int ObjectID) {
	// [FT]		miItemClicked = ObjectID;
	// [FT]	}
	// [FT]
	// [FT]}

}
