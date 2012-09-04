package com.welmo.educational.scenes;

import java.util.HashMap;

import org.andengine.engine.Engine;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.CardinalSplineMoveModifier.CardinalSplineMoveModifierConfig;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;

import com.welmo.educational.managers.ResourcesManager;
import com.welmo.educational.managers.SceneDescriptorsManager;
import com.welmo.educational.managers.SceneManager;
import com.welmo.educational.scenes.components.CardinalSplineMoveAndRotateModifier;
import com.welmo.educational.scenes.components.ClickableSprite;
import com.welmo.educational.scenes.components.CompoundSprite;
import com.welmo.educational.scenes.components.IActionOnSceneListener;
import com.welmo.educational.scenes.components.Stick;
import com.welmo.educational.scenes.components.TextComponent;
import com.welmo.educational.scenes.components.descriptors.BackGroundObjectDescriptor;
import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor;
import com.welmo.educational.scenes.components.descriptors.SceneDescriptor;
import com.welmo.educational.scenes.components.descriptors.SpriteObjectDescriptor;
import com.welmo.educational.scenes.components.descriptors.TextObjectDescriptor;
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
	protected HashMap<Integer, IAreaShape> 		mapOfObjects;
	
	// ===========================================================
	// Constructor
	// ===========================================================
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

		for(BasicObjectDescriptor scObjDsc:scDsc.pChild){
			if(scObjDsc instanceof BackGroundObjectDescriptor){
				BackGroundObjectDescriptor pBkgDsc = (BackGroundObjectDescriptor) scObjDsc;
				switch(pBkgDsc.type){
				case COLOR:
					this.setBackground(new Background(pRM.getColor(pBkgDsc.color)));
					break;
				case SPRITE:
					SpriteObjectDescriptor pSDsc = (SpriteObjectDescriptor)pBkgDsc.pChild.getFirst();
					final Sprite spriteBKG = new Sprite(0, 0, pBkgDsc.getDimension().getWidth(), pBkgDsc.getDimension().getHeight(), 
							pRM.getTextureRegion(pSDsc.getTextureName()), 
							this.mEngine.getVertexBufferObjectManager());
					this.setBackground(new SpriteBackground(spriteBKG));

					break;
				default:
					break;
				}
			}
			if(scObjDsc instanceof SpriteObjectDescriptor){
				SpriteObjectDescriptor pSprtDsc = (SpriteObjectDescriptor)scObjDsc;
				switch(pSprtDsc.getType()){	
				case  STATIC:
					this.attachChild(createSprite(pSprtDsc));
				case CLICKABLE:
					/* Create the clickable sprite elements */
					final ClickableSprite newClickable = createClickableSprite(pSprtDsc);
					for( int i = 0; i < pSprtDsc.getTextElements().size(); i++){
						final TextComponent newText = createText(pSprtDsc.getTextElements().get(i));
						newText.align(pSprtDsc.getTextElements().get(i), newClickable);
						newClickable.attachChild(newText);
					};
					this.attachChild(newClickable);
					break;

				case COMPOUND_SPRITE:
					final CompoundSprite newEntity = new CompoundSprite(0, 0, 0,0, this.mEngine.getVertexBufferObjectManager());
					newEntity.setID(scObjDsc.getID());
					for( int i = 0; i < pSprtDsc.getCoumpoundElements().size(); i++){
						ClickableSprite newComponent = createClickableSprite(pSprtDsc.getCoumpoundElements().get(i));
						newEntity.attachComponentChild(newComponent.getX(), newComponent.getY(), newComponent.getWidth(), newComponent.getHeight(),newComponent);
					};
					for( int i = 0; i < pSprtDsc.getTextElements().size(); i++){
						final TextComponent newText = createText(pSprtDsc.getTextElements().get(i));
						newText.align(pSprtDsc.getTextElements().get(i), newEntity);
						newEntity.attachChild(newText);
					};
					this.attachChild(newEntity);
					newEntity.setActionOnSceneListener(this);
					newEntity.setPDescriptor(scObjDsc);
					this.registerTouchArea(newEntity);
					mapOfObjects.put(scObjDsc.getID(), newEntity); 
					break;
				case ANIMATED:
					/* Create the animated sprite elements */
					final AnimatedSprite newAnimatedSprite = createAnimatedSprite(pSprtDsc);
					this.attachChild(newAnimatedSprite);
					break;
				default:
					break;
				}
			}
			if(scObjDsc instanceof TextObjectDescriptor){
				TextObjectDescriptor pTxtDsc = (TextObjectDescriptor)scObjDsc;
				switch(pTxtDsc.getType()){	
				case SIMPLE:
					//TODO add to text description Text Optoion with default value
					final TextComponent newText = createText(pTxtDsc);
					this.attachChild(newText);
					break;
				default:
					break;
				}
			}
		}
	}
	//Create components
	private Sprite createSprite(SpriteObjectDescriptor spDsc){
		final Sprite newSprite = new Sprite(spDsc.getPosition().getX(), spDsc.getPosition().getY(), 
				spDsc.getDimension().getWidth(), spDsc.getDimension().getHeight(), 
				pRM.getTextureRegion(spDsc.getTextureName()), 
				this.mEngine.getVertexBufferObjectManager());
		
		mapOfObjects.put(spDsc.getID(), newSprite); 
		return newSprite;
	}
	private TextComponent createText(TextObjectDescriptor spTxtDsc){
		TextComponent newText = new TextComponent(0, 0, pRM.getFont(spTxtDsc.getFontName()), spTxtDsc.getMessage(),
				new TextOptions(HorizontalAlign.CENTER), this.mEngine.getVertexBufferObjectManager());
		
		newText.configure(spTxtDsc);
		return newText;
	}
	private ClickableSprite createClickableSprite(SpriteObjectDescriptor spDsc){
		
		final ClickableSprite newClicableSprite = new ClickableSprite(spDsc.getPosition().getX(),spDsc.getPosition().getY(), 
				spDsc.getDimension().getWidth(), spDsc.getDimension().getHeight(),
				this.pRM.getTextureRegion(spDsc.getTextureName()), 
				this.mEngine.getVertexBufferObjectManager());
		
		//setup Id
		newClicableSprite.setID(spDsc.getID());
		newClicableSprite.setPDescriptor(spDsc);
		
		/* Setup Rotation*/
		newClicableSprite.setRotationCenter(newClicableSprite.getWidth()/2, newClicableSprite.getHeight()/2);
		newClicableSprite.setRotation(spDsc.getOriantation().getOriantation());
			
		this.registerTouchArea(newClicableSprite);
		mapOfObjects.put(spDsc.getID(), newClicableSprite); 
		
		newClicableSprite.setActionOnSceneListener(this);
		return newClicableSprite;
	}
	private AnimatedSprite createAnimatedSprite(SpriteObjectDescriptor spDsc){
		final AnimatedSprite animatedObject = new AnimatedSprite(100,100, 
				pRM.getTiledTexture(spDsc.getTextureName()), 
				this.mEngine.getVertexBufferObjectManager());
		
		final Path path = new Path(5).to(10, 10).to(10, 480 - 74).to(800 - 58, 480 - 74).to(800 - 58, 10).to(10, 10);

		/* Add the proper animation when a waypoint of the path is passed. */
		CardinalSplineMoveModifierConfig splineModifierConfig= new CardinalSplineMoveModifierConfig(8,0.4f);
		
		splineModifierConfig.setControlPoint(0, 233f, 88f);
		splineModifierConfig.setControlPoint(1, 182f, 238f);
		splineModifierConfig.setControlPoint(2, 68f,  194f);
		splineModifierConfig.setControlPoint(3, 109f, 376f);
		splineModifierConfig.setControlPoint(4, 290f, 357f);
		splineModifierConfig.setControlPoint(5, 346f, 141f);
		splineModifierConfig.setControlPoint(6, 259f, 154f);
		splineModifierConfig.setControlPoint(7, 233f, 88f);
		
		CardinalSplineMoveAndRotateModifier splineModifier = new CardinalSplineMoveAndRotateModifier(10,splineModifierConfig);
		
		splineModifier.pEngine = this.mEngine;
		splineModifier.pScene = this;
		
		
		animatedObject.registerEntityModifier(new LoopEntityModifier(splineModifier));
		
		animatedObject.animate(50);
		return animatedObject;
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
