package com.welmo.educational.scenes;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.managers.SceneManager;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.components.CompoundSprite;
import com.welmo.educational.scenes.components.IActionOnSceneListener;
import com.welmo.educational.scenes.components.Stick;
import com.welmo.educational.scenes.components.descriptors.BackGroundObjectDescriptor;
import com.welmo.educational.scenes.components.descriptors.SceneDescriptor;
import com.welmo.educational.scenes.components.descriptors.SpriteObjectDescriptor;
import com.welmo.educational.scenes.components.descriptors.TextObjectDescriptor;
import com.welmo.educational.scenes.description.tags.ResTags;
import com.welmo.educational.scenes.events.descriptors.Action;


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
	
	public ManageableScene(){
		//initialize dummy listeners
		// [FT] mClickLeastener=new ManageableScene.ClicalbeSpriteLeastener();
		pSDM = SceneDescriptorsManager.getInstance();
		pRM = ResourcesManager.getInstance();
		mapOfObjects = new HashMap<Integer, IAreaShape>();
	}
	public void loadScene(String SceneName) {
		SceneDescriptor scDsc;
		if((scDsc = pSDM.getScene(SceneName))== null)
			throw new NullPointerException("In ManageableScene: the scene: " + SceneName + " don't exists");
	

		switch(scDsc.thebackGround.type){
		case COLOR:
			this.setBackground(new Background(pRM.getColor(scDsc.thebackGround.color)));
			break;
		default:
			break;
		}
		
		for(SpriteObjectDescriptor scObjDsc:scDsc.scObjects){
			switch(scObjDsc.getType()){	
			case  STATIC:
				this.attachChild(createSprite(scObjDsc));
			case CLICKABLE:
				/* Create the clickable sprite elements */
				final ClickableSprite newClickable = createClickableSprite(scObjDsc);
				for( int i = 0; i < scObjDsc.getTextElements().size(); i++){
					final Text newText = createText(scObjDsc.getTextElements().get(i));
					newClickable.attachChild(newText);
				};
				this.attachChild(newClickable);
				break;

			case COMPOUND_SPRITE:
				final CompoundSprite newEntity = new CompoundSprite(0, 0, 0,0, this.mEngine.getVertexBufferObjectManager());
				newEntity.setID(scObjDsc.ID);
				for( int i = 0; i < scObjDsc.getCoumpoundElements().size(); i++){
					ClickableSprite newComponent = createClickableSprite(scObjDsc.getCoumpoundElements().get(i));
					newEntity.attachComponentChild(newComponent.getX(), newComponent.getY(), newComponent.getWidth(), newComponent.getHeight(),newComponent);
				};
				for( int i = 0; i < scObjDsc.getTextElements().size(); i++){
					final Text newText = createText(scObjDsc.getTextElements().get(i));
				
					newEntity.attachChild(newText);
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
		for(TextObjectDescriptor scObjDsc:scDsc.scText){
			switch(scObjDsc.getType()){	
			case SIMPLE:
				//TODO add to text description Text Optoion with default value
				final Text newText = createText(scObjDsc);
				this.attachChild(newText);
				break;
			default:
				break;
			}
		}
	}

	
	//Create components
	private Sprite createSprite(SpriteObjectDescriptor spDsc){
		final Sprite newSprite = new Sprite(spDsc.getPosition().getX(), spDsc.getPosition().getY(), 
				spDsc.getDimension().getWidth(), spDsc.getDimension().getHeight(), 
				pRM.getTextureRegion(spDsc.getTextureName()), 
				this.mEngine.getVertexBufferObjectManager());
		
		mapOfObjects.put(spDsc.ID, newSprite); 
		return newSprite;
	}
	private Text createText(TextObjectDescriptor spTxtDsc){
		Text newText = new Text(0, 0, pRM.getFont(spTxtDsc.getFontName()), spTxtDsc.getMessage(),
				new TextOptions(HorizontalAlign.CENTER), this.mEngine.getVertexBufferObjectManager());
		//Set Text Color
		newText.setColor(pRM.getColor(spTxtDsc.getColorName()));
		//Set Position
		newText.setPosition(spTxtDsc.getPosition().getX(),spTxtDsc.getPosition().getY());
		
		newText.setScaleCenter(0, 0);
		newText.setScale(2);
		return newText;
		
	}
	private ClickableSprite createClickableSprite(SpriteObjectDescriptor spDsc){

		final ClickableSprite newClicableSprite = new ClickableSprite(spDsc.getPosition().getX(),spDsc.getPosition().getY(), 
				spDsc.getDimension().getWidth(), spDsc.getDimension().getHeight(),
				pRM.getTextureRegion(spDsc.getTextureName()), 
				this.mEngine.getVertexBufferObjectManager());
		newClicableSprite.setID(spDsc.ID);
		newClicableSprite.setActionOnSceneListener(this);
		newClicableSprite.setPDescriptor(spDsc);
		
		/* Setup Rotation*/
		newClicableSprite.setRotationCenter(newClicableSprite.getWidth()/2, newClicableSprite.getHeight()/2);
		newClicableSprite.setRotation(spDsc.getOriantation().getOriantation());
		
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
