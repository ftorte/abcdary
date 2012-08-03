package com.welmo.educational.scenes;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.components.IActionOnSceneListener;
import com.welmo.educational.scenes.description.SceneDescriptor;
import com.welmo.educational.scenes.description.SpriteDescriptor;
import com.welmo.educational.scenes.description.tags.ResTags;
import com.welmo.educational.utility.MLOG;

import android.content.Context;
import android.util.Log;

abstract  public class ManageableScene extends Scene implements IManageableScene, IActionOnSceneListener{
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private static final String 				TAG  = "ManageableScene";
	protected Engine 							mEngine;
	protected Context 							mContext;
	protected SceneDescriptorsManager 			pSDM;
	protected ResourcesManager					pRM;
	
	protected ClickableSprite.IClickLeastener 	mClickLeastener;
	
	//public void loadResources(){};
	
	//protected Context mApplication = null;
	
	ManageableScene(){
		//initialize dummy listeners
		mClickLeastener=new ManageableScene.ClicalbeSpriteLeastener();
		pSDM = SceneDescriptorsManager.getInstance();
		pRM = ResourcesManager.getInstance();
	}
	
	public void loadScene(String SceneName) {
		loadScene(SceneName,this.pRM);
	};

	public void loadScene(String SceneName,ResourcesManager res2) {
		SceneDescriptor scDsc;
		if((scDsc = pSDM.getScene(SceneName))== null)
			throw new NullPointerException("In ManageableScene: the scene: " + SceneName + " don't exists");
	
		for(SpriteDescriptor scObjDsc:scDsc.scObjects){
			switch(scObjDsc.type){
			
			case  STATIC:
				/* Create the background sprite and add it to the scene. */
				final Sprite newSprite = new Sprite(scObjDsc.Parameters[ResTags.R_A_POSITION_X_IDX], scObjDsc.Parameters[ResTags.R_A_POSITION_Y_IDX], 
						scObjDsc.Parameters[ResTags.R_A_WIDTH_IDX ], scObjDsc.Parameters[ResTags.R_A_HEIGHT_IDX], pRM.GetTextureRegion(scObjDsc.resourceName), 
						this.mEngine.getVertexBufferObjectManager());
				this.attachChild(newSprite);
				break;
				
			case CLICKABLE:
				/* Create the clickable sprites elements */
				final ClickableSprite newClicableSprite = new ClickableSprite(scObjDsc.Parameters[ResTags.R_A_POSITION_X_IDX], scObjDsc.Parameters[ResTags.R_A_POSITION_Y_IDX], 
						scObjDsc.Parameters[ResTags.R_A_WIDTH_IDX], scObjDsc.Parameters[ResTags.R_A_HEIGHT_IDX], pRM.GetTextureRegion(scObjDsc.resourceName), 
						this.mEngine.getVertexBufferObjectManager());
				newClicableSprite.setActionOnSceneListener(this);
				newClicableSprite.setID(scObjDsc.ID);
				newClicableSprite.setActionOnSceneListener(this);
				newClicableSprite.setOnClikcNextScene(scObjDsc.nextScene);
				this.attachChild(newClicableSprite);
				this.registerTouchArea(newClicableSprite);

				break;
			default:
				break;
			}
		}
	}

	public void init(Engine theEngine, Context ctx) {
		mEngine = theEngine;
		mContext = ctx;
	}
	public void resetScene(){
		
	}
		
	// ===========================================================
	// Dummy Class implementing IClickLeastener of ClicableSprite object
	// ===========================================================
	private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{

		public void onClick(int ObjectID) {
			if(MLOG.LOG) Log.i(TAG,"In Scene Not Inilialised click Listener");
			throw new NullPointerException("ManageableScene In Scene Not Initialized click Listener");
		}
		/*public void reset() {
			if(MLOG.LOG) Log.i(TAG,"In Scene Not Inilialised click Listener");
			throw new NullPointerException("ManageableScene In Scene Not Initialized click Listener");
		}
		public int getObjectID(){
			if(MLOG.LOG) Log.i(TAG,"In Scene Not Inilialised click Listener");
			throw new NullPointerException("ManageableScene In Scene Not Initialized click Listener");
		}*/
	}

}
