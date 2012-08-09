package com.welmo.educational.scenes;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.managers.SceneManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.components.CompoundSprite;
import com.welmo.educational.scenes.components.IActionOnSceneListener;
import com.welmo.educational.scenes.components.Stick;
import com.welmo.educational.scenes.description.SceneDescriptor;
import com.welmo.educational.scenes.description.SpriteDescriptor;
import com.welmo.educational.scenes.description.Events.Action;
import com.welmo.educational.scenes.description.tags.ResTags;


import android.content.Context;


public class ManageableScene extends Scene implements IManageableScene, IActionOnSceneListener{
	//--------------------------------------------------------
	// Variables
	//--------------------------------------------------------
	private static final String 				TAG  = "ManageableScene";
	protected Engine 							mEngine;
	protected Context 							mContext;
	protected SceneDescriptorsManager 			pSDM;
	protected ResourcesManager					pRM;
	protected SceneManager<?>					pSM;
	protected HashMap<Integer, IAreaShape> 	mapOfObjects;
	
	// [FT] protected ClickableSprite.IClickLeastener 	mClickLeastener;
	
	//public void loadResources(){};
	
	//protected Context mApplication = null;
	
	public ManageableScene(){
		//initialize dummy listeners
		// [FT] mClickLeastener=new ManageableScene.ClicalbeSpriteLeastener();
		pSDM = SceneDescriptorsManager.getInstance();
		pRM = ResourcesManager.getInstance();
		mapOfObjects = new HashMap<Integer, IAreaShape>();
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
				this.attachChild(createSprite(scObjDsc));
			case CLICKABLE:
				/* Create the clickable sprite elements */
				this.attachChild(createClickableSprite(scObjDsc));
				break;

			case COMPOUND_SPRITE:
				final CompoundSprite newEntity = new CompoundSprite(0, 0, 0,0, this.mEngine.getVertexBufferObjectManager());
				newEntity.setID(scObjDsc.ID);
				for( int i = 0; i < scObjDsc.coumpoundElements.size(); i++){
					ClickableSprite newComponent = createClickableSprite(scObjDsc.coumpoundElements.get(i));
					newEntity.attachComponentChild(newComponent.getX(), newComponent.getY(), newComponent.getWidth(), newComponent.getHeight(),newComponent);
				};
				this.attachChild(newEntity);
				newEntity.setActionOnSceneListener(this);
				newEntity.setPDescriptor(scObjDsc);
				this.registerTouchArea(newEntity);
				mapOfObjects.put(scObjDsc.ID, newEntity); 
				break;
			default:
				break;
			}
		}
	}

	
	//Create components
	private Sprite createSprite(SpriteDescriptor spDsc){
		final Sprite newSprite = new Sprite(spDsc.Parameters[ResTags.R_A_POSITION_X_IDX], spDsc.Parameters[ResTags.R_A_POSITION_Y_IDX], 
				spDsc.Parameters[ResTags.R_A_WIDTH_IDX ], spDsc.Parameters[ResTags.R_A_HEIGHT_IDX], pRM.GetTextureRegion(spDsc.resourceName), 
				this.mEngine.getVertexBufferObjectManager());
		mapOfObjects.put(spDsc.ID, newSprite); 
		return newSprite;
	}
	private ClickableSprite createClickableSprite(SpriteDescriptor spDsc){

		final ClickableSprite newClicableSprite = new ClickableSprite(spDsc.Parameters[ResTags.R_A_POSITION_X_IDX], spDsc.Parameters[ResTags.R_A_POSITION_Y_IDX], 
				spDsc.Parameters[ResTags.R_A_WIDTH_IDX], spDsc.Parameters[ResTags.R_A_HEIGHT_IDX], pRM.GetTextureRegion(spDsc.resourceName), 
				this.mEngine.getVertexBufferObjectManager());
		newClicableSprite.setID(spDsc.ID);
		newClicableSprite.setActionOnSceneListener(this);
		newClicableSprite.setPDescriptor(spDsc);
		this.registerTouchArea(newClicableSprite);
		mapOfObjects.put(spDsc.ID, newClicableSprite); 
		return newClicableSprite;
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
	
	//Default Implementation of IActionOnSceneListener interface
	@Override
	public boolean onActionChangeScene(String nextScene) {
		ManageableScene psc = pSM.getScene(nextScene);
		if(psc != null){
			mEngine.setScene(pSM.getScene(nextScene));
			return true;
		}
		else
			return false;
	}

	public void setSceneManager(SceneManager<?> sceneManager) {
		this.pSM = sceneManager;
	}

	@Override
	public void onStick(IAreaShape currentShapeToStick,
			Action stickActionDescription) {
		IAreaShape shapeToStickWith = mapOfObjects.get(stickActionDescription.stick_with);
		// TO DO calculation distance must be from border and not from center & value must be a parameter
		if (shapeToStickWith != null){
			if(Stick.isStickOn(currentShapeToStick, shapeToStickWith, 150)){
				switch(stickActionDescription.stickMode){
				case STICK_LEFTH: Stick.lefth(currentShapeToStick, shapeToStickWith);break;
				case STICK_RIGHT: Stick.right(currentShapeToStick, shapeToStickWith);break;
				case STICK_UP: Stick.up(currentShapeToStick, shapeToStickWith);break;
				case STICK_BOTTOM: Stick.bottom(currentShapeToStick, shapeToStickWith);break;
				case STICK_UP_LEFTH: Stick.upLefth(currentShapeToStick, shapeToStickWith);break;
				case STICK_UP_RIGHT: Stick.upRight(currentShapeToStick, shapeToStickWith);break;
				case STICK_BOTTOM_LEFTH: Stick.bottomLefth(currentShapeToStick, shapeToStickWith);break;
				case STICK_BOTTOM_RIGHT: Stick.bottomRight(currentShapeToStick, shapeToStickWith);break;
				}
			}
		}
	}
}
