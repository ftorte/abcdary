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

import com.welmo.educational.scenes.description.SpriteDescriptor;
import com.welmo.educational.scenes.description.Events.Action;
import com.welmo.educational.scenes.description.Events.EventDescriptionsManager;
import com.welmo.educational.scenes.description.Events.EventDescriptionsManager.Events;
import com.welmo.educational.scenes.description.Events.Modifier;
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
		
	/* FT public String getOnClikcNextScene() {
		return strOnClikcNextScene;
	}
	public void setOnClikcNextScene(String strOnClikcNextScene) {
		this.strOnClikcNextScene = strOnClikcNextScene;
	} */
	
	private void init(){
		pEDMgr = EventDescriptionsManager.getInstance();
	}
	
	/*public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pSpriteVertexBufferObject,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject,
				pShaderProgram);
		// TODO Auto-generated constructor stub
		init(); 
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pSpriteVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType, ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType, pShaderProgram);
		init(); 
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType);
		init(); 
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pShaderProgram);
		init(); 
		// TODO Auto-generated constructor stub
	}*/
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		init(); 
		// TODO Auto-generated constructor stub
	}
	/*public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pVertexBufferObject,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObject, pShaderProgram);
		init(); 
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pVertexBufferObject) {
		super(pX, pY, pTextureRegion, pVertexBufferObject);
		init(); 
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType, ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType,
				pShaderProgram);
		init(); 
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType);
		init(); 
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pShaderProgram);
		init(); 
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		init(); 
		// TODO Auto-generated constructor stub
	}*/

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		List<Action> pActionList = null;
		List<Modifier> pModifierList = null;
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			break;
		case TouchEvent.ACTION_MOVE:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			pActionList = pEDMgr.getActionList(Events.ON_MOVE,this.getPDescriptor());
			if (pActionList != null){
				
			}
			pModifierList = pEDMgr.getModifierList(Events.ON_MOVE,this.getPDescriptor());
			if (pModifierList != null){
				for (Modifier mod: pModifierList) {
					switch(mod.type){
					case MOVE: 
						this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);	
						break;
					case SCALE:
						break;
					default:
						break;
					}
				}
				return true;
			}
			break;
		case TouchEvent.ACTION_UP:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			// [FT] mClickListener.onClick(this.nID);
			if(mActionListener != null){
				pActionList = pEDMgr.getActionList(Events.ON_CLICK,this.getPDescriptor());
				if (pActionList != null){
					for (Action act: pActionList) {
						switch(act.type){
						case CHANGE_SCENE:
							mActionListener.onActionChangeScene(act.NextScene);
						case STICK:
							mActionListener.onStick(pSceneTouchEvent,pTouchAreaLocalX, pTouchAreaLocalY, act);
						default:
							break;
						}
					}
					return true;
				}
			}
			break;
		}
		return false;
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