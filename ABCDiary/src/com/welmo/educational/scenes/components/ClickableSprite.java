package com.welmo.educational.scenes.components;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
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
	private IClickLeastener mClickListener		=null;
	private IActionOnSceneListener mActionListener	=null;
	private String strOnClikcNextScene			="";
	private int nID;
	// ===========================================================
	// Constructors
	// ===========================================================


	public String getOnClikcNextScene() {
		return strOnClikcNextScene;
	}
	public void setOnClikcNextScene(String strOnClikcNextScene) {
		this.strOnClikcNextScene = strOnClikcNextScene;
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pSpriteVertexBufferObject,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject,
				pShaderProgram);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pDrawType);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager,
				pShaderProgram);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pVertexBufferObject,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObject, pShaderProgram);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			ISpriteVertexBufferObject pVertexBufferObject) {
		super(pX, pY, pTextureRegion, pVertexBufferObject);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType, ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType,
				pShaderProgram);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			DrawType pDrawType) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pDrawType);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			ShaderProgram pShaderProgram) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, pShaderProgram);
		// TODO Auto-generated constructor stub
	}
	public ClickableSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			break;
		case TouchEvent.ACTION_MOVE:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			break;
		case TouchEvent.ACTION_UP:
			if (MLOG.LOG)Log.i(TAG,"onAreaTouched ACTION_DOWN = " + nID);
			mClickListener.onClick(this.nID);
			mActionListener.onActionChangeScene(IActionOnSceneListener.CHANGE_SCENE, this.strOnClikcNextScene);
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

	public void setActionListener(IClickLeastener clickListener) {
		mClickListener=clickListener;
	}
	public void setActionOnSceneListener(IActionOnSceneListener actionLeastner) {
		this.mActionListener=actionLeastner;
	}
}