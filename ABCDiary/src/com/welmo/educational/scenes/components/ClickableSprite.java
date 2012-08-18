package com.welmo.educational.scenes.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.welmo.educational.scenes.events.descriptors.Action;
import com.welmo.educational.scenes.events.descriptors.EventDescriptionsManager;
import com.welmo.educational.scenes.events.descriptors.Modifier;
import com.welmo.educational.scenes.events.descriptors.EventDescriptionsManager.Events;
import com.welmo.educational.utility.MLOG;

import android.util.Log;

public class ClickableSprite extends Sprite {
	
	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "ClickableSprite";
	
	// ===========================================================
	// Fields
	// ===========================================================
	//private IClickLeastener mClickListener					=null;
	private IActionOnSceneListener mActionListener			=null;
	//private String strOnClikcNextScene						="";
	private int nID											=-1;
	private EventDescriptionsManager pEDMgr					= null;
	private Object					 pDescriptor			= null;
	
	// ===========================================================
	// Constructors
	// ===========================================================
		
	private void init(){
		pEDMgr = EventDescriptionsManager.getInstance();
	}
	
	
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		init(); 
		// TODO Auto-generated constructor stub
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

	/*public void setActionListener(IClickLeastener clickListener) {
		mClickListener=clickListener;
	}*/
	
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