package com.welmo.educational.scenes;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import com.welmo.educational.managers.ResourceDescriptorsManager;
import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.description.SceneDescriptor;
import com.welmo.educational.scenes.description.SceneObjectDescriptor;
import com.welmo.educational.scenes.description.XMLTags;
import com.welmo.educational.utility.MLOG;

import android.content.Context;
import android.util.Log;

abstract  public class ManageableScene extends Scene {
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private static final String 				TAG  = "ManageableScene";
	protected Engine 							mEngine;
	protected Context 							mContext;
	protected ResourceDescriptorsManager 		pResDescMgr;
	protected ClickableSprite.IClickLeastener 	mClickLeastener;
	
	public void loadResources(){};
	
	protected Context mApplication = null;
	
	ManageableScene(){
		//initialize dummy listeners
		mClickLeastener=new ManageableScene.ClicalbeSpriteLeastener();
		IntiScene();
	}
	
	private void IntiScene(){
		mClickLeastener =  new ClicalbeSpriteLeastener();
		pResDescMgr = ResourceDescriptorsManager.getInstance();
	}
	
	public void loadScene(String SceneName) {};

	public void loadScene(String SceneName,ResourcesManager res) {
		SceneDescriptor scDsc;
		if((scDsc = pResDescMgr.getScene(SceneName))== null)
			throw new NullPointerException("In ManageableScene: the scene: " + SceneName + "don't exists");
	
		for(SceneObjectDescriptor scObjDsc:scDsc.scObjects){
			switch(scObjDsc.type){
			
			case  SPRITE:
				/* Create the background sprite and add it to the scene. */
				final Sprite newSprite = new Sprite(scObjDsc.Parameters[XMLTags.POSITION_X_IDX], scObjDsc.Parameters[XMLTags.POSITION_Y_IDX], 
						scObjDsc.Parameters[XMLTags.WIDTH_IDX], scObjDsc.Parameters[XMLTags.HEIGHT_IDX], res.GetTexture(scObjDsc.resourceName), 
						this.mEngine.getVertexBufferObjectManager());
				this.attachChild(newSprite);
				break;
				
			case CLICKABLE_SPRITE:
				/* Create the clickable sprites elements */
				final ClickableSprite newClicableSprite = new ClickableSprite(scObjDsc.Parameters[XMLTags.POSITION_X_IDX], scObjDsc.Parameters[XMLTags.POSITION_Y_IDX], 
						scObjDsc.Parameters[XMLTags.WIDTH_IDX], scObjDsc.Parameters[XMLTags.HEIGHT_IDX], res.GetTexture(scObjDsc.resourceName), 
						this.mEngine.getVertexBufferObjectManager());
				newClicableSprite.SetClickListener(mClickLeastener);
				newClicableSprite.setID(scObjDsc.ID);
				this.attachChild(newClicableSprite);
				this.registerTouchArea(newClicableSprite);

				break;
			}
		}
	}

	public void init(Engine theEngine, Context ctx) {
		// TODO Auto-generated method stub
		mEngine = theEngine;
		mContext = ctx;
	}
	public void resetScene(){
		
	}
		
	// ===========================================================
	// Dummy ClicalbeSpriteLeastener
	// ===========================================================
	private class ClicalbeSpriteLeastener implements ClickableSprite.IClickLeastener{

		public void onClick(int ObjectID) {
			if(MLOG.LOG) Log.i(TAG,"In Scene Not Inilialised click Listener");
			throw new NullPointerException("ManageableScene In Scene Not Inilialised click Listenee");
		}
		public void reset() {
			if(MLOG.LOG) Log.i(TAG,"In Scene Not Inilialised click Listener");
			throw new NullPointerException("ManageableScene In Scene Not Inilialised click Listenee");
		}
		public int getObjectID(){
			if(MLOG.LOG) Log.i(TAG,"In Scene Not Inilialised click Listener");
			throw new NullPointerException("ManageableScene In Scene Not Inilialised click Listenee");
		}
	}

}
