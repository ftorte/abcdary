package com.welmo.educational.scenes.components;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public final class Stick extends CompoundSprite{
	
	
	public Stick(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}
	public enum StickFunctions {
	    STICK_LEFTH, STICK_RIGHT, STICK_UP, STICK_BOTTOM, 
	    STICK_UP_LEFTH, STICK_UP_RIGHT, STICK_BOTTOM_LEFTH, STICK_BOTTOM_RIGHT 
	}
	
	public void Lefth(Sprite entityToStick){
		entityToStick.setPosition(this.getX() + this.getWidth(),this.getY());
		this.attachSprite(entityToStick);
	}
	
	public void Right(Sprite entityToStick){
		entityToStick.setPosition(this.getX() - entityToStick.getWidth(),this.getY());
		this.attachSprite(entityToStick);
	}
	public void Up(Sprite entityToStick){
		entityToStick.setPosition(this.getX(),this.getY() - entityToStick.getHeight());
		this.attachSprite(entityToStick);
	}
	public void Bottom(Sprite entityToStick){
		entityToStick.setPosition(this.getX(),this.getY() + this.getHeight());
		this.attachSprite(entityToStick);
	}
	public void UpLefth(Sprite entityToStick){
		entityToStick.setPosition(this.getX() + this.getWidth(),this.getY() - entityToStick.getHeight());
		this.attachSprite(entityToStick);
	}
	public void UpRight(Sprite entityToStick){
		entityToStick.setPosition(this.getX() - entityToStick.getWidth(),this.getY() - entityToStick.getHeight());
		this.attachSprite(entityToStick);
	}
	public void BottomLefth(Sprite entityToStick){
		entityToStick.setPosition(this.getX() + this.getWidth(),this.getY() + this.getHeight());
		this.attachSprite(entityToStick);
	}
	public void BottomRight(Sprite entityToStick){
		entityToStick.setPosition(this.getX() - entityToStick.getWidth(),this.getY() + this.getHeight());
		this.attachSprite(entityToStick);
	}
	private void attachSprite(Sprite sprite){
		this.attachComponentChild(sprite.getX(), sprite.getY(),sprite.getWidth(),sprite.getHeight(), sprite);
	}
}
