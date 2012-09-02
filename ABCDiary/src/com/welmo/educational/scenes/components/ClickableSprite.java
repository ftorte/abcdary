package com.welmo.educational.scenes.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.welmo.educational.scenes.components.descriptors.BasicObjectDescriptor.Alignment;
import com.welmo.educational.scenes.components.descriptors.SpriteObjectDescriptor;
import com.welmo.educational.scenes.events.descriptors.Action;
import com.welmo.educational.scenes.events.descriptors.EventDescriptionsManager;
import com.welmo.educational.scenes.events.descriptors.Modifier;
import com.welmo.educational.scenes.events.descriptors.EventDescriptionsManager.Events;
import com.welmo.educational.utility.MLOG;


public class ClickableSprite extends Sprite {
	
	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "ClickableSprite";
	
	// ===========================================================
	// Fields
	// ===========================================================
	private IActionOnSceneListener mActionListener			=null;
	private int nID											=-1;
	private EventDescriptionsManager pEDMgr					= null;
	private Object					 pDescriptor			= null;
	
	// ===========================================================
	// Constructors
	// ===========================================================
		
	private void init(){
		pEDMgr = EventDescriptionsManager.getInstance();
	}

	
	public void configure(SpriteObjectDescriptor spDsc){
		setID(spDsc.getID());
		setPDescriptor(spDsc);
		/* Setup Rotation*/
		setRotationCenter(getWidth()/2, getHeight()/2);
		setRotation(spDsc.getOriantation().getOriantation());
		//set position			
		setX(spDsc.getPosition().getX());
		setY(spDsc.getPosition().getY());
	}
	
	public void align(SpriteObjectDescriptor spDsc, IAreaShape theFather){
		//Setup horizontal Alignment
		Alignment alignment = spDsc.getPosition().getHorizzontalAlignment();
		if(alignment != Alignment.NO_ALIGNEMENT){
			switch(alignment){
				case LEFTH:
					this.setX(0);
					break;
				case RIGHT:
					setX(theFather.getWidth()-this.getWidth());
					break;
				case CENTER:
					setX(theFather.getWidth()/2-this.getWidth()/2);
					break;
				default:
					break;
			}
		}
		//Setup Vertical Alignment
		alignment = spDsc.getPosition().getHorizzontalAlignment();
		if(alignment != Alignment.NO_ALIGNEMENT){
			switch(alignment){
				case TOP:
					this.setY(0);
					break;
				case BOTTOM:
					setY(theFather.getHeight()-this.getHeight());
					break;
				case CENTER:
					setY(theFather.getHeight()/2-this.getHeight()/2);
					break;
				default:
					break;
			}
		}	
	}
	
	
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		init(); 		
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		boolean managed = false;
		List<Action> pActionList = null;
		List<Modifier> pModifierList = null;
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			break;
		case TouchEvent.ACTION_MOVE:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_MOVE = " + nID);
			pModifierList = pEDMgr.getModifierList(Events.ON_MOVE,this.getPDescriptor());
			if (pModifierList != null){
				for (Modifier mod: pModifierList) {
					switch(mod.type){
					case MOVE: 
						this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);	
						managed = true;
						break;
					case SCALE:
						break;
					default:
						break;
					}
				}
			}
			if(mActionListener != null){
				pActionList = pEDMgr.getActionList(Events.ON_MOVE,this.getPDescriptor());
				if (pActionList != null){
					for (Action act: pActionList) {
						switch(act.type){
						case STICK:
							mActionListener.onStick(this, act);
							managed = true;
							break;
						default:
							break;
						}
					}
				}
			}
			break;
		case TouchEvent.ACTION_UP:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_UP= " + nID);
			// [FT] mClickListener.onClick(this.nID);
			if(mActionListener != null){
				pActionList = pEDMgr.getActionList(Events.ON_CLICK,this.getPDescriptor());
				if (pActionList != null){
					for (Action act: pActionList) {
						switch(act.type){
						case CHANGE_SCENE:
							mActionListener.onActionChangeScene(act.NextScene);
							managed = true;
							break;
						default:
							break;
						}
					}
				}
			}
			break;
		}
		return managed;
	}
	
	public int getID() {
		return nID;
	}
	public void setID(int ID) {
		this.nID = ID;
	}
	
	public static interface  IClickLeastener{
		public void onClick(int ObjectID); // Sprite call this interface to inform parent that has been clicked
		//public void reset();
		//public int getObjectID();
	}
	
	public void setActionOnSceneListener(IActionOnSceneListener actionLeastner) {
		this.mActionListener=actionLeastner;
	}
	public Object getPDescriptor() {
		return pDescriptor;
	}
	public void setPDescriptor(Object pDescriptor) {
		this.pDescriptor = pDescriptor;
	}
}