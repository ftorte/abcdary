package com.welmo.educational.scenes.components;

import java.util.List;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

import com.welmo.educational.scenes.description.Events.Action;
import com.welmo.educational.scenes.description.Events.EventDescriptionsManager;
import com.welmo.educational.scenes.description.Events.Modifier;
import com.welmo.educational.scenes.description.Events.EventDescriptionsManager.Events;
import com.welmo.educational.utility.MLOG;

public class CompoundSprite extends Rectangle{
	// ===========================================================
	// Constants
	// ===========================================================
	//Log & Debug
	private static final String TAG = "ClickableSprite";
	private EventDescriptionsManager pEDMgr					= null;
	private Object					 pDescriptor			= null;
	private IActionOnSceneListener mActionListener			=null;

	public CompoundSprite(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
		this.setColor(0, 1, 0);
		pEDMgr = EventDescriptionsManager.getInstance();

	}


	private int nID								=-1;

	public int getID() {
		return nID;
	}
	public void setID(int ID) {
		this.nID = ID;
	}
	
	public void attachComponentChild(float px, float py, float w, float h, IEntity pEntity) throws IllegalStateException {
		
		
		float curObjXmin,curObjXmax,curObjYmin,curObjYmax;
		float newObjXmin,newObjXmax,newObjYmin,newObjYmax;
		
		//Calculate coordinate current rectangle
		curObjXmin = this.getX();
		curObjXmax = this.getX() + this.getWidth();
		curObjYmin = this.getY();
		curObjYmax = this.getY() + this.getHeight();
		
		
		newObjXmin = px;
		newObjXmax = px + w;
		newObjYmin = py;
		newObjYmax = py + h;
		
		if(mChildren == null){
			this.setPosition(px,py);
			this.setSize(w,h);
			pEntity.setPosition(0, 0);
		}
		else{
			//this.setPosition(Math.min(curObjXmin,newObjXmin),Math.min(curObjYmin,newObjYmin));
			//1st expand compound zone
			this.setWidth(Math.max(curObjXmax, newObjXmax)-Math.min(curObjXmin,newObjXmin));
			this.setHeight(Math.max(curObjYmax, newObjYmax)-Math.min(curObjYmin,newObjYmin));
			//2nd calculate compound delta PX and delta PY
			float deltaPX = Math.min(curObjXmin,newObjXmin)-curObjXmin;
			float deltaPY = Math.min(curObjYmin,newObjYmin)-curObjYmin;
			//3th move all chile by -delatX/Y
			for (IEntity tmpEntity:mChildren)
				tmpEntity.setPosition(tmpEntity.getX()-deltaPX, tmpEntity.getY()-deltaPY);
			//4th move compound by delatX/Y
			this.setPosition(this.getX()+deltaPX, this.getY()+deltaPY);
			//5th add new entity to relative position in the Compound space
			pEntity.setPosition(px-this.getX(),py-this.getY());
		}
		
		this.attachChild(pEntity);
	}
	
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